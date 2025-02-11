package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.cockpit;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import net.minecraft.resources.ResourceLocation;

public class CockpitAHudModel extends CockpitHudModel {
    @Override
    public ResourceLocation getAnimationResource(CockpitHudAnimatable animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/cockpita.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(CockpitHudAnimatable animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/cockpita.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CockpitHudAnimatable animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/entity/cockpita.png");
    }
}
