package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz02Entity;
import net.minecraft.world.level.Level;

public class CoreStonePMGZ02Item extends CoreStonePMGE01Item {
    public CoreStonePMGZ02Item(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmgz02Entity(PomkotsMechsExtension.PMGZ02.get(), world);
    }
}
