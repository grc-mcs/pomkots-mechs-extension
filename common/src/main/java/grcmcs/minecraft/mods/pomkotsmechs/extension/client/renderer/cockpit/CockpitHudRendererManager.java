package grcmcs.minecraft.mods.pomkotsmechs.extension.client.renderer.cockpit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.model.cockpit.*;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class CockpitHudRendererManager {
    private static Map<Class, Pair<CockpitHudAnimatable, CockpitHudRenderer>> cockpitRenderMap = new HashMap<>();

    static {
        Pair<CockpitHudAnimatable, CockpitHudRenderer> pitDefault = new Pair<>(new CockpitHudAnimatable(), new CockpitHudRenderer(new CockpitHudModel()));
        Pair<CockpitHudAnimatable, CockpitHudRenderer> pitTypeA = new Pair<>(new CockpitAHudAnimatable(), new CockpitHudRenderer(new CockpitAHudModel()));
        Pair<CockpitHudAnimatable, CockpitHudRenderer> pitTypeB = new Pair<>(new CockpitBHudAnimatable(), new CockpitHudRenderer(new CockpitBHudModel()));

        cockpitRenderMap.put(Pmgz01Entity.class, pitTypeA);
        cockpitRenderMap.put(Pmgz02Entity.class, pitTypeA);
        cockpitRenderMap.put(Pmgz03Entity.class, pitTypeB);
        cockpitRenderMap.put(Pmgz03yEntity.class, pitTypeB);

        cockpitRenderMap.put(Pmge01Entity.class, pitTypeB);
        cockpitRenderMap.put(Pmge02Entity.class, pitTypeB);
        cockpitRenderMap.put(Pmge03Entity.class, pitTypeB);
    }

    public static void renderCockpit(PoseStack poseStack, MultiBufferSource buffer, int light, PmgBaseEntity vehicle, Player player) {
        var pit = cockpitRenderMap.get(vehicle.getClass());

        if (pit == null) {
            pit = cockpitRenderMap.get(Pmge03Entity.class);
        }

        pit.getSecond().render(poseStack, pit.getFirst(), buffer, null, null, 130);
    }
}
