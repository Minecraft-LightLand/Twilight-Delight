package dev.xkmc.twilightdelight.init;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.teamabnormals.neapolitan.core.Neapolitan;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.base.effects.EffectSyncEvents;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import dev.xkmc.twilightdelight.events.NeapolitanEventListeners;
import dev.xkmc.twilightdelight.init.data.*;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCakes;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanFood;
import dev.xkmc.twilightdelight.init.world.TreeConfig;
import dev.xkmc.twilightdelight.mixin.FoodPropertiesAccessor;
import dev.xkmc.twilightdelight.mixin.ItemAccessor;
import dev.xkmc.twilightdelight.util.StoveAddBlockUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModEffects;

@Mod(TwilightDelight.MODID)
@Mod.EventBusSubscriber(modid = TwilightDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwilightDelight {

	public static final String MODID = "twilightdelight";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	public TwilightDelight() {
		TDBlocks.register();
		TDItems.register();
		DelightFood.register();
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			NeapolitanFood.register();
			NeapolitanCakes.register();
			MinecraftForge.EVENT_BUS.register(NeapolitanEventListeners.class);
		}
		TDEffects.register();
		TDRecipes.register(FMLJavaModLoadingContext.get().getModEventBus());
		TDModConfig.init();
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::genItemTag);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::genBlockTag);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipes);
		REGISTRATE.addDataGenerator(ProviderType.LOOT, ExtraLootGen::genLoot);
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			TreeConfig.register();
			StoveAddBlockUtil.addBlock(ModBlockEntityTypes.STOVE.get(), TDBlocks.MAZE_STOVE.get());
			StoveAddBlockUtil.addBlock(ModBlockEntityTypes.COOKING_POT.get(), TDBlocks.FIERY_POT.get());
			((FoodPropertiesAccessor) TFItems.STROGANOFF).getEffectSupplierList()
					.add(Pair.of(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000), 1f));

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
		});
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(event.includeServer(), new GLMGen(event.getGenerator()));
	}

}
