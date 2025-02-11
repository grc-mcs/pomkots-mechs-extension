package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BulletLargeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BulletLargeEntityModel extends GeoModel<BulletLargeEntity> {
    @Override
    public ResourceLocation getAnimationResource(BulletLargeEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/bulletlarge.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(BulletLargeEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/bulletlarge.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BulletLargeEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/projectile/bulletlarge.png");
    }
}
