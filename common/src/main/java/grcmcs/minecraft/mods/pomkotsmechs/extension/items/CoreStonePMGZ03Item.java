package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz03Entity;
import net.minecraft.world.level.Level;

public class CoreStonePMGZ03Item extends CoreStonePMGE01Item {
    public CoreStonePMGZ03Item(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmgz03Entity(PomkotsMechsExtension.PMGZ03.get(), world);
    }
}
