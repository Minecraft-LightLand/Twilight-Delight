package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.init.data.TDModConfig;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.FarmlandWaterManager;
import vectorwing.farmersdelight.common.block.RichSoilBlock;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;

import javax.annotation.Nullable;

/**
 * Farmland variant of {@link TwilightRichSoilBlock}, obtained by tilling it.
 * Falls back to plain twilight rich soil whenever the farmland cannot survive.
 */
public class TwilightRichSoilFarmlandBlock extends RichSoilFarmlandBlock {

	public TwilightRichSoilFarmlandBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		int moisture = state.getValue(MOISTURE);
		if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
			if (moisture > 0) level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
		} else if (moisture < 7) {
			level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
		} else if (moisture == 7) {
			for (int i = 0; i < TDModConfig.SERVER.richSoilTrials.get(); i++) {
				RichSoilBlock.tryBoostingPlantsAboveAndBelow(level, pos, random);
			}
		}
	}

	private static boolean isNearWater(LevelReader level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
			if (state.canBeHydrated(level, pos, level.getFluidState(nearbyPos), nearbyPos)) return true;
		}
		return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
	}

	public static void turnToTwilightSoil(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
		level.setBlockAndUpdate(pos,
				pushEntitiesUp(state, TDBlocks.TWILIGHT_RICH_SOIL.get().defaultBlockState(), level, pos));
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, state));
	}

	@Override
	public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getValue(MOISTURE) > 0;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.canSurvive(level, pos)) turnToTwilightSoil(null, state, level, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if (!defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos())) {
			return TDBlocks.TWILIGHT_RICH_SOIL.get().defaultBlockState();
		}
		return super.getStateForPlacement(context);
	}

}