package grcmcs.minecraft.mods.pomkotsmechs.extension.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.BeamEntityModel;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.BeamLargeEntityModel;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamLargeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class BeamLargeEntityRenderer extends GeoEntityRenderer<BeamLargeEntity> {
    public BeamLargeEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BeamLargeEntityModel());

        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void preApplyRenderLayers(PoseStack poseStack, BeamLargeEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        this.scaleHeight = 20;
        this.scaleWidth = 20;
        super.preApplyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);

        model.getBone("root").get().setRotX((float)Math.toRadians(animatable.getXRot()));
        model.getBone("root").get().setRotY((float)Math.toRadians(animatable.getYRot()));
    }
}
