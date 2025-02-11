package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz03yEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmgz03yEntityModel extends GeoModel<Pmgz03yEntity> {

    @Override
    public ResourceLocation getAnimationResource(Pmgz03yEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmgz03c.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmgz03yEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmgz03c.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmgz03yEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmgz03c.png");
    }
}
