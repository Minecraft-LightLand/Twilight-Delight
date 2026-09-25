package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class ChaosStewBlock extends FeastBlock {

	protected static final VoxelShape PLATE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 3.0D, 15.0D);
	protected static final VoxelShape FULL_SHAPE = Shapes.joinUnoptimized(
			PLATE_SHAPE, Block.box(3.0D, 3.0D, 3.0D, 13.0D, 11.0D, 13.0D), BooleanOp.OR);
	protected static final VoxelShape STAGE1_SHAPE = Shapes.joinUnoptimized(
			PLATE_SHAPE, Block.box(3.0D, 3.0D, 3.0D, 13.0D, 9.0D, 13.0D), BooleanOp.OR);
	protected static final VoxelShape STAGE2_SHAPE = Shapes.joinUnoptimized(
			PLATE_SHAPE, Block.box(3.0D, 3.0D, 3.0D, 13.0D, 7.0D, 13.0D), BooleanOp.OR);
	protected static final VoxelShape STAGE3_SHAPE = Shapes.joinUnoptimized(
			PLATE_SHAPE, Block.box(3.0D, 3.0D, 3.0D, 13.0D, 5.0D, 13.0D), BooleanOp.OR);

	public ChaosStewBlock() {
		super(Properties.copy(Blocks.WHITE_WOOL), DelightFood.BOWL_OF_CHAOS_STEW.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(SERVINGS)) {
			case 3 -> STAGE1_SHAPE;
			case 2 -> STAGE2_SHAPE;
			case 1 -> STAGE3_SHAPE;
			case 0 -> PLATE_SHAPE;
			default -> FULL_SHAPE;
		};
	}

}
