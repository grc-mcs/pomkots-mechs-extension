package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmac01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz03Entity;
import net.minecraft.world.level.Level;

public class CoreStonePMAC01Item extends CoreStonePMGE01Item {
    public CoreStonePMAC01Item(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmac01Entity(PomkotsMechsExtension.PMAC01.get(), world);
    }
}
