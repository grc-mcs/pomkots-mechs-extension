package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.BeamLargeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BeamLargeEntityModel extends GeoModel<BeamLargeEntity> {
    @Override
    public ResourceLocation getAnimationResource(BeamLargeEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/beamlarge.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(BeamLargeEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/beamlarge.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeamLargeEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/projectile/beamlarge.png");
    }
}
