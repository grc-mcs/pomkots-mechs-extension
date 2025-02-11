package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac02Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmac02EntityModel extends GeoModel<Pmac02Entity> {
    @Override
    public ResourceLocation getAnimationResource(Pmac02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmac02.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmac02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmac02.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmac02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmac02.png");
    }
}
