package grcmcs.minecraft.mods.pomkotsmechs.extension.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.BeamEntityModel;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.MachineGunBulletEntityModel;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.MachineGunBulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class MachineGunBulletEntityRenderer extends GeoEntityRenderer<MachineGunBulletEntity> {
    public MachineGunBulletEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MachineGunBulletEntityModel());

        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void preApplyRenderLayers(PoseStack poseStack, MachineGunBulletEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        this.scaleHeight = 2;
        this.scaleWidth = 2;
        super.preApplyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
    }
}
