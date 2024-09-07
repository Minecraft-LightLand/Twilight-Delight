package dev.xkmc.twilightdelight.init;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.teamabnormals.neapolitan.core.Neapolitan;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import com.tterrag.registrate.providers.ProviderType;
import dev.ghen.thirst.Thirst;
import dev.xkmc.l2core.events.EffectSyncEvents;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.init.events.EffectSyncEvents;
import dev.xkmc.twilightdelight.compat.ThirstCompat;
import dev.xkmc.twilightdelight.events.NeapolitanEventListeners;
import dev.xkmc.twilightdelight.init.data.*;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightPie;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCakes;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanFood;
import dev.xkmc.twilightdelight.mixin.FoodPropertiesAccessor;
import dev.xkmc.twilightdelight.mixin.ItemAccessor;
import dev.xkmc.twilightdelight.util.StoveAddBlockUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.LinkedHashSet;
import java.util.Set;

@Mod(TwilightDelight.MODID)
@EventBusSubscriber(modid = TwilightDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TwilightDelight {

	public static final String MODID = "twilightdelight";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public TwilightDelight() {
		TDBlocks.register();
		TDItems.register();
		DelightFood.register();
		DelightPie.register();
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			NeapolitanFood.register();
			NeapolitanCakes.register();
			MinecraftForge.EVENT_BUS.register(NeapolitanEventListeners.class);
		}
		TDEffects.register();
		TDRecipes.register();
		TDModConfig.init();
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			StoveAddBlockUtil.addBlock(ModBlockEntityTypes.STOVE.get(), TDBlocks.MAZE_STOVE.get());
			StoveAddBlockUtil.addBlock(ModBlockEntityTypes.COOKING_POT.get(), TDBlocks.FIERY_POT.get());
			((FoodPropertiesAccessor) TFItems.MEEF_STROGANOFF.get().getFoodProperties()).getEffectSupplierList()
					.add(Pair.of(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000), 1f));

			Set<Block> set = new LinkedHashSet<>(ModBlockEntityTypes.CABINET.get().validBlocks);
			for (var e : TDBlocks.WoodTypes.values()) set.add(TDBlocks.CABINETS[e.ordinal()].get());
			ModBlockEntityTypes.CABINET.get().validBlocks = set;

			ComposterBlock.COMPOSTABLES.put(TDBlocks.MUSHGLOOM_COLONY.get().asItem(), 1.0F);

			EffectSyncEvents.TRACKED.add(TDEffects.FIRE_RANGE.get());
			EffectSyncEvents.TRACKED.add(TDEffects.FROZEN_RANGE.get());
			EffectSyncEvents.TRACKED.add(TDEffects.POISON_RANGE.get());
			EffectSyncEvents.TRACKED.add(TDEffects.AURORA_GLOWING.get());

			if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
				((ItemAccessor) NeapolitanItems.ADZUKI_ICE_CREAM.get()).setCraftingRemainingItem(Items.BOWL);
				((ItemAccessor) NeapolitanItems.BANANA_ICE_CREAM.get()).setCraftingRemainingItem(Items.BOWL);
				((ItemAccessor) NeapolitanItems.MINT_ICE_CREAM.get()).setCraftingRemainingItem(Items.BOWL);
			}

			if (ModList.get().isLoaded(Thirst.ID)) {
				ThirstCompat.init();
			}
		});
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {

		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::genItemTag);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::genBlockTag);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipes);
		REGISTRATE.addDataGenerator(ProviderType.LOOT, ExtraLootGen::genLoot);

		event.getGenerator().addProvider(event.includeServer(), new GLMGen(event.getGenerator()));
		var output = event.getGenerator().getPackOutput();
		var reg = new TDDatapackRegistriesGen(output, event.getLookupProvider());
		event.getGenerator().addProvider(event.includeServer(), reg);
		event.getGenerator().addProvider(event.includeServer(), new TDDatapackTagsGen(output, reg.getRegistryProvider(), event.getExistingFileHelper()));
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
				Pack pack = Pack.readMetaAndCreate(MODID + ":" + builtin,
						Component.literal("Shader Compatible Fiery"), false,
						(id) -> new ModFilePackResources(id, modFile, "resourcepacks/" + builtin),
						PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
				if (pack != null) {
					consumer.accept(pack);
				}
			});
		}
	}

	public static ResourceLocation loc(String id){
		return ResourceLocation.fromNamespaceAndPath(MODID, id);
	}

}
