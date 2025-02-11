package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge03Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz01Entity;
import net.minecraft.world.level.Level;

public class CoreStonePMGZ01Item extends CoreStonePMGE01Item {
    public CoreStonePMGZ01Item(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmgz01Entity(PomkotsMechsExtension.PMGZ01.get(), world);
    }
}
