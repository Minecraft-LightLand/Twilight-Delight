package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class ChaosStewBlock extends FeastBlock {

	protected static final VoxelShape FULL_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);
	protected static final VoxelShape STAGE1_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
	protected static final VoxelShape STAGE2_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D);
	protected static final VoxelShape STAGE3_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 5.0D, 12.0D);
	protected static final VoxelShape LEFTOVER_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

	public ChaosStewBlock() {
		super(Properties.copy(Blocks.WHITE_WOOL), DelightFood.BOWL_OF_CHAOS_STEW.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(SERVINGS)) {
			case 3 -> STAGE1_SHAPE;
			case 2 -> STAGE2_SHAPE;
			case 1 -> STAGE3_SHAPE;
			case 0 -> LEFTOVER_SHAPE;
			default -> FULL_SHAPE;
		};
	}

}
