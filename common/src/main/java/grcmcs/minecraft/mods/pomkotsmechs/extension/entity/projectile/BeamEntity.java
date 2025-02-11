package grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile;

import grcmcs.minecraft.mods.pomkotsmechs.config.BattleBalance;
import grcmcs.minecraft.mods.pomkotsmechs.entity.monster.boss.Pmb01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.PomkotsThrowableProjectile;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.ProjectileUtil;
import grcmcs.minecraft.mods.pomkotsmechs.extension.config.CombatBalance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BeamEntity extends PomkotsThrowableProjectile implements GeoEntity, GeoAnimatable {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private static final int MAX_LIFE_TICKS = 80;
    private int lifeTicks = 0;
    private int damage;
    private LivingEntity shooter = null;
    private float explosionScale = 15;

    public BeamEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        this(entityType, level, null);
    }

    public BeamEntity(EntityType<? extends ThrowableProjectile> entityType, Level level, LivingEntity shooter) {
        this(entityType, level, shooter, CombatBalance.BASE_DAMAGE_BEAM);
    }

    public BeamEntity(EntityType<? extends ThrowableProjectile> entityType, Level world, LivingEntity shooter, int damage) {
        super(entityType, world);
        this.setNoGravity(true);
        this.noCulling = true;
        this.noPhysics = true;
        this.shooter = shooter;
        this.damage = damage;
    }

    @Override
    public boolean shouldRender(double a, double b, double c) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double e) {
        return true;
    }

    @Override
    public void tick() {
        this.setNoGravity(true);

        // 弾速が早すぎると、ティック間にすり抜けちゃうのでレイキャスティングで補完
        var hitResult = ProjectileUtil.raycastBoundingCheck(this);
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onHit(hitResult);
        }

        super.tick();

        this.updateRotationBasedOnVelocity();

        var vel = this.getDeltaMovement();
        this.setPos(this.getX() + vel.x(), this.getY() + vel.y(), this.getZ() + vel.z());

        if(this.lifeTicks++ >= MAX_LIFE_TICKS) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        handleEntityHit(entityHitResult.getEntity());
    }

    public void handleEntityHit(Entity entity) {
        if (entity.equals(shooter) || entity instanceof BeamEntity) {
            return;
        }

        entity.hurt(entity.damageSources().thrown(this, this.getOwner() != null ? this.getOwner() : this), damage);
        entity.invulnerableTime = 0;

        createExplosion(entity.position());
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        createExplosion(blockHitResult.getLocation());
        this.discard();
    }


    private void createExplosion(Vec3 pos) {
        Level world = level();
        if (!world.isClientSide) {
            if (ProjectileUtil.isDestructionAllowed(this)) {
                world.explode(this,  pos.x, pos.y, pos.z, explosionScale, false, Level.ExplosionInteraction.BLOCK);

            } else {
                world.explode(this,  pos.x, pos.y, pos.z, explosionScale, false, Level.ExplosionInteraction.NONE);
            }
        }
    }

    @Override
    public void onClientRemoval() {
        if (this.level().isClientSide) {
            addParticles(this.position());
        }
    }

    private void addParticles(Vec3 offset) {
        Level world = level();
        for (int i = 0; i < 5; i++) {
            int rad = Math.abs(i - 3);

            for (int j = 0; j < 5 - rad; j++) {
                for (int k = 0; k < 5 - rad; k++) {
                    world.addParticle(
                            ParticleTypes.EXPLOSION,
                            offset.x - (k - (3 - rad/2)) * 2,
                            offset.y - (i - 3) * 2,
                            offset.z - (j - (3 - rad/2)) * 2,
                            0,0,0);
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "rotation", 0, event -> {
            return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.beam.idle"));
        }));
    }

    protected void updateRotationBasedOnVelocity() {
        Vec3 velocity = this.getDeltaMovement();

        if (!velocity.equals(Vec3.ZERO)) {
            double yaw = Math.toDegrees(Math.atan2(velocity.x, velocity.z));
            this.setYRot((float) yaw);

            double horizontalDistance = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
            double pitch = Math.toDegrees(Math.atan2(velocity.y, horizontalDistance));
            this.setXRot((float) pitch);

            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

}
