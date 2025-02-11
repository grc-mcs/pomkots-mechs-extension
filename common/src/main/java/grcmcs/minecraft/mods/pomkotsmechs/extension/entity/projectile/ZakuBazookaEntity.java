package grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile;

import grcmcs.minecraft.mods.pomkotsmechs.PomkotsMechs;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.ExplosionEntity;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.GrenadeLargeEntity;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.PomkotsThrowableProjectile;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.ProjectileUtil;
import grcmcs.minecraft.mods.pomkotsmechs.extension.config.CombatBalance;
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

public class ZakuBazookaEntity extends GrenadeLargeEntity {

    private float explosionScale = 0;
    private LivingEntity shooter = null;

    public ZakuBazookaEntity(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        this(entityType, world, (LivingEntity)null);
    }

    public ZakuBazookaEntity(EntityType<? extends ThrowableProjectile> entityType, Level world, LivingEntity shooter) {
        this(entityType, world, shooter, 10.0F);
    }

    public ZakuBazookaEntity(EntityType<? extends ThrowableProjectile> entityType, Level world, LivingEntity shooter, float exprosionScale) {
        super(entityType, world, shooter, exprosionScale);
        this.shooter = shooter;
        this.explosionScale = exprosionScale;
        this.noCulling = true;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        if (entity.equals(shooter)) {
            return;
        }

        entity.hurt(entity.damageSources().thrown(this, this.getOwner() != null ? this.getOwner() : this), CombatBalance.BASE_DAMAGE_ZAKUBAZ);
        entity.invulnerableTime = 0;

        this.createExplosion(entityHitResult.getLocation());
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

            ExplosionEntity e = new ExplosionEntity(PomkotsMechs.EXPLOSION.get(), world);
            e.setPos(this.position());
            world.addFreshEntity(e);
        }
    }


    @Override
    public boolean shouldRender(double a, double b, double c) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double e) {
        return true;
    }

}
