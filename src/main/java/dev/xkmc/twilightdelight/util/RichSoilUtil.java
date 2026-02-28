package dev.xkmc.twilightdelight.util;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.HugeLilyPadBlock;
import twilightforest.enums.HugeLilypadPiece;
import twilightforest.init.TFBlocks;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class RichSoilUtil {

	public static void convert(BlockPos pos, Level level) {
		convertLiveRoot(pos, level);
		convertLilyPad(pos, level);
		convertIronwood(pos, level);
	}

	public static void convertLiveRoot(BlockPos pos, Level level) {
		Direction d0 = Direction.getRandom(level.getRandom());
		BlockPos ipos = pos.relative(d0);
		BlockState state = level.getBlockState(ipos);
		if (state.is(TFBlocks.ROOT_BLOCK.get())) {
			Direction d1 = Direction.getRandom(level.getRandom());
			BlockPos jpos = ipos.relative(d1);
			BlockState next = level.getBlockState(jpos);
			if (next.is(TFBlocks.LIVEROOT_BLOCK.get())) {
				level.setBlockAndUpdate(ipos, TFBlocks.LIVEROOT_BLOCK.get().defaultBlockState());
			}
		}
	}

	public static void convertLilyPad(BlockPos pos, Level level) {
		BlockPos target = pos.above(2);
		var state = level.getBlockState(target);
		if (state.is(Blocks.LILY_PAD)) {
			if (level.getRandom().nextBoolean()) {
				Direction dir = Direction.from2DDataValue(level.getRandom().nextInt(4));
				if (level.getBlockState(target.relative(dir)).is(TFBlocks.HUGE_LILY_PAD.get())) {
					level.setBlockAndUpdate(target, TFBlocks.HUGE_WATER_LILY.get().defaultBlockState());
					return;
				}
			}
			convertPadToHuge(target, level);
		} else if (state.is(TFBlocks.HUGE_WATER_LILY.get())) {
			spreadLilyPad(target, level);
		}
	}

	private static void spreadLilyPad(BlockPos target, Level level) {
		var dir = Direction.from2DDataValue(level.getRandom().nextInt(4));
		var p = target.relative(dir);
		var s = level.getBlockState(p);
		if (!s.isAir() && !s.is(Blocks.LILY_PAD)) return;
		var w = level.getBlockState(p.below());
		if (!w.is(Blocks.WATER) || !w.getFluidState().isSource())
			return;
		level.setBlockAndUpdate(p, Blocks.LILY_PAD.defaultBlockState());
	}

	private static void convertPadToHuge(BlockPos target, Level level) {
		var large = TFBlocks.HUGE_LILY_PAD.get();
		var dir = Direction.from2DDataValue(level.getRandom().nextInt(4));
		var piece = HugeLilypadPiece.values()[level.getRandom().nextInt(4)];
		var res = large.defaultBlockState()
				.setValue(HugeLilyPadBlock.FACING, dir)
				.setValue(HugeLilyPadBlock.PIECE, piece);
		var list = large.getAllMyBlocks(target, res);
		for (var p : list) {
			var s = level.getBlockState(p);
			if (!s.isAir() && !s.is(Blocks.LILY_PAD)) return;
			var w = level.getBlockState(p.below());
			if (!w.is(Blocks.WATER) || !w.getFluidState().isSource())
				return;
		}

		BlockPos origin = switch (piece) {
			case NE -> target.west();
			case SE -> target.north().west();
			case SW -> target.north();
			default -> target;
		};

		level.setBlockAndUpdate(origin, res.setValue(HugeLilyPadBlock.PIECE, HugeLilypadPiece.NW));
		level.setBlockAndUpdate(origin.east(), res.setValue(HugeLilyPadBlock.PIECE, HugeLilypadPiece.NE));
		level.setBlockAndUpdate(origin.south(), res.setValue(HugeLilyPadBlock.PIECE, HugeLilypadPiece.SW));
		level.setBlockAndUpdate(origin.south().east(), res.setValue(HugeLilyPadBlock.PIECE, HugeLilypadPiece.SE));
	}

	public static void convertIronwood(BlockPos pos, Level level) {
		var up = pos.above();
		var state = level.getBlockState(up);
		if (state.is(TFBlocks.DARKWOOD_SAPLING.get())) {
			if (MathUtils.RAND.nextFloat() <= Configuration.RICH_SOIL_BOOST_CHANCE.get()) {
				int count = 0;
				for (int x = -1; x <= 1; x++) {
					for (int z = -1; z <= 1; z++) {
						if (level.getBlockState(pos.offset(x, -1, z)).is(TFBlocks.LIVEROOT_BLOCK)) {
							count++;
						}
					}
				}
				if (level.getRandom().nextFloat() * 10 < count) {
					level.setBlockAndUpdate(up, TDBlocks.IRON_SAPLING.get().defaultBlockState());
				}
			}
		}
	}

}
