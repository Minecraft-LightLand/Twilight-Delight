package dev.xkmc.twilightdelight.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFBlocks;

public class RichSoilUtil {

	public static void convert(BlockPos pos, Level level) {
		for (Direction d0 : Direction.values()) {
			BlockPos ipos = pos.relative(d0);
			BlockState state = level.getBlockState(ipos);
			if (state.is(TFBlocks.ROOT_BLOCK.get())) {
				for (Direction d1 : Direction.values()) {
					BlockPos jpos = pos.relative(d1);
					BlockState next = level.getBlockState(jpos);
					if (next.is(TFBlocks.LIVEROOT_BLOCK.get())) {
						level.setBlockAndUpdate(ipos, TFBlocks.LIVEROOT_BLOCK.get().defaultBlockState());
						return;
					}
				}
			}
		}
	}

}
