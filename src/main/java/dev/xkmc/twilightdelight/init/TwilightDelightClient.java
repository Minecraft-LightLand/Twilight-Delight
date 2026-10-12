package dev.xkmc.twilightdelight.init;

import dev.xkmc.twilightdelight.content.block.FiddleheadClumpBlock;
import dev.xkmc.twilightdelight.content.item.food.ReusableDrinkItem;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = TwilightDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwilightDelightClient {

	@SubscribeEvent
	public static void registerItemProperties(FMLClientSetupEvent event) {
		ItemProperties.register(TDItems.CHAOS_INK_DRINK.get(),
				new ResourceLocation(TwilightDelight.MODID, "sips"),
				(stack, level, entity, seed) -> ReusableDrinkItem.getSipsProperty(stack));
	}

	/**
	 * Tint the fiddlehead by biome, mirroring how vanilla tints grass.
	 */
	@SubscribeEvent
	public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
		event.register((state, getter, pos, tintIndex) -> {
			if ((tintIndex & 1) == 1) return -1;
			return getter != null && pos != null ? BiomeColors.getAverageGrassColor(getter, pos) : GrassColor.get(0.5, 1.0);
		}, TDBlocks.FIDDLEHEAD_CLUMP.get(), TDBlocks.FIDDLEHEAD_CLUMP_UPPER.get());
	}

	/**
	 * The clump item renders soil on layer0 and the fiddlehead on layer1; only the plant takes
	 * the tint, and without a level/pos to average over that is the plain biome foliage color.
	 */
	@SubscribeEvent
	public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
		event.register((stack, tintIndex) -> tintIndex == 1 ? FoliageColor.get(0.5, 1.0) : -1,
				(ItemLike) TDBlocks.FIDDLEHEAD_CLUMP.get().asItem());
	}

}
