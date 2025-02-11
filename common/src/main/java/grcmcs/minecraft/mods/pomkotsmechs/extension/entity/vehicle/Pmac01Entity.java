package grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle;

import grcmcs.minecraft.mods.pomkotsmechs.PomkotsMechs;
import grcmcs.minecraft.mods.pomkotsmechs.client.input.DriverInput;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.MissileVerticalEntity;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.Action;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.ActionController;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.config.CombatBalance;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class Pmac01Entity extends PmaBaseEntity {
    public static final float DEFAULT_SCALE = 0.5f;

    @Override
    protected String getMechName() {
        return "base";
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return createLivingAttributes()
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.MAX_HEALTH, CombatBalance.BASE_HEALTH * 0.3);
    }

    public Pmac01Entity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    protected static final int ACT_SHOOT = 7;
    protected static final int ACT_GATLING = 8;
    protected static final int ACT_MISSILE_RIGHT = 9;
    protected static final int ACT_MISSILE_LEFT = 10;


    @Override
    protected void registerCombatActions() {
        this.actionController.registerAction(ACT_SHOOT, new Action(20, 9, 11), ActionController.ActionType.R_ARM_MAIN);
        this.actionController.registerAction(ACT_GATLING, new Action(0, 7, 2), ActionController.ActionType.L_ARM_MAIN);
        this.actionController.registerAction(ACT_MISSILE_RIGHT, new Action(120, 9, 11), ActionController.ActionType.R_SHL_MAIN);
        this.actionController.registerAction(ACT_MISSILE_LEFT, new Action(120, 2, 14), ActionController.ActionType.L_SHL_MAIN);
    }

    @Override
    protected void applyPlayerInputWeaponsMainMode(DriverInput driverInput) {
        if (driverInput.isWeaponRightHandPressed()) {
            this.actionController.getAction(ACT_SHOOT).startAction();
        } else if (driverInput.isWeaponLeftHandPressed()) {
            this.actionController.getAction(ACT_GATLING).startAction();
        } else if (driverInput.isWeaponRightShoulderPressed()) {
            this.actionController.getAction(ACT_MISSILE_RIGHT).startAction();
        } else if (driverInput.isWeaponLeftShoulderPressed()) {
            this.actionController.getAction(ACT_MISSILE_LEFT).startAction();
        }
    }

    @Override
    protected void fireWeapons() {
        Level level = level();

        if (actionController.getAction(ACT_SHOOT).isOnFire()) {
            this.fireShoot(level);
        } else if (actionController.getAction(ACT_GATLING).isOnFire()) {
            this.fireGatling(level);
        } else if (actionController.getAction(ACT_MISSILE_RIGHT).isOnFire()) {
            this.fireMissileRight(level);
        } else if (actionController.getAction(ACT_MISSILE_LEFT).isInFire()) {
            if (actionController.getAction(ACT_MISSILE_LEFT).currentFireTime % 2 == 0) {
                this.fireMissileLeft(level, actionController.getAction(ACT_MISSILE_LEFT).currentFireTime / 2 - 1);
            }
        }
    }

    private void fireShoot(Level level) {
        if (!level.isClientSide()) {
            BulletLargeEntity be = new BulletLargeEntity(PomkotsMechsExtension.BULLETLARGE.get(), level, this, CombatBalance.BASE_DAMAGE_BEAM * 2 / 3);

            // 原因不明なんだけど、getPosした時の座標と、レンダリングされてる座標で3tick分ぐらい乖離がある気配がする
            // ので、3tick前の座標をオフセットにする
            // なんかaddVelocity周りが悪さしてる…？
            var offset = posHistory.getFirst();

            // オフセット位置から大体の銃口の座標を決める（モデル位置からとるとクラサバ同期がめんどい…）
            var muzzlPos = new Vec3(-3F/2, 15F/2, 18F/2);
            muzzlPos = muzzlPos.yRot((float) Math.toRadians((-1.0) * this.getYRot()));

            be.setPos(offset.add(muzzlPos));

            float[] angle = getShootingAngle(be, false);
            be.shootFromRotation(be, angle[0], angle[1], this.getFallFlyingTicks(), CombatBalance.BASE_SPEED_BEAM_LARGE, 0F);

            level.addFreshEntity(be);
        }
    }

    private void fireGatling(Level level) {
        if (!level.isClientSide()) {
            MachineGunBulletEntity be = new MachineGunBulletEntity(PomkotsMechsExtension.MACHINEGUNBULLET.get(), level, this, CombatBalance.BASE_DAMAGE_MACHINEGUN * 2 / 3);

            // 原因不明なんだけど、getPosした時の座標と、レンダリングされてる座標で3tick分ぐらい乖離がある気配がする
            // ので、3tick前の座標をオフセットにする
            // なんかaddVelocity周りが悪さしてる…？
            var offset = posHistory.getFirst();

            // オフセット位置から大体の銃口の座標を決める（モデル位置からとるとクラサバ同期がめんどい…）
            var muzzlPos = new Vec3(3F/2, 15F/2, 16F/2);
            muzzlPos = muzzlPos.yRot((float) Math.toRadians((-1.0) * this.getYRot()));
            be.setPos(offset.add(muzzlPos));

            float[] angle = getShootingAngle(be, true);

            be.shootFromRotation(be, angle[0], angle[1], this.getFallFlyingTicks(), CombatBalance.BASE_SPEED_MACHINEGUN, 1F);

            level.addFreshEntity(be);
        } else {
            if (tickCount % 10 == 0) {
                playSoundEffect(PomkotsMechs.SE_GATLING_EVENT.get());
            }
        }
    }

    private void fireMissileRight(Level level) {
        if (!level.isClientSide()) {
            // 原因不明なんだけど、getPosした時の座標と、レンダリングされてる座標で3tick分ぐらい乖離がある気配がする
            // ので、3tick前の座標をオフセットにする
            // なんかaddVelocity周りが悪さしてる…？
            var offset = posHistory.getFirst();

            // オフセット位置から大体の銃口の座標を決める（モデル位置からとるとクラサバ同期がめんどい…）
            var muzzlPos = new Vec3(-3F/2, 18F/2, 2F/2);

            for (int i = 0; i < 4; i++) {
                var worldMuzzlPos = muzzlPos.add(1 * (i / 2 - 0.5),1 * (i % 2),0).yRot((float) Math.toRadians((-1.0) * this.getYRot()));

                MissileHorizontalEntity be = new MissileHorizontalEntity(PomkotsMechsExtension.MISSILE.get(), level, this, null, CombatBalance.BASE_DAMAGE_MISSILE * 2 / 3);

                be.setPos(offset.add(worldMuzzlPos));

                be.shootFromRotation(be, -10, this.getYRot(), this.getFallFlyingTicks(), CombatBalance.BASE_SPEED_MISSILE, 0F);

                level.addFreshEntity(be);
            }
        }
    }

    private void fireMissileLeft(Level level, int slot) {
        if (!level.isClientSide()) {
            // 原因不明なんだけど、getPosした時の座標と、レンダリングされてる座標で3tick分ぐらい乖離がある気配がする
            // ので、3tick前の座標をオフセットにする
            // なんかaddVelocity周りが悪さしてる…？
            var offset = posHistory.getFirst();

            // オフセット位置から大体の銃口の座標を決める（モデル位置からとるとクラサバ同期がめんどい…）
            var muzzlPos = new Vec3(1.3F, 18F/2, 2F/2);
            var worldMuzzlPos = muzzlPos.add(1 * (slot / 3),0,-2 - ((slot % 3) * 1)).yRot((float) Math.toRadians((-1.0) * this.getYRot()));

            MissileHorizontalEntity be = new MissileHorizontalEntity(PomkotsMechsExtension.MISSILE.get(), level, this, null, CombatBalance.BASE_DAMAGE_MISSILE * 2 / 3);

            be.setPos(offset.add(worldMuzzlPos));

            be.shootFromRotation(be, -70, this.getYRot(), this.getFallFlyingTicks(), 1F, 0F);

            level.addFreshEntity(be);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);

        controllers.add(new AnimationController<>(this, "attack", 2, event -> {
            if (this.actionController.getAction(ACT_SHOOT).isInAction()) {
                if (this.actionController.getAction(ACT_SHOOT).isOnStart()) {
                    event.getController().forceAnimationReset();
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".shootUpperBody"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".shoot"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_MISSILE_RIGHT).isInAction()) {
                if (this.actionController.getAction(ACT_MISSILE_RIGHT).isOnStart()) {
                    event.getController().forceAnimationReset();
                    return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".mr"));
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_MISSILE_LEFT).isInAction()) {
                if (this.actionController.getAction(ACT_MISSILE_LEFT).isOnStart()) {
                    event.getController().forceAnimationReset();
                    return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".ml"));
                }
                return PlayState.CONTINUE;
            } else if (event.isMoving() && this.actionController.isBoost() && inAirTicks < 10) {
                return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".runUpper"));
            }

            if (event.isMoving()) {
                return PlayState.CONTINUE;
            } else {
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".idle"));
            }
        }).setSoundKeyframeHandler(state -> {
            registerAnimationSoundHandlers(state);
        }));

        controllers.add(new AnimationController<>(this, "gatring", 1, event -> {
            if (this.actionController.getAction(ACT_GATLING).isInAction()) {
                if (this.actionController.getAction(ACT_GATLING).isOnStart()) {
                    event.getController().forceAnimationReset();
                }

                if (!this.actionController.getAction(ACT_GATLING).isInFire()) {
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".shootL1_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".shootL1"));
                    }
                } else {
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".shootL2_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".shootL2"));
                    }
                }
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".nop"));
        }).setSoundKeyframeHandler(state -> {
            registerAnimationSoundHandlers(state);
        }));
    }

    @Override
    protected float getRunSpeed() {
        return 2.5F;
    }

    @Override
    protected float getEvasionSpeed() {
        return 8F;
    }

    @Override
    protected float getJumpInitialSpped() {
        return 3.5F;
    }

    @Override
    protected float getJumpContinueSpped() {
        return 1F;
    }
}
