package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz03Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz03yEntity;
import net.minecraft.world.level.Level;

public class CoreStonePMGZ03CItem extends CoreStonePMGE01Item {
    public CoreStonePMGZ03CItem(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmgz03yEntity(PomkotsMechsExtension.PMGZ03C.get(), world);
    }
}
