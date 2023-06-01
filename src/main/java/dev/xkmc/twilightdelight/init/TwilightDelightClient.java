package dev.xkmc.twilightdelight.init;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = TwilightDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwilightDelightClient {

	@SubscribeEvent
	public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
		event.register(TwilightDelightClient::getLeafBlockColor, TDBlocks.IRON_LEAVES.get());
	}

	@SubscribeEvent
	public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
		event.register(TwilightDelightClient::getLeafItemColor, TDBlocks.IRON_LEAVES.get().asItem());
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
