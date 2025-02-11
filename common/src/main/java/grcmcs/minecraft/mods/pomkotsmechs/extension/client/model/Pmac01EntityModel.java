package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac02Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmac01EntityModel extends GeoModel<Pmac01Entity> {
    @Override
    public ResourceLocation getAnimationResource(Pmac01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmac01.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmac01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmac01.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmac01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmac01.png");
    }
}
