package grcmcs.minecraft.mods.pomkotsmechs.extension.fabric.client;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtensionClient;
import net.fabricmc.api.ClientModInitializer;

public final class PomkotsMechsExtensionClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PomkotsMechsExtensionClient.initialize();
    }
}
