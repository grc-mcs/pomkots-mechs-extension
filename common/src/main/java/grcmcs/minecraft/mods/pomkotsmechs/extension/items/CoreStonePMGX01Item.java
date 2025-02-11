package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgx01Entity;
import net.minecraft.world.level.Level;

public class CoreStonePMGX01Item extends CoreStonePMGE01Item {
    public CoreStonePMGX01Item(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmgx01Entity(PomkotsMechsExtension.PMGX01.get(), world);
    }
}
