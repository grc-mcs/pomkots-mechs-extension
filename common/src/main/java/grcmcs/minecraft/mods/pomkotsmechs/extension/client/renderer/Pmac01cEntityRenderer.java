package grcmcs.minecraft.mods.pomkotsmechs.extension.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grcmcs.minecraft.mods.pomkotsmechs.client.renderer.RenderUtils;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.Pmac01EntityModel;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.Pmac01cEntityModel;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac01cEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class Pmac01cEntityRenderer extends GeoEntityRenderer<Pmac01cEntity> {
    public Pmac01cEntityRenderer(EntityRendererProvider.Context renderManager) {
        this(renderManager, new Pmac01cEntityModel());
    }

    public Pmac01cEntityRenderer(EntityRendererProvider.Context renderManager, Pmac01cEntityModel model) {
        super(renderManager, model);
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void preApplyRenderLayers(PoseStack poseStack, Pmac01cEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        this.scaleHeight = Pmac01cEntity.DEFAULT_SCALE;
        this.scaleWidth = Pmac01cEntity.DEFAULT_SCALE;

        super.preApplyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);

    }

    @Override
    public void actuallyRender(PoseStack poseStack, Pmac01cEntity animatable, BakedGeoModel model, RenderType renderType,
                               MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
                               int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType,
                bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, alpha);

        if (animatable.getDrivingPassenger() != null) {
            animatable.setClientSeatPos(getSeatPosition(model, animatable, Pmac01cEntity.DEFAULT_SCALE));
            animatable.setMainCameraPosition(MSRenderUtils.getMainCameraPosition(model, animatable, partialTick, Pmac01Entity.DEFAULT_SCALE));
        }

        RenderUtils.renderAdditionalHud(poseStack, animatable, this.entityRenderDispatcher.cameraOrientation(), bufferSource);
    }

    public static Vec3 getSeatPosition(BakedGeoModel model, PmgBaseEntity entity, float defaultScale) {
        if (model != null) {
            GeoBone vcSeat = model.getBone("seat").get();
            GeoBone vcRoot = model.getBone("root").get();

            Vector3d seatPos = vcSeat.getLocalPosition();
            Vector3d rootPos = vcRoot.getLocalPosition();

            return new Vec3(
                    seatPos.x * defaultScale - rootPos.x * defaultScale,
                    seatPos.y * defaultScale - rootPos.y * defaultScale - 9,
                    seatPos.z * defaultScale - rootPos.z * defaultScale);
        } else {
            return Vec3.ZERO;
        }
    }
}
