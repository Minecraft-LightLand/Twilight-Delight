package dev.xkmc.twilightdelight.init;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.providers.ProviderType;
import dev.ghen.thirst.Thirst;
import dev.xkmc.l2core.init.L2TagGen;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.simple.Reg;
import dev.xkmc.twilightdelight.compat.ThirstCompat;
import dev.xkmc.twilightdelight.init.data.*;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightPie;
import dev.xkmc.twilightdelight.util.StoveAddBlockUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.locating.IModFile;
import org.slf4j.Logger;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Mod(TwilightDelight.MODID)
@EventBusSubscriber(modid = TwilightDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TwilightDelight {

	public static final String MODID = "twilightdelight";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Reg REG = new Reg(MODID);
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public TwilightDelight() {
		TDBlocks.register();
		TDItems.register();
		DelightFood.register();
		DelightPie.register();
		/* TODO neapolitan
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			NeapolitanFood.register();
			NeapolitanCakes.register();
			MinecraftForge.EVENT_BUS.register(NeapolitanEventListeners.class);
		}*/
		TDEffects.register();
		TDRecipes.register();
		TDModConfig.init();
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			StoveAddBlockUtil.addBlock(ModBlockEntityTypes.STOVE.get(), TDBlocks.MAZE_STOVE.get());
			StoveAddBlockUtil.addBlock(ModBlockEntityTypes.COOKING_POT.get(), TDBlocks.FIERY_POT.get());
			Set<Block> set = new LinkedHashSet<>(ModBlockEntityTypes.CABINET.get().validBlocks);
			for (var e : TDBlocks.WoodTypes.values()) set.add(TDBlocks.CABINETS[e.ordinal()].get());
			ModBlockEntityTypes.CABINET.get().validBlocks = set;

			/* TODO neapolitan
			if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
				((ItemAccessor) NeapolitanItems.ADZUKI_ICE_CREAM.get()).setCraftingRemainingItem(Items.BOWL);
				((ItemAccessor) NeapolitanItems.BANANA_ICE_CREAM.get()).setCraftingRemainingItem(Items.BOWL);
				((ItemAccessor) NeapolitanItems.MINT_ICE_CREAM.get()).setCraftingRemainingItem(Items.BOWL);
			}
			 */

			if (ModList.get().isLoaded(Thirst.ID)) {
				ThirstCompat.init();
			}
		});
	}

	@SubscribeEvent
	public static void modifyDefaultComponent(ModifyDefaultComponentsEvent event) {
		event.modify(TFItems.MEEF_STROGANOFF, b -> b.set(DataComponents.FOOD, new FoodProperties.Builder()
				.nutrition(8).saturationModifier(0.6F).alwaysEdible().usingConvertsTo(Items.BOWL)
				.effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT, 6000), 1f).build()));
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {

		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::genItemTag);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::genBlockTag);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipes);
		REGISTRATE.addDataGenerator(ProviderType.LOOT, ExtraLootGen::genLoot);
		REGISTRATE.addDataGenerator(L2TagGen.EFF_TAGS, pvd -> pvd.addTag(L2TagGen.TRACKED_EFFECTS).add(
				TDEffects.FIRE_RANGE.get(), TDEffects.FROZEN_RANGE.get(),
				TDEffects.POISON_RANGE.get(), TDEffects.AURORA_GLOWING.get()
		));
		REGISTRATE.addDataGenerator(ProviderType.DATA_MAP, pvd -> pvd.builder(NeoForgeDataMaps.COMPOSTABLES)
				.add(TDBlocks.MUSHGLOOM_COLONY.getSibling(Registries.ITEM), new Compostable(1), false));

		var gen = event.getGenerator();
		var pvd = event.getLookupProvider();
		var output = gen.getPackOutput();
		var file = event.getExistingFileHelper();
		var run = event.includeServer();
		gen.addProvider(run, new GLMGen(output, pvd));
		gen.addProvider(run, new FoodTwilight(gen, pvd));
		var reg = new TDDatapackRegistriesGen(output, pvd);
		gen.addProvider(run, reg);
		gen.addProvider(run, new TDDatapackTagsGen(output, reg.getRegistryProvider(), file));
	}

	@SubscribeEvent
	public static void addPackFinders(AddPackFindersEvent event) {
		if (event.getPackType() == PackType.CLIENT_RESOURCES) {
			IModFileInfo modFileInfo = ModList.get().getModFileById(MODID);
			if (modFileInfo == null)
				return;
			String builtin = "shader_compatible_fiery";
			IModFile modFile = modFileInfo.getFile();
			event.addRepositorySource((consumer) -> {
				Pack pack = Pack.readMetaAndCreate(
						new PackLocationInfo(MODID + ":" + builtin, Component.literal("Shader Compatible Fiery"),
								PackSource.FEATURE, Optional.empty()),
						new ModFilePackResources(modFile, "resourcepacks/" + builtin),
						PackType.CLIENT_RESOURCES,
						new PackSelectionConfig(false, Pack.Position.TOP, false)
				);
				if (pack != null) {
					consumer.accept(pack);
				}
			});
		}
	}

	public static ResourceLocation loc(String id) {
		return ResourceLocation.fromNamespaceAndPath(MODID, id);
	}

}
