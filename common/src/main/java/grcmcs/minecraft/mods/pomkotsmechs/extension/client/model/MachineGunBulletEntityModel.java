package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.MachineGunBulletEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MachineGunBulletEntityModel extends GeoModel<MachineGunBulletEntity> {
    @Override
    public ResourceLocation getAnimationResource(MachineGunBulletEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "animations/mbullet.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MachineGunBulletEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "geo/mbullet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MachineGunBulletEntity animatable) {
        return new ResourceLocation(PomkotsMechsExtension.MODID, "textures/projectile/mbullet.png");
    }
}
