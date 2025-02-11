package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgx01Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmgx01EntityModel extends GeoModel<Pmgx01Entity> {
    @Override
    public ResourceLocation getAnimationResource(Pmgx01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmgx01.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmgx01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmgx01.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmgx01Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmgx01.png");
    }
}
