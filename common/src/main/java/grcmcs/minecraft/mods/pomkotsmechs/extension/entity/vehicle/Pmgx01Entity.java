package grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle;

import grcmcs.minecraft.mods.pomkotsmechs.client.input.DriverInput;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.Action;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.ActionController;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.config.CombatBalance;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.MissileHorizontalEntity;
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

public class Pmgx01Entity extends PmgBaseEntity {
    @Override
    protected String getMechName() {
        return "base";
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return createLivingAttributes()
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.MAX_HEALTH, CombatBalance.BASE_HEALTH * 0.7);
    }

    public Pmgx01Entity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    protected static final int ACT_CHEST = 7;
    protected static final int ACT_SABER = 8;
    protected static final int ACT_SABER2 = 11;
    protected static final int ACT_SABER3 = 12;
    protected static final int ACT_MISSILE = 10;
    protected static final int ACT_TETSU = 9;

    @Override
    protected void registerCombatActions() {
        this.actionController.registerAction(ACT_CHEST, new Action(60, 18, 10), ActionController.ActionType.L_ARM_MAIN);
        this.actionController.registerAction(ACT_SABER3, new Action(60, 11, 11), ActionController.ActionType.R_ARM_MAIN);
        this.actionController.registerAction(ACT_SABER2, new Action(60, 11, 11), ActionController.ActionType.R_ARM_MAIN);
        this.actionController.registerAction(ACT_SABER, new Action(60, 11, 11), ActionController.ActionType.R_ARM_MAIN);
        this.actionController.registerAction(ACT_MISSILE, new Action(120, 11, 11), ActionController.ActionType.R_SHL_MAIN);
        this.actionController.registerAction(ACT_TETSU, new Action(60, 11, 11), ActionController.ActionType.L_SHL_MAIN);
    }

    @Override
    protected void applyPlayerInputWeaponsMainMode(DriverInput driverInput) {
        if (driverInput.isWeaponRightHandPressed()) {
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
        } else if (driverInput.isWeaponLeftHandPressed()) {
            this.actionController.getAction(ACT_CHEST).startAction();
        } else if (driverInput.isWeaponRightShoulderPressed()) {
            this.actionController.getAction(ACT_MISSILE).startAction();
        } else if (driverInput.isWeaponLeftShoulderPressed()) {
            this.actionController.getAction(ACT_TETSU).startAction();
        }
    }

    @Override
    protected void fireWeapons() {
        Level level = level();

        if (this.isMainMode()) {
            if (actionController.getAction(ACT_CHEST).isOnFire()) {
                this.fireChest(level);
            } else if (actionController.getAction(ACT_SABER).isOnFire()
                    || actionController.getAction(ACT_SABER2).isOnFire()
                    || actionController.getAction(ACT_SABER3).isOnFire()) {
                this.fireSaber(level);
            } else if (actionController.getAction(ACT_TETSU).isOnFire()) {
                this.fireTetsu(level);
            } else if (actionController.getAction(ACT_MISSILE).isOnFire()) {
                this.fireMissile(level);
            }
        }
    }

    private void fireChest(Level level) {
        this.fireSaber(level, CombatBalance.BASE_DAMAGE_SABER * 1.5F, 100);
    }

    private void fireTetsu(Level level) {
        this.fireSaber(level, CombatBalance.BASE_DAMAGE_SABER * 1.2F, 100);
    }

    private void fireMissile(Level level) {
        if (!level.isClientSide()) {
            // 原因不明なんだけど、getPosした時の座標と、レンダリングされてる座標で3tick分ぐらい乖離がある気配がする
            // ので、3tick前の座標をオフセットにする
            // なんかaddVelocity周りが悪さしてる…？
            var offset = posHistory.getFirst();

            // オフセット位置から大体の銃口の座標を決める（モデル位置からとるとクラサバ同期がめんどい…）
            var muzzlPos = new Vec3(0, 19F, 0F);

            for (int i = 0; i < 6; i++) {
                var worldMuzzlPos = muzzlPos.add(4.5 * (i / 3 - 0.5),1 * (i % 3),0).yRot((float) Math.toRadians((-1.0) * this.getYRot()));

                MissileHorizontalEntity be = new MissileHorizontalEntity(PomkotsMechsExtension.MISSILE.get(), level, this, null, CombatBalance.BASE_DAMAGE_MISSILE /2);

                be.setPos(offset.add(worldMuzzlPos));

                be.shootFromRotation(be, 0, this.getYRot(), this.getFallFlyingTicks(), CombatBalance.BASE_SPEED_MISSILE, 0F);

                level.addFreshEntity(be);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);

        controllers.add(new AnimationController<>(this, "attack", 2, event -> {
            if (this.isSubMode()) {
                event.getController().forceAnimationReset();
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".nop"));
            }

            if (this.actionController.getAction(ACT_CHEST).isInAction()) {
                if (this.actionController.getAction(ACT_CHEST).isOnStart()) {
                    event.getController().forceAnimationReset();
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".chest_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".chest"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_SABER3).isInAction()) {
                if (this.actionController.getAction(ACT_SABER3).isOnStart()) {
                    event.getController().forceAnimationReset();

                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber3_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber3"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_SABER2).isInAction()) {
                if (this.actionController.getAction(ACT_SABER2).isOnStart()) {
                    event.getController().forceAnimationReset();

                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber2_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber2"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_SABER).isInAction()) {
                if (this.actionController.getAction(ACT_SABER).isOnStart()) {
                    event.getController().forceAnimationReset();

                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber1_upper"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".saber1"));
                    }
                }
                return PlayState.CONTINUE;
            } else if (this.actionController.getAction(ACT_TETSU).isInAction()) {
                if (this.actionController.getAction(ACT_TETSU).isOnStart()) {
                    event.getController().forceAnimationReset();
                    if (event.isMoving()) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".tetsu"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".tetsu"));
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
    }

    @Override
    protected float getRunSpeed() {
        return 1.7F;
    }

}
