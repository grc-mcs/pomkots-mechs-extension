package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge01Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmge01EntityModel extends GeoModel<Pmge01Entity> {

    @Override
    public ResourceLocation getAnimationResource(Pmge01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmge01.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmge01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmge01.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmge01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmge01.png");
    }

//    @Override
//    public ResourceLocation getAnimationResource(Pmge03Entity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/rx79.animation.json");
//    }
//
//    @Override
//    public ResourceLocation getModelResource(Pmge03Entity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/rx79.geo.json");
//    }
//
//    @Override
//    public ResourceLocation getTextureResource(Pmge03Entity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/rx79.png");
//    }
}
