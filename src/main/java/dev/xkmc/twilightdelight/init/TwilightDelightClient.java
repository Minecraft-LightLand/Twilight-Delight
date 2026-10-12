package dev.xkmc.twilightdelight.init;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.content.item.food.ReusableDrinkItem;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import javax.annotation.Nullable;

@EventBusSubscriber(value = Dist.CLIENT, modid = TwilightDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TwilightDelightClient {

	@SubscribeEvent
	public static void registerItemProperties(FMLClientSetupEvent event) {
		ItemProperties.register(TDItems.CHAOS_INK_DRINK.get(), TwilightDelight.loc("sips"),
				(stack, level, entity, seed) -> ReusableDrinkItem.getSipsProperty(stack));
	}

	@SubscribeEvent
	public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
		event.register(TwilightDelightClient::getLeafBlockColor, TDBlocks.IRON_LEAVES.get());
		event.register(TwilightDelightClient::getGrassBlockColor,
				TDBlocks.FIDDLEHEAD_CLUMP.get(), TDBlocks.FIDDLEHEAD_CLUMP_UPPER.get());
	}

	@SubscribeEvent
	public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
		event.register(TwilightDelightClient::getLeafItemColor, TDBlocks.IRON_LEAVES.get().asItem());
		event.register(TwilightDelightClient::getFiddleheadItemColor, TDBlocks.FIDDLEHEAD_CLUMP.get().asItem());
	}

	/**
	 * The clump item renders soil on layer0 and the fiddlehead on layer1; only the plant takes
	 * the tint, and without a level/pos to average over that is the plain biome foliage color.
	 */
	private static int getFiddleheadItemColor(ItemStack stack, int tintIndex) {
		return tintIndex == 1 ? FoliageColor.get(0.5, 1.0) : -1;
	}

	/**
	 * Tint the fiddlehead by biome, mirroring how vanilla tints grass.
	 */
	private static int getGrassBlockColor(BlockState state, @Nullable BlockAndTintGetter getter,
			@Nullable BlockPos pos, int tintIndex) {
		if ((tintIndex & 1) == 1) return -1;
		return getter != null && pos != null
				? BiomeColors.getAverageGrassColor(getter, pos)
				: GrassColor.get(0.5, 1.0);
	}

	private static int getLeafBlockColor(BlockState state, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, int tintIndex) {
		if (tintIndex > 15) {
			return 16777215;
		} else if (getter != null && pos != null) {
			int red = 0;
			int grn = 0;
			int blu = 0;

			for (int dz = -1; dz <= 1; ++dz) {
				for (int dx = -1; dx <= 1; ++dx) {
					int i2 = BiomeColors.getAverageFoliageColor(getter, pos.offset(dx, 0, dz));
					red += (i2 & 16711680) >> 16;
					grn += (i2 & '\uff00') >> 8;
					blu += i2 & 255;
				}
			}

			return (red / 9 & 255) << 16 | (grn / 9 & 255) << 8 | blu / 9 & 255;
		} else {
			return FoliageColor.getDefaultColor();
		}
	}

	private static int getLeafItemColor(ItemStack stack, int tintIndex) {
		if (stack.getItem() instanceof BlockItem blockItem) {
			return getLeafBlockColor(blockItem.getBlock().defaultBlockState(), null, null, tintIndex);
		}
		return 16777215;
	}

}
