package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz03Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmgz03EntityModel extends GeoModel<Pmgz03Entity> {
    @Override
    public ResourceLocation getAnimationResource(Pmgz03Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmgz03.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmgz03Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmgz03.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmgz03Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmgz03.png");
    }
}
