package grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle;

import grcmcs.minecraft.mods.pomkotsmechs.client.input.DriverInput;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.Action;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.ActionController;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.config.CombatBalance;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamLargeEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.MissileHorizontalEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public abstract class PmaBaseEntity extends PmgBaseEntity {
    public PmaBaseEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected boolean useEnergy(int dec) {
        dec = dec * 2 / 3;
        return super.useEnergy(dec);
    }

    @Override
    protected void handleCollisionWithLivingEntities() {
        //NOP
    }

    @Override
    protected void registerActions() {
        super.registerActions();

        this.actionController.getAction(ACT_EVASION).maxChargeTime = 2;
        this.actionController.getAction(ACT_EVASION).maxFireTime = 8;
    }

    @Override
    protected void registerMoveActions(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 2, event -> {
            if (this.actionController.getAction(ACT_STAND).isInAction()) {
                if (this.actionController.getAction(ACT_STAND).isOnStart()) {
                    event.getController().forceAnimationReset();
                }
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".mount"));
            } else if (this.actionController.getAction(ACT_KNEEL).isInAction()) {
                if (this.actionController.getAction(ACT_KNEEL).isOnStart()) {
                    event.getController().forceAnimationReset();
                }
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".dismount"));
            }

            if (this.isSubMode()) {
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".dismount"));
            }

            if (this.getDrivingPassenger() == null) {
                event.getController().forceAnimationReset();
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".idle"));
            }

            if (this.actionController.getAction(ACT_JUMP).isInAction()) {
                if (this.actionController.getAction(ACT_JUMP).isOnStart()) {
                    event.getController().forceAnimationReset();
                }
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".jump"));

            }

            if (this.actionController.getAction(ACT_EVASION).isInAction()) {
                if (this.actionController.getAction(ACT_EVASION).isOnStart()) {
                    event.getController().forceAnimationReset();

                    if (this.sidewayIntention < 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".evasion_right"));

                    } else if (sidewayIntention > 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".evasion_left"));

                    } else {
                        if (!onGround()) {
                            return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".dash"));
                        } else {
                            return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".dash"));
                        }
                    }
                }
                return PlayState.CONTINUE;
            }

            if (event.isMoving()) {
                if (event.getAnimatable().onGround()) {
                    if (this.actionController.isBoost()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".dash_keep"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".run"));
                    }
                } else {
                    if (inAirTicks > 5) {
                        if (this.actionController.isBoost()) {
                            return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".dash_keep"));
                        } else {
                            return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".fly"));
                        }
                    } else {
                        if (this.actionController.isBoost()) {
                            return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".dash_keep"));
                        } else {
                            return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".run"));
                        }
                    }
                }
            } else {
                if (this.yRotO != this.getYRot()) {
                    return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".run"));
                } else {
                    event.getController().forceAnimationReset();
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".idle"));
                }
            }
        }).setSoundKeyframeHandler(state -> {
            registerAnimationSoundHandlers(state);
        }));
    }

    @Override
    public double getPassengersRidingOffset() {
        return 7.5F;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        super.getDismountLocationForPassenger(passenger);

        float height;
        if (this.isMainMode()) {
            height =  7.5F;
        } else {
            height =  2.5F;
        }
        return new Vec3(this.getX(), this.getY() + height, this.getZ());
    }
}
