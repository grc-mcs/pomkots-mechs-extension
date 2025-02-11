package grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile;

import grcmcs.minecraft.mods.pomkotsmechs.config.BattleBalance;
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

public class MachineGunBulletEntity extends PomkotsThrowableProjectile implements GeoEntity, GeoAnimatable {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private static final int MAX_LIFE_TICKS = 30;
    private int lifeTicks = 0;
    private LivingEntity shooter = null;
    private int damage;

    public MachineGunBulletEntity(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        this(entityType, world, null);
    }

    public MachineGunBulletEntity(EntityType<? extends ThrowableProjectile> entityType, Level level, LivingEntity shooter) {
        this(entityType, level, shooter, CombatBalance.BASE_DAMAGE_MACHINEGUN);
    }

    public MachineGunBulletEntity(EntityType<? extends ThrowableProjectile> entityType, Level world, LivingEntity shooter, int damage) {
        super(entityType, world);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.shooter = shooter;
        this.damage = damage;
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

        var vel = this.getDeltaMovement();
        this.setPos(this.getX() + vel.x(), this.getY() + vel.y(), this.getZ() + vel.z());

        if(this.lifeTicks++ >= MAX_LIFE_TICKS) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        if (entity.equals(shooter)) {
            return;
        }

        float d = damage;

        if (lifeTicks < 10) {
            d += (10 - (float)lifeTicks) * damage / 20;
        }

        entity.hurt(entity.damageSources().thrown(this, this.getOwner() != null ? this.getOwner() : this), d);
        entity.invulnerableTime = 0;

        if (this.level().isClientSide) {
            this.level().addParticle(
                    ParticleTypes.EXPLOSION,
                    this.getX(), this.getY(), this.getZ(),
                    0,0,0);
        }
        this.discard();
    }


    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (this.level().isClientSide) {
            this.level().addParticle(
                    ParticleTypes.EXPLOSION,
                    this.getX(), this.getY(), this.getZ(),
                    0,0,0);
        }
        this.discard();
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "rotation", 0, event -> {
            return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.bullet.idle"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

}
