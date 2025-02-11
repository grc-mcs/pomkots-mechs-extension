package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.cockpit;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CockpitBHudModel extends CockpitHudModel {
    @Override
    public ResourceLocation getAnimationResource(CockpitHudAnimatable animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/cockpitb.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(CockpitHudAnimatable animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/cockpitb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CockpitHudAnimatable animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/cockpit.png");
    }
}
