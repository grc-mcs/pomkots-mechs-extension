package grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle;

import grcmcs.minecraft.mods.pomkotsmechs.PomkotsMechs;
import grcmcs.minecraft.mods.pomkotsmechs.client.input.DriverInput;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.ExplosionEntity;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.PomkotsThrowableProjectile;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.PomkotsVehicleBase;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.Action;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.equipment.action.ActionController;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.config.CombatBalance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

// FA Gundam
public abstract class PmgBaseEntity extends PomkotsVehicleBase {
    public static final float DEFAULT_SCALE = 1.0f;

    @Override
    protected String getMechName() {
        return "base";
    }

    @Override
    protected boolean useEnergy(int dec) {
        dec = dec * 2;
        return super.useEnergy(dec);
    }

    @Override
    protected void setupProperties() {
        this.setMaxUpStep(5.0F);
    }

    public PmgBaseEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    protected int inAirTicks = 0;
    protected boolean previousMode = true;

    protected boolean previousOnground = true;

    @Override
    public void tick() {
        super.tick();

        if (this.onGround()) {
            inAirTicks = 0;
        } else {
            inAirTicks++;
        }

        if(this.isServerSide()) {
            handleCollisionWithProjectiles();
            handleCollisionWithLivingEntities();
//            handleCollisionWithBlocks();
        }
        previousMode = this.isMainMode();
        previousOnground = this.onGround();
    }

    protected void handleCollisionWithProjectiles() {
        // ボスのAABB（現在の位置からの範囲）
        AABB bossBoundingBox = this.getBoundingBox();

        // ボスの近くに存在するProjectileエンティティを取得
        List<PomkotsThrowableProjectile> projectiles = this.level().getEntitiesOfClass(
                PomkotsThrowableProjectile.class, // Projectileエンティティのクラス
                bossBoundingBox.inflate(1.0D), // 判定範囲を少し拡大
                projectile -> !projectile.isRemoved() // 削除されていないエンティティのみ
        );

        for (PomkotsThrowableProjectile projectile : projectiles) {
            if (bossBoundingBox.intersects(projectile.getBoundingBox())) {
                projectile.onHitEntityPublic(this);
            }
        }
    }

    protected boolean isMoving() {
        var vel = this.getDeltaMovement();
        return Mth.abs((float)vel.x) > 0 || Mth.abs((float)vel.z) > 0 || Mth.abs((float)vel.y) > 0;
    }

    protected void handleCollisionWithBlocks() {
        var driver = this.getDrivingPassenger();

        if (PomkotsMechs.CONFIG.enableEntityBlockDestruction && driver != null && isMoving()) {
            // ロボットのAABBを取得
            AABB bossBoundingBox = this.getBoundingBox().inflate(3, 0, 3);
            bossBoundingBox.setMaxY(bossBoundingBox.minY + 10);
            breakBlocksInAABB(bossBoundingBox);
        }
    }

