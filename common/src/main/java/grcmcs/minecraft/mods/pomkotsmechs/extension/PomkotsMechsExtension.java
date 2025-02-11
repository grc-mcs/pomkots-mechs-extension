package grcmcs.minecraft.mods.pomkotsmechs.extension;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;

import dev.architectury.registry.registries.RegistrySupplier;
import grcmcs.minecraft.mods.pomkotsmechs.PomkotsMechs;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.projectile.*;
import grcmcs.minecraft.mods.pomkotsmechs.extension.entity.vehicle.*;
import grcmcs.minecraft.mods.pomkotsmechs.extension.items.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PomkotsMechsExtension {
	public static final String MODID = "pomkotsmechsextension";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}

	// ENTITIES -------------------------------------------------------------------------------------------

	public static  final  DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(MODID, Registries.ENTITY_TYPE);

	// Vehicles
	public static final RegistrySupplier<EntityType<Pmgz01Entity>> PMGZ01 = registerEntityType("pmgz01", Pmgz01Entity::new, MobCategory.CREATURE, 10F, 24F);
	public static final RegistrySupplier<EntityType<Pmgz02Entity>> PMGZ02 = registerEntityType("pmgz02", Pmgz02Entity::new, MobCategory.CREATURE, 10F, 24F);
	public static final RegistrySupplier<EntityType<Pmgz03Entity>> PMGZ03 = registerEntityType("pmgz03", Pmgz03Entity::new, MobCategory.CREATURE, 8F, 20F);
	public static final RegistrySupplier<EntityType<Pmgz03yEntity>> PMGZ03C = registerEntityType("pmgz03y", Pmgz03yEntity::new, MobCategory.CREATURE, 8F, 20F);

	public static final RegistrySupplier<EntityType<Pmge01Entity>> PMGE01 = registerEntityType("pmge01", Pmge01Entity::new, MobCategory.CREATURE, 10F, 24F);
	public static final RegistrySupplier<EntityType<Pmge02Entity>> PMGE02 = registerEntityType("pmge02", Pmge02Entity::new, MobCategory.CREATURE, 10F, 24F);
	public static final RegistrySupplier<EntityType<Pmge03Entity>> PMGE03 = registerEntityType("pmge03", Pmge03Entity::new, MobCategory.CREATURE, 8F, 20F);

	public static final RegistrySupplier<EntityType<Pmgx01Entity>> PMGX01 = registerEntityType("pmgx01", Pmgx01Entity::new, MobCategory.CREATURE, 10F, 24F);

	public static final RegistrySupplier<EntityType<Pmac01Entity>> PMAC01 = registerEntityType("pmac01", Pmac01Entity::new, MobCategory.CREATURE, 4F, 10F);
	public static final RegistrySupplier<EntityType<Pmac01cEntity>> PMAC01C = registerEntityType("pmac01c", Pmac01cEntity::new, MobCategory.CREATURE, 4F, 10F);

	public static final RegistrySupplier<EntityType<Pmac02Entity>> PMAC02 = registerEntityType("pmac02", Pmac02Entity::new, MobCategory.CREATURE, 4F, 10F);
	public static final RegistrySupplier<EntityType<Pmac02cEntity>> PMAC02C = registerEntityType("pmac02c", Pmac02cEntity::new, MobCategory.CREATURE, 4F, 10F);

	// Projectiles
	public static final RegistrySupplier<EntityType<MissileHorizontalEntity>> MISSILE = registerEntityType("missile", MissileHorizontalEntity::new, MobCategory.MISC, 1F, 1F);

	public static final RegistrySupplier<EntityType<BulletLargeEntity>> BULLETLARGE = registerEntityType("bulletlarge", BulletLargeEntity::new, MobCategory.MISC, 2F, 2F);
	public static final RegistrySupplier<EntityType<BeamEntity>> BEAM = registerEntityType("beam", BeamEntity::new, MobCategory.MISC, 2F, 2F);
	public static final RegistrySupplier<EntityType<BeamLargeEntity>> BEAMLARGE = registerEntityType("beamlarge",BeamLargeEntity::new, MobCategory.MISC, 2F, 2F);
	public static final RegistrySupplier<EntityType<MachineGunBulletEntity>> MACHINEGUNBULLET = registerEntityType("machinegunbullet",MachineGunBulletEntity::new, MobCategory.MISC, 2F, 2F);
	public static final RegistrySupplier<EntityType<ZakuBazookaEntity>> ZAKUBAZ = registerEntityType("zakubazu", ZakuBazookaEntity::new, MobCategory.MISC, 2F, 2F);

	private static <T extends Entity> RegistrySupplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
		return ENTITIES.register(name, () ->
				EntityType.Builder.of(factory, category)
						.sized(width, height)
						.build(id(name).toString()));
	}

	// PARTICLES -------------------------------------------------------------------------------------------

