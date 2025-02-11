package grcmcs.minecraft.mods.pomkotsmechs.extension.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class PlayerMixin {
    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void re(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    private void re(Player arg, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}