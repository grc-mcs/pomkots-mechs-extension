package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac02cEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import net.minecraft.world.level.Level;

public class CoreStonePMAC02CItem extends CoreStonePMGE01Item {
    public CoreStonePMAC02CItem(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmac02cEntity(PomkotsMechsExtension.PMAC02C.get(), world);
    }
}