//	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(MODID, Registries.PARTICLE_TYPE);
//
//	public static final RegistrySupplier<SimpleParticleType> FIRE = PARTICLES.register("fire", () -> new PomkotsSimpleParticleType(false));
//	public static final RegistrySupplier<SimpleParticleType> MISSILE_SMOKE = PARTICLES.register("missilesmoke", () -> new PomkotsSimpleParticleType(false));
//	public static final RegistrySupplier<SimpleParticleType> EXPLOSION_CORE = PARTICLES.register("explosioncore", () -> new PomkotsSimpleParticleType(false));
//	public static final RegistrySupplier<SimpleParticleType> SPARK = PARTICLES.register("spark", () -> new PomkotsSimpleParticleType(false));
//
//	public static class PomkotsSimpleParticleType extends SimpleParticleType {
//		protected PomkotsSimpleParticleType(boolean bl) {
//			super(bl);
//		}
//	}

	// ITEMS ---------------------------------------------------------------------------------------------------

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MODID, Registries.ITEM);

	public static final RegistrySupplier<Item> CORESTONE_PMGE01 = ITEMS.register("corestone_pmge01", () -> new CoreStonePMGE01Item(new Item.Properties()));
	public static final RegistrySupplier<Item> CORESTONE_PMGE02 = ITEMS.register("corestone_pmge02", () -> new CoreStonePMGE02Item(new Item.Properties()));
	public static final RegistrySupplier<Item> CORESTONE_PMGE03 = ITEMS.register("corestone_pmge03", () -> new CoreStonePMGE03Item(new Item.Properties()));

	public static final RegistrySupplier<Item> CORESTONE_PMGZ01 = ITEMS.register("corestone_pmgz01", () -> new CoreStonePMGZ01Item(new Item.Properties()));
	public static final RegistrySupplier<Item> CORESTONE_PMGZ02 = ITEMS.register("corestone_pmgz02", () -> new CoreStonePMGZ02Item(new Item.Properties()));
	public static final RegistrySupplier<Item> CORESTONE_PMGZ03 = ITEMS.register("corestone_pmgz03", () -> new CoreStonePMGZ03Item(new Item.Properties()));
	public static final RegistrySupplier<Item> CORESTONE_PMGZ03C = ITEMS.register("corestone_pmgz03y", () -> new CoreStonePMGZ03CItem(new Item.Properties()));

	public static final RegistrySupplier<Item> CORESTONE_PMAC01 = ITEMS.register("corestone_pmac01", () -> new CoreStonePMAC01Item(new Item.Properties()));
	public static final RegistrySupplier<Item> CORESTONE_PMAC01C = ITEMS.register("corestone_pmac01c", () -> new CoreStonePMAC01CItem(new Item.Properties()));
	public static final RegistrySupplier<Item> CORESTONE_PMAC02 = ITEMS.register("corestone_pmac02", () -> new CoreStonePMAC02Item(new Item.Properties()));
	public static final RegistrySupplier<Item> CORESTONE_PMAC02C = ITEMS.register("corestone_pmac02c", () -> new CoreStonePMAC02CItem(new Item.Properties()));

	public static final RegistrySupplier<Item> CORESTONE_PMGX01 = ITEMS.register("corestone_pmgx01", () -> new CoreStonePMGX01Item(new Item.Properties()));


	// ITEMS
	public static final DeferredRegister<CreativeModeTab> ITEM_GROUPS = DeferredRegister.create(MODID, Registries.CREATIVE_MODE_TAB);
	public static final RegistrySupplier<CreativeModeTab> CUSTOM_TAB = ITEM_GROUPS.register(PomkotsMechsExtension.id("item_group"), () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, -1)
			.icon(() -> new ItemStack(CORESTONE_PMGE01.get()))
			.title(Component.translatable("itemGroup." + PomkotsMechsExtension.MODID))
			.displayItems((parameters, output) -> {
				output.accept(new ItemStack(CORESTONE_PMGE01.get()));
				output.accept(new ItemStack(CORESTONE_PMGE02.get()));
				output.accept(new ItemStack(CORESTONE_PMGE03.get()));

				output.accept(new ItemStack(CORESTONE_PMGZ01.get()));
				output.accept(new ItemStack(CORESTONE_PMGZ02.get()));
				output.accept(new ItemStack(CORESTONE_PMGZ03.get()));
				output.accept(new ItemStack(CORESTONE_PMGZ03C.get()));

				output.accept(new ItemStack(CORESTONE_PMAC01.get()));
				output.accept(new ItemStack(CORESTONE_PMAC01C.get()));
				output.accept(new ItemStack(CORESTONE_PMAC02.get()));
				output.accept(new ItemStack(CORESTONE_PMAC02C.get()));

				output.accept(new ItemStack(CORESTONE_PMGX01.get()));
			})
			.build()
	);

	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(PomkotsMechs.MODID, Registries.SOUND_EVENT);

	public static final RegistrySupplier<SoundEvent> SE_GBOOT_EVENT = SOUNDS.register(id("gboot"), () -> SoundEvent.createVariableRangeEvent(id("gboot")));
	public static final RegistrySupplier<SoundEvent> SE_GWALK_EVENT = SOUNDS.register(id("gwalk"), () -> SoundEvent.createVariableRangeEvent(id("gwalk")));
	public static final RegistrySupplier<SoundEvent> SE_GWALK2_EVENT = SOUNDS.register(id("gwalk2"), () -> SoundEvent.createVariableRangeEvent(id("gwalk2")));
	public static final RegistrySupplier<SoundEvent> SE_BAZOOKA = SOUNDS.register(id("bazooka"), () -> SoundEvent.createVariableRangeEvent(id("bazooka")));
	public static final RegistrySupplier<SoundEvent> SE_DASH = SOUNDS.register(id("dash"), () -> SoundEvent.createVariableRangeEvent(id("dash")));
	public static final RegistrySupplier<SoundEvent> SE_JUMP = SOUNDS.register(id("jump"), () -> SoundEvent.createVariableRangeEvent(id("jump")));
	public static final RegistrySupplier<SoundEvent> SE_MISSILE = SOUNDS.register(id("missile"), () -> SoundEvent.createVariableRangeEvent(id("missile")));
	public static final RegistrySupplier<SoundEvent> SE_SABER = SOUNDS.register(id("saber"), () -> SoundEvent.createVariableRangeEvent(id("saber")));
	public static final RegistrySupplier<SoundEvent> SE_SABERSTART = SOUNDS.register(id("saberstart"), () -> SoundEvent.createVariableRangeEvent(id("saberstart")));
	public static final RegistrySupplier<SoundEvent> SE_SHOOT = SOUNDS.register(id("shoot"), () -> SoundEvent.createVariableRangeEvent(id("shoot")));
	public static final RegistrySupplier<SoundEvent> SE_START = SOUNDS.register(id("start"), () -> SoundEvent.createVariableRangeEvent(id("start")));

	public static grcmcs.minecraft.mods.pomkotsmechs.config.PomkotsConfig CONFIG;

	public static void initialize() {
//		AutoConfig.register(grcmcs.minecraft.mods.pomkotsmechs.config.PomkotsConfig.class, GsonConfigSerializer::new);
//		CONFIG = AutoConfig.getConfigHolder(grcmcs.minecraft.mods.pomkotsmechs.config.PomkotsConfig.class).getConfig();

		ENTITIES.register();

		EntityAttributeRegistry.register(PMGZ01::get, Pmgz01Entity::createMobAttributes);
		EntityAttributeRegistry.register(PMGZ02::get, Pmgz02Entity::createMobAttributes);
		EntityAttributeRegistry.register(PMGZ03::get, Pmgz03Entity::createMobAttributes);
		EntityAttributeRegistry.register(PMGZ03C::get, Pmgz03yEntity::createMobAttributes);

		EntityAttributeRegistry.register(PMGE01::get, Pmge01Entity::createMobAttributes);
		EntityAttributeRegistry.register(PMGE02::get, Pmge02Entity::createMobAttributes);
		EntityAttributeRegistry.register(PMGE03::get, Pmge03Entity::createMobAttributes);

		EntityAttributeRegistry.register(PMAC01::get, Pmac01Entity::createMobAttributes);
		EntityAttributeRegistry.register(PMAC01C::get, Pmac01cEntity::createMobAttributes);
		EntityAttributeRegistry.register(PMAC02::get, Pmac02Entity::createMobAttributes);
		EntityAttributeRegistry.register(PMAC02C::get, Pmac02cEntity::createMobAttributes);

		EntityAttributeRegistry.register(PMGX01::get, Pmgx01Entity::createMobAttributes);

		ITEMS.register();
		ITEM_GROUPS.register();
		SOUNDS.register();
	}


}