package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge02Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge03Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CoreStonePMGE02Item extends CoreStonePMGE01Item {
    public CoreStonePMGE02Item(Properties properties) {
        super(properties);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmge02Entity(PomkotsMechsExtension.PMGE02.get(), world);
    }
}
