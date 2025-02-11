package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge03Entity;
import net.minecraft.world.level.Level;

public class CoreStonePMGE03Item extends CoreStonePMGE01Item {
    public CoreStonePMGE03Item(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmge03Entity(PomkotsMechsExtension.PMGE03.get(), world);
    }
}
