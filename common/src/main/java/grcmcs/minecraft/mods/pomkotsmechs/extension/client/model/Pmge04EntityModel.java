package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge04Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmge04EntityModel extends GeoModel<Pmge04Entity> {

    @Override
    public ResourceLocation getAnimationResource(Pmge04Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/rgm.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmge04Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/RGM.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmge04Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/rgm.png");
    }
}
