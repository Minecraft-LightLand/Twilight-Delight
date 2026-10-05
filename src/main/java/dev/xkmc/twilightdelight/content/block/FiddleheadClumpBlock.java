package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Supplier;

/**
 * A mushroom colony variant that grows a fiddlehead clump.
 * Keeps the upper block in sync with the colony age, so the plant visibly grows taller.
 */
public class FiddleheadClumpBlock extends MushroomColonyBlock {

	public FiddleheadClumpBlock(Properties properties, Supplier<Item> mushroomType) {
		super(properties, mushroomType);
	}

	public static void updateUpperBlock(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (!state.is(TDBlocks.FIDDLEHEAD_CLUMP.get())) return;
		int age = state.getValue(COLONY_AGE);
		BlockPos abovePos = pos.above();
		BlockState aboveState = level.getBlockState(abovePos);
		var upper = (FiddleheadClumpUpperBlock) TDBlocks.FIDDLEHEAD_CLUMP_UPPER.get();
		if (age > 0) {
			int top = Math.min(age, 3);
			if ((aboveState.isAir() || aboveState.is(upper))
					&& (!aboveState.is(upper) || aboveState.getValue(FiddleheadClumpUpperBlock.AGE) != top)) {
				level.setBlock(abovePos, upper.defaultBlockState().setValue(FiddleheadClumpUpperBlock.AGE, top), 3);
			}
		} else if (aboveState.is(upper)) {
			level.setBlock(abovePos, Blocks.AIR.defaultBlockState(), 3);
		}
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
		super.onPlace(state, level, pos, oldState, moved);
		updateUpperBlock(level, pos);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(state, level, pos, random);
		updateUpperBlock(level, pos);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		int age = state.getValue(COLONY_AGE);
		BlockState groundState = level.getBlockState(pos.below());
		if (age < getMaxAge()
				&& groundState.is(ModTags.Blocks.MUSHROOM_COLONY_GROWABLE_ON)
				&& ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(4) == 0)) {
			level.setBlock(pos, state.setValue(COLONY_AGE, age + 1), 2);
			ForgeHooks.onCropsGrowPost(level, pos, state);
		}
		updateUpperBlock(level, pos);
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		super.performBonemeal(level, random, pos, state);
		updateUpperBlock(level, pos);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		var result = super.use(state, level, pos, player, hand, hit);
		if (result.consumesAction()) updateUpperBlock(level, pos);
		return result;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
		super.onRemove(state, level, pos, newState, moved);
		if (!state.is(newState.getBlock())) {
			BlockPos abovePos = pos.above();
			if (level.getBlockState(abovePos).is(TDBlocks.FIDDLEHEAD_CLUMP_UPPER.get())) {
				level.setBlock(abovePos, Blocks.AIR.defaultBlockState(), 3);
			}
		}
	}

}