    protected void breakBlocksInAABB(AABB aabb) {
        // 範囲の座標を計算
        int minX = Mth.floor(aabb.minX);
        int maxX = Mth.floor(aabb.maxX);
        int minZ = Mth.floor(aabb.minZ);
        int maxZ = Mth.floor(aabb.maxZ);
        int minY = Mth.floor(aabb.minY);
        int maxY = Mth.floor(aabb.maxY);

        // 範囲内のブロックをループ処理
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = minY; y <= maxY; y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = level().getBlockState(pos);

                    // ブロックが破壊可能か確認
                    if (!blockState.isAir()) {
                        level().destroyBlock(pos, false, this);
                    }
                }
            }
        }
    }

    protected void handleCollisionWithLivingEntities() {
        var driver = this.getDrivingPassenger();

        if (driver != null && isMoving()) {
            // ボスのAABB（現在の位置からの範囲）
            AABB bossBoundingBox = this.getBoundingBox().setMaxY(this.getBoundingBox().minY + 3);

            List<LivingEntity> ents = this.level().getEntitiesOfClass(
                    LivingEntity.class, // Projectileエンティティのクラス
                    bossBoundingBox.inflate(1.0D), // 判定範囲を少し拡大
                    entity -> !(entity instanceof PmgBaseEntity) // 削除されていないエンティティのみ
            );


            for (LivingEntity ent : ents) {
                if (bossBoundingBox.intersects(ent.getBoundingBox())) {
                    var kbVel = ent.position().vectorTo(this.position()).normalize();
                    DamageSource ds;
                    if (driver instanceof Player p) {
                        ds = this.damageSources().playerAttack(p);
                    } else {
                        ds = this.damageSources().generic();
                    }

                    ent.knockback(4, kbVel.x, kbVel.z);
                    ent.hurt(ds, 20);
                }
            }
        }
    }

    public static final int ACT_POSE = 2;
    public static final int ACT_OPENHATCH = 3;
    public static final int ACT_CLOSEHATCH = 4;
    public static final int ACT_KNEEL = 5;
    public static final int ACT_STAND = 6;

    @Override
    protected void registerActions() {
        super.registerActions();
        this.actionController.getAction(ACT_EVASION).maxChargeTime = 2;
        this.actionController.getAction(ACT_EVASION).maxFireTime = 18;
        this.actionController.registerAction(ACT_POSE, new Action(20, 60, 60), ActionController.ActionType.R_ARM_SUB);
        this.actionController.registerAction(ACT_OPENHATCH, new Action(20, 10, 10), ActionController.ActionType.L_ARM_SUB);
        this.actionController.registerAction(ACT_CLOSEHATCH, new Action(20, 10, 10), ActionController.ActionType.R_SHL_SUB);
        this.actionController.registerAction(ACT_KNEEL, new Action(10, 10, 10), ActionController.ActionType.L_SHL_SUB);
        this.actionController.registerAction(ACT_STAND, new Action(10, 10, 10), ActionController.ActionType.L_SHL_SUB);

        this.registerCombatActions();
    }

    protected void resetActionExceptCooltime(Action a) {
        a.currentChargeTime = 0;
        a.currentFireTime = 0;
    }

    protected abstract void registerCombatActions();

    @Override
    protected void applyPlayerInputWeapons(DriverInput driverInput) {
        if (this.isMainMode()) {
            if (!previousMode) {
                this.actionController.getAction(ACT_STAND).startAction();

                if (this.isClientSide()) {
                    this.playSoundEffect(PomkotsMechsExtension.SE_GBOOT_EVENT.get());
                }
            } else if (this.actionController.getAction(ACT_STAND).isInAction()) {
                //NOP
            } else {
                this.applyPlayerInputWeaponsMainMode(driverInput);
            }
        } else {
            if (previousMode) {
                this.actionController.getAction(ACT_KNEEL).startAction();
                if (this.isClientSide()) {
                    this.playSoundEffect(PomkotsMechsExtension.SE_START.get());
                }
            } else if (this.actionController.getAction(ACT_KNEEL).isInAction()) {
                //NOP
            } else {
                this.applyPlayerInputWeaponsSubMode(driverInput);
            }
        }
    }

    protected void startEvasion() {
        if (this.isServerSide()) {
            Vec3 vel;
            if (this.forwardIntention == 0.0F && this.sidewayIntention == 0.0F) {
                vel = new Vec3(0.0, 0.0, 1.0);
            } else {
                vel = (new Vec3((double)this.sidewayIntention, 0.0, (double)this.forwardIntention)).normalize();
            }

            vel = vel.yRot((float)Math.toRadians(-1.0 * (double)this.getYRot()));
            double distance = getEvasionSpeed();
            vel = vel.scale(distance);
            this.push(vel.x, vel.y, vel.z);
        }
    }

    protected void applyPlayerInputJump(DriverInput driverInput) {
        if (driverInput.isJumpPressed() && this.onGround()) {
            this.actionController.getAction(1).startAction();
        }

        if (this.actionController.getAction(1).isOnFire() && this.isServerSide()) {
            this.push(0.0, getJumpInitialSpped(), 0.0);
        }
    }

    protected void applyPlayerInputInAirActions(DriverInput driverInput) {
        if (!this.onGround()) {
            if (driverInput.isJumpPressed()) {
                this.setNoGravity(true);
                if (this.useEnergy(5)) {
                    if (this.isServerSide() && this.getDeltaMovement().y() < getJumpContinueSpped()) {
                        this.push(0.0, 0.3, 0.0);
                    }
                } else if (this.isServerSide()) {
                    this.push(0.0, -0.17640000343322754, 0.0);
                }
            } else if (this.isServerSide()) {
                this.push(0.0, -0.17640000343322754, 0.0);
            }
        } else {
            this.setNoGravity(false);
        }
    }

    protected float getEvasionSpeed() {
        return 7.125F;
    }

    protected float getJumpInitialSpped() {
        return 2.0F;
    }

    protected float getJumpContinueSpped() {
        return 0.7F;
    }

    protected abstract void applyPlayerInputWeaponsMainMode(DriverInput driverInput);

    protected void applyPlayerInputWeaponsSubMode(DriverInput driverInput) {
        if (driverInput.isWeaponRightHandPressed()) {
//            this.actionController.getAction(ACT_OPENHATCH).startAction();
        } else if (driverInput.isWeaponLeftHandPressed()) {
//            this.actionController.getAction(ACT_CLOSEHATCH).startAction();
        } else if (driverInput.isWeaponRightShoulderPressed()) {
            this.actionController.getAction(ACT_POSE).startAction();
        } else if (driverInput.isWeaponLeftShoulderPressed()) {
            this.actionController.getAction(ACT_KNEEL).startAction();
        }
    }

    @Override
    protected void fireWeapons() {
        //NOP
    }

    protected float[] getShootingAngle(Entity bullet, boolean useLockTarget) {
        double xRot = 0;
        double yRot = 0;

        var driver = this.getDrivingPassenger();
        var lookAngle = driver.getLookAngle();

        xRot = -Math.toDegrees(Math.asin(lookAngle.y)) - 7.5;
        yRot = Math.toDegrees(Math.atan2(lookAngle.z, lookAngle.x)) - 90.0;

        return new float[]{(float)xRot, (float)yRot};
    }

    protected void fireSaber(Level level) {
        this.fireSaber(level, CombatBalance.BASE_DAMAGE_SABER);
    }

    protected void fireSaber(Level level, float damage) {
        this.fireSaber(level, damage, 10);
    }

    protected void fireSaber(Level level, float damage, float knockBack) {
        this.fireSaber(level, damage, knockBack, 0);
    }

    protected void fireSaber(Level level, float damage, float knockBack, float yaxisKnockBack) {
        if (this.isServerSide()) {
            Entity driver = this.getDrivingPassenger();

            fireSaberBreakBlock(level, this, 20, 90);

            var pilePos1 = new Vec3(6.5, 18F, 36F).yRot((float) Math.toRadians((-1.0) * this.getYRot())).add(this.position());
            var pilePos2 = new Vec3(-6.5, -3F, -2F).yRot((float) Math.toRadians((-1.0) * this.getYRot())).add(this.position());

            var kbVel = new Vec3(0, yaxisKnockBack, -10F).yRot((float) Math.toRadians((-1.0) * this.getYRot()));
            AABB aabb = new AABB(pilePos1, pilePos2);
            for (var ent : level.getEntities(null, aabb)) {
                if (isSelf(ent)) {
                    continue;
                }

                if (ent instanceof LivingEntity le) {
                    le.knockback(knockBack, kbVel.x, kbVel.z);

                    DamageSource ds;
                    if (driver instanceof Player p) {
                        ds = this.damageSources().playerAttack(p);
                    } else {
                        ds = this.damageSources().generic();
                    }
                    le.hurt(ds, damage);
                }
            }
        }
    }

    protected void fireSaberBreakBlock(Level level, Entity bossEntity, int radius, double angleDegrees) {
        if (PomkotsMechs.CONFIG.enableEntityBlockDestruction) {
            // ボスの位置と向き
            Vec3 bossPosition = bossEntity.position();
            Vec3 lookDirection = bossEntity.getLookAngle().normalize();

            // 扇形の範囲を計算
            double angleRadians = Math.toRadians(angleDegrees / 2); // 半分の角度
            double cosMaxAngle = Math.cos(angleRadians); // 扇形の境界線方向のcos値

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    // 中心からの相対位置
                    Vec3 offset = new Vec3(x, 0, z);

                    // 距離チェック
                    if (offset.length() > radius) {
                        continue;
                    }

                    // offsetを正規化して、視線方向との角度を判定
                    Vec3 normalizedOffset = offset.normalize();
                    double dotProduct = lookDirection.dot(normalizedOffset);

                    // cos値を用いて扇形内か判定
                    if (dotProduct >= cosMaxAngle) {
                        for (int y = 0; y < 24; y++) {
                            // 扇形内のブロックの破壊
                            BlockPos pos = new BlockPos(
                                    (int) (bossPosition.x + x),
                                    (int) (bossPosition.y + y),
                                    (int) (bossPosition.z + z)
                            );

                            BlockState blockState = level.getBlockState(pos);

                            // ブロックが破壊可能か確認
                            if (!blockState.isAir()) {
                                level.destroyBlock(pos, false, bossEntity); // ドロップ有効で破壊
                            }
                        }
                    }
                }
            }
        }
    }
