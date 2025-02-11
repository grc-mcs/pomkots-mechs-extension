package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge03Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmge03EntityModel extends GeoModel<Pmge03Entity> {

    @Override
    public ResourceLocation getAnimationResource(Pmge03Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmge03.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmge03Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmge03.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmge03Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmge03.png");
    }
}
