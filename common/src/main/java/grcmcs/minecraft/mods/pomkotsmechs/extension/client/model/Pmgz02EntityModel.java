package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz02Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmgz02EntityModel extends GeoModel<Pmgz02Entity> {

    @Override
    public ResourceLocation getAnimationResource(Pmgz02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmgz02.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmgz02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmgz02.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmgz02Entity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmgz02.png");
    }
}
