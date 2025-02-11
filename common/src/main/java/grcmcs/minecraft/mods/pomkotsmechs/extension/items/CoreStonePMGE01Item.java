package grcmcs.minecraft.mods.pomkotsmechs.extension.items;

import grcmcs.minecraft.mods.pomkotsmechs.PomkotsMechs;
import grcmcs.minecraft.mods.pomkotsmechs.entity.vehicle.Pmv01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.PomkotsMechsExtension;
import grcmcs.minecraft.mods.pomkotsmechs.extension.config.CombatBalance;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.PmgBaseEntity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmge01Entity;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.Pmgz02Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CoreStonePMGE01Item extends Item {
    // 使用済みフラグのキー
    private static final String USED_KEY = PomkotsMechsExtension.MODID + ":corestone:used";

    public CoreStonePMGE01Item(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag nbt = stack.getOrCreateTag();

        // 1回目の使用：ロボット召喚
        if (!nbt.getBoolean(USED_KEY)) {
            if (!world.isClientSide) { // サーバー側のみで実行
                summonRobot(world, player);
            }
            nbt.putBoolean(USED_KEY, true); // 使用済みに設定
            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
        } else {
            return InteractionResultHolder.fail(stack); // ロボットがいない場合は失敗
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof PmgBaseEntity robot) {
            // ロボットの体力を回復
            robot.heal(CombatBalance.BASE_HEALTH / 10);
            robot.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1, 3)); // 回復エフェクト

            return false;
        }
        return super.hurtEnemy(stack, target, attacker);
    }
    // ロボットを召喚するメソッド
    private void summonRobot(Level world, Player player) {
        PmgBaseEntity robot = createInstance(world);
        robot.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
        world.addFreshEntity(robot);
    }

    protected PmgBaseEntity createInstance(Level world) {
        return new Pmge01Entity(PomkotsMechsExtension.PMGE01.get(), world);
    }
}
