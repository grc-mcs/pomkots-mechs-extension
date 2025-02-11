package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz01Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmgz01EntityModel extends GeoModel<Pmgz01Entity> {

    @Override
    public ResourceLocation getAnimationResource(Pmgz01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmgz01.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmgz01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmgz01.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmgz01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmgz01.png");
    }
//
//    @Override
//    public ResourceLocation getAnimationResource(Pmgz02Entity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/zaku2_solari.animation.json");
//    }
//
//    @Override
//    public ResourceLocation getModelResource(Pmgz02Entity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/zaku2_solari.geo.json");
//    }
//
//    @Override
//    public ResourceLocation getTextureResource(Pmgz02Entity animatable) {
//        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/zaku2_solari.png");
//    }
}
