package grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle;

import grcmcs.minecraft.mods.pomkotsmechs.PomkotsMechs;
import grcmcs.minecraft.mods.pomkotsmechs.client.input.DriverInput;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.Action;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.ActionController;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.config.CombatBalance;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamLargeEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.MachineGunBulletEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.MissileHorizontalEntity;
import net.minecraft.util.Mth;
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

public class Pmgz02Entity extends PmgBaseEntity {

    @Override
    protected String getMechName() {
        return "base";
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return createLivingAttributes()
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.MAX_HEALTH, CombatBalance.BASE_HEALTH * 0.4);
    }

    public Pmgz02Entity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    protected static final int ACT_SHOOT = 7;
    protected static final int ACT_BAZOOKA = 9;
    protected static final int ACT_MISSILE = 10;
    protected static final int ACT_SABER = 8;
    protected static final int ACT_SABER2 = 11;
    protected static final int ACT_SABER3 = 12;


    @Override
    protected void registerCombatActions() {
        this.actionController.registerAction(ACT_SHOOT, new Action(0, 7, 2), ActionController.ActionType.R_ARM_MAIN);
        this.actionController.registerAction(ACT_SABER3, new Action(60, 11, 9), ActionController.ActionType.L_ARM_MAIN);
        this.actionController.registerAction(ACT_SABER2, new Action(60, 11, 9), ActionController.ActionType.L_ARM_MAIN);
        this.actionController.registerAction(ACT_SABER, new Action(60, 11, 9), ActionController.ActionType.L_ARM_MAIN);
        this.actionController.registerAction(ACT_BAZOOKA, new Action(20, 9, 11), ActionController.ActionType.R_SHL_MAIN);
        this.actionController.registerAction(ACT_MISSILE, new Action(20, 5, 10), ActionController.ActionType.L_SHL_MAIN);
    }

    @Override
    protected void applyPlayerInputWeaponsMainMode(DriverInput driverInput) {
        if (driverInput.isWeaponLeftHandPressed()) {
            final int cancelableTick = 4;
            if (this.actionController.getAction(ACT_SABER).isInAction()) {
                if (this.actionController.getAction(ACT_SABER).currentFireTime > cancelableTick) {
                    resetActionExceptCooltime(this.actionController.getAction(ACT_SABER));
                    this.actionController.getAction(ACT_SABER2).startAction();
                }
            } else if (this.actionController.getAction(ACT_SABER2).isInAction()) {
                if (this.actionController.getAction(ACT_SABER2).currentFireTime > cancelableTick) {
                    resetActionExceptCooltime(this.actionController.getAction(ACT_SABER2));
                    this.actionController.getAction(ACT_SABER3).startAction();
                }
            } else {
                this.actionController.getAction(ACT_SABER).startAction();
            }
        } else if (driverInput.isWeaponRightHandPressed()) {
            this.actionController.getAction(ACT_SHOOT).startAction();
        }
    }

    @Override
    protected void fireWeapons() {
        Level level = level();

        if (actionController.getAction(ACT_SHOOT).isOnFire()) {
            this.fireShoot(level);
        } else if (actionController.getAction(ACT_SABER).isOnFire()
                || actionController.getAction(ACT_SABER2).isOnFire()
                || actionController.getAction(ACT_SABER3).isOnFire()) {
            this.fireSaber(level, CombatBalance.BASE_DAMAGE_SABER * 1.5F);
        }
    }

    private void fireShoot(Level level) {
        if (!level.isClientSide()) {
            MachineGunBulletEntity be = new MachineGunBulletEntity(PomkotsMechsExtension.MACHINEGUNBULLET.get(), level, this);

            // 原因不明なんだけど、getPosした時の座標と、レンダリングされてる座標で3tick分ぐらい乖離がある気配がする
            // ので、3tick前の座標をオフセットにする
            // なんかaddVelocity周りが悪さしてる…？
            var offset = posHistory.getFirst();

            // オフセット位置から大体の銃口の座標を決める（モデル位置からとるとクラサバ同期がめんどい…）
            var muzzlPos = new Vec3(6, 19F, 16F);
            muzzlPos = muzzlPos.yRot((float) Math.toRadians((-1.0) * this.getYRot()));
            be.setPos(offset.add(muzzlPos));

            float[] angle = getShootingAngle(be, true);

            be.shootFromRotation(be, angle[0], angle[1], this.getFallFlyingTicks(), CombatBalance.BASE_SPEED_MACHINEGUN, 2F);

            level.addFreshEntity(be);
        } else {
            if (tickCount % 10 == 0) {
                playSoundEffect(PomkotsMechs.SE_GATLING_EVENT.get());
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);

        controllers.add(new AnimationController<>(this, "attack", 2, event -> {
            if (this.actionController.getAction(ACT_SABER3).isInAction()) {
                if (this.actionController.getAction(ACT_SABER3).isOnStart()) {
                    event.getController().forceAnimationReset();

                    if (this.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber3_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber3"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_SABER2).isInAction()) {
                if (this.actionController.getAction(ACT_SABER2).isOnStart()) {
                    event.getController().forceAnimationReset();

                    if (this.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber2_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber2"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_SABER).isInAction()) {
                if (this.actionController.getAction(ACT_SABER).isOnStart()) {
                    event.getController().forceAnimationReset();

                    if (this.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber1_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber1"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_BAZOOKA).isInAction()) {
                if (this.actionController.getAction(ACT_BAZOOKA).isOnStart()) {
                    event.getController().forceAnimationReset();
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".bazooka_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".bazooka"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_MISSILE).isInAction()) {
                if (this.actionController.getAction(ACT_MISSILE).isOnStart()) {
                    event.getController().forceAnimationReset();
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".missileUpperBody"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".missile"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (event.isMoving() && this.actionController.isBoost() && inAirTicks < 10 && !this.actionController.isInActionAll()) {
                return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".runUpper"));
            }
            return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".nop"));
        }).setSoundKeyframeHandler(state -> {
            registerAnimationSoundHandlers(state);
        }));

        controllers.add(new AnimationController<>(this, "gatring", 1, event -> {
            if (this.actionController.getAction(ACT_SHOOT).isInAction()) {
                if (this.actionController.getAction(ACT_SHOOT).isOnStart()) {
                    event.getController().forceAnimationReset();
                }

                if (!this.actionController.getAction(ACT_SHOOT).isInFire()) {
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".shoot1_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".shoot1"));
                    }
                } else {
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".shoot2_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".shoot2"));
                    }
                }
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".nop"));
        }).setSoundKeyframeHandler(state -> {
            registerAnimationSoundHandlers(state);
        }));
    }

    protected boolean isMoving() {
        var vel = this.getDeltaMovement();
        return Mth.abs((float)vel.x) > 0.3 || Mth.abs((float)vel.z) > 0.3 || Mth.abs((float)vel.y) > 0.3;
    }

    @Override
    protected float getRunSpeed() {
        return 2F;
    }
}
