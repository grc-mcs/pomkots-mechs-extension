package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.PomkotsMechs;
import grcmcs.minecraft.mods.pomkotsmechs.entity.projectile.BulletMiddleEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BeamEntityModel extends GeoModel<BeamEntity> {
    @Override
    public ResourceLocation getAnimationResource(BeamEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/beam.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(BeamEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/beam.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeamEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/projectile/beam.png");
    }
}