//
//    @Override
//    public void travel(Vec3 pos) {
//        if (this.isAlive() && this.isVehicle()) {
//            if (this.isMainMode() && !this.actionController.getAction(ACT_STAND).isInAction()) {
//                var pilot = this.getDrivingPassenger();
//
//                // ROTATE Vehicle
//                this.setYRot(pilot.getYRot());
//                this.yRotO = this.getYRot();
//                this.setXRot(pilot.getXRot() * 0.5F);
//                this.setRot(this.getYRot(), this.getXRot());
//                this.setYBodyRot(this.getYRot());
//                this.setYHeadRot(this.getYRot());
//
//
//                // BOOST
////                if (isServerSide()) {
//                    if (this.actionController.isBoost()) {
//                        PomkotsMechsExtension.LOGGER.info(this.getRunSpeed()+"");
//                        this.setSpeed(this.getRunSpeed());
//                    } else {
//                        this.setSpeed(this.getWalkSpeed());
//                    }
////                }
//
//                float f = pilot.xxa * 0.5F * this.getSpeed();
//                float f1 = pilot.zza * 0.5F * this.getSpeed();
//
//                super.travel(new Vec3(f, pos.y, f1));
//            }
//        } else {
//            super.travel(pos);
//        }
//    }

    @Override
    public void travel(Vec3 pos) {
        if (this.isAlive() && this.isVehicle()) {
            if (this.isMainMode() && !this.actionController.getAction(ACT_STAND).isInAction()) {
                super.travel(pos);
            }
        } else {
            super.travel(pos);
        }
    }

                @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "hatch", 1, event -> {
            if (this.actionController.getAction(ACT_OPENHATCH).isInAction()) {
                if (this.actionController.getAction(ACT_OPENHATCH).isOnStart()) {
                    event.getController().forceAnimationReset();
                }
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".cockpit_open"));
            } else if (this.actionController.getAction(ACT_CLOSEHATCH).isInAction()) {
                if (this.actionController.getAction(ACT_CLOSEHATCH).isOnStart()) {
                    event.getController().forceAnimationReset();
                }
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".cockpit_close"));
            } else {
                return PlayState.CONTINUE;
            }
        }));

        registerMoveActions(controllers);

        controllers.add(new AnimationController<>(this, "fly", 10, event -> {
            if (event.getAnimatable().onGround()) {
                if (justLanded(event.getAnimatable())) {
                    event.getController().forceAnimationReset();
                    return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".onground"));
                } else {
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".nop"));
                }
            } else {
                if (inAirTicks > 10) {
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".flylegs"));
                } else {
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".nop"));
                }
            }
        }).setSoundKeyframeHandler(state -> {
            if ("jump".equals(state.getKeyframeData().getSound())) {
                this.playSoundEffect(PomkotsMechsExtension.SE_JUMP.get());
            };
            if ("start".equals(state.getKeyframeData().getSound())) {
                this.playSoundEffect(PomkotsMechsExtension.SE_START.get());
            };
        }));

        controllers.add(new AnimationController<>(this, "rotation", 4, event -> {
            if (event.isMoving()) {
                if (false && this.isNoGravity() && !this.actionController.isBoost() && !this.actionController.getAction(ACT_EVASION).isInAction()) {
                    if (this.forwardIntention > 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".flyforward"));
                    } else if (forwardIntention < 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".flybackward"));
                    } else if (this.sidewayIntention < 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".flyright"));
                    } else if (sidewayIntention > 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".flyleft"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".nop"));
                    }
                } else {
                    if (this.sidewayIntention < 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".right"));
                    } else if (sidewayIntention > 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".left"));
                    } else if (forwardIntention < 0) {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".back"));
                    }  else {
                        return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".nop"));
                    }
                }
            } else {
                if (!this.actionController.isInActionAll()) {
                    event.getController().forceAnimationReset();
                }
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".nop"));
            }
        }));
    }

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
                            return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".evasion_front"));
                        }
                    }
                }
                return PlayState.CONTINUE;
            }

