package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge02Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmge02EntityModel extends GeoModel<Pmge02Entity> {

//    @Override
//    public ResourceLocation getAnimationResource(Pmge02cEntity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/excustom.animation.json");
//    }
//
//    @Override
//    public ResourceLocation getModelResource(Pmge02cEntity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/excustom.geo.json");
//    }
//
//    @Override
//    public ResourceLocation getTextureResource(Pmge02cEntity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/excustom.png");
//    }
    @Override
    public ResourceLocation getAnimationResource(Pmge02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmge02.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmge02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmge02.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmge02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmge02.png");
    }
}
