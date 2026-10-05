package dev.xkmc.twilightdelight.content.block;

import com.mojang.serialization.MapCodec;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.init.TFBlocks;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

/**
 * Non-interactive upper half of a {@link FiddleheadClumpBlock}.
 * All interactions are forwarded to the colony below, and removing it takes the colony with it.
 */
public class FiddleheadClumpUpperBlock extends BushBlock implements BonemealableBlock {

	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

	public static final MapCodec<FiddleheadClumpUpperBlock> CODEC = simpleCodec(FiddleheadClumpUpperBlock::new);

	public FiddleheadClumpUpperBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(AGE, 1));
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState belowState = level.getBlockState(pos.below());
		return belowState.is(TDBlocks.FIDDLEHEAD_CLUMP.get())
				&& belowState.getValue(MushroomColonyBlock.COLONY_AGE) > 0;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		BlockPos belowPos = pos.below();
		BlockState belowState = level.getBlockState(belowPos);
		if (belowState.is(TDBlocks.FIDDLEHEAD_CLUMP.get()) && belowState.getBlock() instanceof FiddleheadClumpBlock clump) {
			return clump.useWithoutItem(belowState, level, belowPos, player, hit);
		}
		return InteractionResult.PASS;
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
			Player player, InteractionHand hand, BlockHitResult hit) {
		BlockPos belowPos = pos.below();
		BlockState belowState = level.getBlockState(belowPos);
		if (belowState.is(TDBlocks.FIDDLEHEAD_CLUMP.get()) && belowState.getBlock() instanceof FiddleheadClumpBlock clump) {
			return clump.useItemOn(stack, belowState, level, belowPos, player, hand, hit);
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
		super.onRemove(state, level, pos, newState, moved);
		if (!state.is(newState.getBlock())) {
			BlockPos belowPos = pos.below();
			BlockState belowState = level.getBlockState(belowPos);
			if (belowState.is(TDBlocks.FIDDLEHEAD_CLUMP.get())
					&& belowState.getValue(MushroomColonyBlock.COLONY_AGE) > 0) {
				level.destroyBlock(belowPos, true);
			}
		}
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return new ItemStack(TFBlocks.FIDDLEHEAD.get());
	}

	private boolean isLowerClump(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(TDBlocks.FIDDLEHEAD_CLUMP.get());
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		if (!isLowerClump(level, pos)) return false;
		BlockState belowState = level.getBlockState(pos.below());
		return belowState.getBlock() instanceof BonemealableBlock growable
				? growable.isValidBonemealTarget(level, pos.below(), belowState)
				: false;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos belowPos = pos.below();
		BlockState belowState = level.getBlockState(belowPos);
		if (belowState.getBlock() instanceof BonemealableBlock growable) {
			growable.performBonemeal(level, random, belowPos, belowState);
		}
	}

}