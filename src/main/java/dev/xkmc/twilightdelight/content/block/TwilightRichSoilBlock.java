package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.init.data.TDModConfig;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.util.RichSoilUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.ItemAbilities;
import twilightforest.init.TFBlocks;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

/**
 * Rich soil variant that additionally converts mushrooms and fiddleheads growing above it
 * into the matching colonies, and boosts plants with a configurable number of trials per tick.
 */
public class TwilightRichSoilBlock extends RichSoilBlock implements BonemealableBlock {

	public TwilightRichSoilBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (level.isClientSide) return;
		RichSoilUtil.convert(pos, level);
		if (tryConvertColony(level, pos.above())) return;
		for (int i = 0; i < TDModConfig.SERVER.richSoilTrials.get(); i++) {
			RichSoilBlock.tryBoostingPlantsAboveAndBelow(level, pos, random);
		}
	}

	public boolean tryConvertColony(ServerLevel level, BlockPos abovePos) {
		BlockState aboveState = level.getBlockState(abovePos);
		if (convertMushroomToColony(aboveState, abovePos, level)) return true;
		if (aboveState.is(TFBlocks.MUSHGLOOM.get())) {
			level.setBlockAndUpdate(abovePos, TDBlocks.MUSHGLOOM_COLONY.get().defaultBlockState());
			return true;
		}
		if (aboveState.is(TFBlocks.FIDDLEHEAD.get())) {
			level.setBlockAndUpdate(abovePos, TDBlocks.FIDDLEHEAD_CLUMP.get().defaultBlockState());
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		BlockState aboveState = level.getBlockState(pos.above());
		return aboveState.is(Blocks.BROWN_MUSHROOM)
				|| aboveState.is(Blocks.RED_MUSHROOM)
				|| aboveState.is(TFBlocks.MUSHGLOOM.get())
				|| aboveState.is(TFBlocks.FIDDLEHEAD.get());
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		tryConvertColony(level, pos.above());
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
		if (toolAction == ItemAbilities.HOE_TILL
				&& context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
			return TDBlocks.TWILIGHT_RICH_SOIL_FARMLAND.get().defaultBlockState();
		}
		return super.getToolModifiedState(state, context, toolAction, simulate);
	}

}