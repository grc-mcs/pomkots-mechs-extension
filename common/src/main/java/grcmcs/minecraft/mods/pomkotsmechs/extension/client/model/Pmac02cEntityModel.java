package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac01cEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac02cEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmac02cEntityModel extends GeoModel<Pmac02cEntity> {
    @Override
    public ResourceLocation getAnimationResource(Pmac02cEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmac02c.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmac02cEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmac02c.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmac02cEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmac02c.png");
    }
}