//            if (justLanded(event.getAnimatable())) {
//                event.getController().forceAnimationReset();
//                return event.setAndContinue(RawAnimation.begin().thenPlay("animation." + getMechName() + ".onground"));
//            }

            if (event.isMoving()) {
                if (event.getAnimatable().onGround()) {
                    if (this.actionController.isBoost()) {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".runLower"));
                    } else {
                        return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".walk"));
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
                            return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".runLower"));
                        } else {
                            return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".walk"));
                        }
                    }
                }
            } else {
                if (this.yRotO != this.getYRot()) {
                    return event.setAndContinue(RawAnimation.begin().thenLoop("animation." + getMechName() + ".walk"));
                } else {
                    event.getController().forceAnimationReset();
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation." + getMechName() + ".idle"));
                }
            }
        }).setSoundKeyframeHandler(state -> {
            registerAnimationSoundHandlers(state);
        }));
    }

    protected void registerAnimationSoundHandlers(SoundKeyframeEvent state) {
        if ("gwalk2".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_GWALK2_EVENT.get());
        }
        if ("jump".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_JUMP.get());
        }
        if ("dash".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_DASH.get());
        }

        if ("start".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_START.get());
        };
        if ("shoot".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_SHOOT.get());
        }
        if ("saberstart".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_SABERSTART.get());
        }
        if ("saber".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_SABER.get());
        }
        if ("missile".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_MISSILE.get());
        }
        if ("bazooka".equals(state.getKeyframeData().getSound())) {
            this.playSoundEffect(PomkotsMechsExtension.SE_BAZOOKA.get());
        }
    }

    @Override
    protected boolean justLanded(Entity ent) {
        return !previousOnground && this.onGround();
//        return ent.onGround() && ent.getDeltaMovement().y == 0.0 && ent.yOld - ent.getY() > 3;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 15.5F;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        super.getDismountLocationForPassenger(passenger);

        float height;
        if (this.isMainMode()) {
            height =  15.5F;
        } else {
            height =  5.5F;
        }
        return new Vec3(this.getX(), this.getY() + height, this.getZ());
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount * 0.1F);
    }

    @Override
    public boolean shouldRender(double a, double b, double c) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double e) {
        return true;
    }

    protected void playSoundEffect(SoundEvent event) {
        this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), event, SoundSource.PLAYERS, 1.0F, 1.0F, false);
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 10) {
            this.remove(RemovalReason.KILLED);
            if (isServerSide()) {
                var level = this.level();
                ExplosionEntity e = new ExplosionEntity(PomkotsMechs.EXPLOSION.get(), level);
                e.setPos(this.position().x, this.position().y + 5, this.position().z);
                level.addFreshEntity(e);
            }
        }
    }

    protected Vec3 mainCameraPosition = null;

    public Vec3 getMainCameraPosition() {
        if (mainCameraPosition == null) {
            return new Vec3(this.position().x, this.position().y + 18, this.position().z);
        } else {
            return mainCameraPosition;
        }
    }

    public void setMainCameraPosition(Vec3 pos) {
        this.mainCameraPosition = pos;
    }
}
