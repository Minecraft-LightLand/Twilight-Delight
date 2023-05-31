package dev.xkmc.twilightdelight.init;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.teamabnormals.neapolitan.core.Neapolitan;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.twilightdelight.init.data.*;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCakes;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanFood;
import dev.xkmc.twilightdelight.mixin.FoodPropertiesAccessor;
import dev.xkmc.twilightdelight.util.StoveAddBlockUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModEffects;

@Mod(TwilightDelight.MODID)
@Mod.EventBusSubscriber(modid = TwilightDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwilightDelight {

	public static final String MODID = "twilightdelight";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(MODID);

	public TwilightDelight() {
		TDBlocks.register();
		TDItems.register();
		DelightFood.register();
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			NeapolitanFood.register();
			NeapolitanCakes.register();
		}
		TDEffects.register();
		TDModConfig.init();
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagGen::genItemTag);
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, TagGen::genBlockTag);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipes);
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			StoveAddBlockUtil.addBlock(ModBlockEntityTypes.STOVE.get(), TDBlocks.MAZE_STOVE.get());
			((FoodPropertiesAccessor) TFItems.STROGANOFF).getEffectSupplierList()
					.add(Pair.of(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000), 1f));

			ComposterBlock.COMPOSTABLES.put(TDBlocks.MUSHGLOOM_COLONY.get().asItem(), 1.0F);
		});
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(event.includeServer(), new GLMGen(event.getGenerator()));
	}

}
