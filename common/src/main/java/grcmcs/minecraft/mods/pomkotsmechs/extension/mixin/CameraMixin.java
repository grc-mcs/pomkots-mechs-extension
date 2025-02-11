package grcmcs.minecraft.mods.pomkotsmechs.extension.mixin;

import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow
    protected abstract void setPosition(Vec3 vec3);

    @Inject(method = "setup", at = @At("TAIL"), cancellable = true)
    private void adjustCameraPosition(BlockGetter blockGetter, Entity viewEntity, boolean thirdPerson, boolean inverse, float partialTick, CallbackInfo ci) {
        if (viewEntity instanceof Player player) {
            // プレイヤーが巨大ロボに乗っているかを判定
            if (player.getVehicle() instanceof PmgBaseEntity pmg && !thirdPerson) {
                Vec3 bonePos = pmg.getMainCameraPosition();

                // カメラ位置をボーン位置に設定
                setPosition(bonePos);
            }
        }
    }
}
