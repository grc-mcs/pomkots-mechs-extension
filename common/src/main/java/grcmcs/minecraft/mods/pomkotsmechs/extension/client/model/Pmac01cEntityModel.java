package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac01cEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pmac01cEntityModel extends GeoModel<Pmac01cEntity> {
    @Override
    public ResourceLocation getAnimationResource(Pmac01cEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/pmac01c.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Pmac01cEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/pmac01c.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pmac01cEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/pmac01c.png");
    }
}
