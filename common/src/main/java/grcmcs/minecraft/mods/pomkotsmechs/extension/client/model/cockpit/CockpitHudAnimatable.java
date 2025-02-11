package grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.cockpit;

import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CockpitHudAnimatable implements GeoAnimatable {

    protected final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "attack", 2, event -> {
            Player p = Minecraft.getInstance().player;

            if (p != null && p.getVehicle() instanceof PmgBaseEntity veh) {
                if (veh.actionController.getAction(PmgBaseEntity.ACT_KNEEL).isInAction()) {
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.cockpit.close"));
                } else if (veh.actionController.getAction(PmgBaseEntity.ACT_STAND).isInAction()) {
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.cockpit.open"));
                } else if (veh.isSubMode()) {
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.cockpit.idle_closed"));
                } else {
                    return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.cockpit.idle"));
                }
            } else {
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.cockpit.idle"));
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }
}
