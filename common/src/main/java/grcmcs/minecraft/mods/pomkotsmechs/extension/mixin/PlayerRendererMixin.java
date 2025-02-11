package grcmcs.minecraft.mods.pomkotsmechs.extension.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import grcmcs.minecraft.mods.pomkotsmechs.PomkotsMechs;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtensionClient;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.renderer.cockpit.CockpitHudRendererManager;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(method = "renderRightHand", at = @At("HEAD"))
    private void onRenderRightHand(PoseStack poseStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, CallbackInfo ci) {
        if (player != null && player.getVehicle() instanceof PmgBaseEntity vehicle) {
            // 腕の描画位置を調整
            poseStack.pushPose();

            // そのままだと腕の位置に連動しちゃうので、一回リセット
            poseStack.setIdentity();

            // 位置とサイズを調整
            poseStack.translate(0.5, -1.8, 0.1);
            poseStack.scale(1, 1f, 1f);

            // 角度を調整（なんもせんとカメラにくっついてきちゃう）
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.mulPose(Axis.XP.rotationDegrees(-player.getXRot() / 2));

            // カスタムエフェクトの追加 (例: 光のエフェクト)
//            RenderSystem.enableBlend();
//            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            // 実描画
            CockpitHudRendererManager.renderCockpit(poseStack, buffer, light, vehicle, player);

            poseStack.popPose();
        }
    }

    //    @Inject(method = "render", at = @At("TAIL"))
    // これはワークせんやった…どうやってもFPの時にレンダリングがなされぬ…
    private void re(AbstractClientPlayer player, float f, float g, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
//        // 腕の描画位置を調整
//        poseStack.pushPose();
//
//        poseStack.translate(0.2, -0.5, -0.3); // X, Y, Zを調整
//        poseStack.scale(1.2f, 1.2f, 1.2f); // サイズを調整
//
//        // カスタムエフェクトの追加 (例: 光のエフェクト)
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//
//        ResourceLocation texture = new ResourceLocation("modid", "textures/entity/cockpit.png");
//        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(texture));
//
//        PomkotsMechsExtensionClient.GundamHud.RENDERER.render(poseStack, PomkotsMechsExtensionClient.GundamHud.ANIM, buffer, RenderType.entityTranslucent(texture), vertexConsumer, light);
//
//        // ... 描画ロジックを追加する場合
//        poseStack.popPose();
    }
}