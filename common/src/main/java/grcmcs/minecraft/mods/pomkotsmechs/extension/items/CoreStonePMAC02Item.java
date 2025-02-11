package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import net.minecraft.world.level.Level;

public class CoreStonePMAC02Item extends CoreStonePMGE01Item {
    public CoreStonePMAC02Item(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmac02Entity(PomkotsMechsExtension.PMAC02.get(), world);
    }
}
