package grcmcs.minecraft.mods.pomkotsmechs.extension.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grcmcs.minecraft.mods.pomkotsmechs.client.renderer.RenderUtils;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.Pmge02EntityModel;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.Pmgx01EntityModel;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgx01Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class Pmgx01EntityRenderer extends GeoEntityRenderer<Pmgx01Entity> {
    public Pmgx01EntityRenderer(EntityRendererProvider.Context renderManager) {
        this(renderManager, new Pmgx01EntityModel());
    }

    public Pmgx01EntityRenderer(EntityRendererProvider.Context renderManager, Pmgx01EntityModel model) {
        super(renderManager, model);
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void preApplyRenderLayers(PoseStack poseStack, Pmgx01Entity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        this.scaleHeight = Pmgx01Entity.DEFAULT_SCALE;
        this.scaleWidth = Pmgx01Entity.DEFAULT_SCALE;

        super.preApplyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, Pmgx01Entity animatable, BakedGeoModel model, RenderType renderType,
                               MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
                               int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType,
                bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, alpha);

        if (animatable.getDrivingPassenger() != null) {
            animatable.setClientSeatPos(MSRenderUtils.getSeatPosition(model, animatable));
            animatable.setMainCameraPosition(MSRenderUtils.getMainCameraPosition(model, animatable, partialTick));
        }

        RenderUtils.renderAdditionalHud(poseStack, animatable, this.entityRenderDispatcher.cameraOrientation(), bufferSource);
    }
}
