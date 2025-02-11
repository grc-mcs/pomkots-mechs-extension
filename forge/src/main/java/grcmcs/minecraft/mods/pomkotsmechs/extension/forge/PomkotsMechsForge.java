package grcmcs.minecraft.mods.pomkotsmechs.extension.forge;

import dev.architectury.platform.forge.EventBuses;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtensionClient;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;

@Mod(PomkotsMechsExtension.MODID)
public final class PomkotsMechsForge {
    public PomkotsMechsForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        EventBuses.registerModEventBus(PomkotsMechsExtension.MODID, bus);
        PomkotsMechsExtension.initialize();


        DistExecutor.unsafeRunWhenOn(
                Dist.CLIENT,
                () ->
                        () -> {
                            PomkotsMechsExtensionClient.initialize();
                        });
    }
}
