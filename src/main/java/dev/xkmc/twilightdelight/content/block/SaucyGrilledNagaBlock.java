package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.util.VoxelBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.List;

public class SaucyGrilledNagaBlock extends FeastBlock {

	protected static final VoxelBuilder PLATE = new VoxelBuilder(0, 0, 0, 16, 2, 16);

	/**
	 * Indexed by {@link FeastBlock#SERVINGS}, then by
	 * {@link net.minecraft.core.Direction#get2DDataValue()}: entry {@code i} is the state with
	 * {@code servings == i}, so index 0 is the leftovers and index 4 is the untouched plate.
	 * The torchberries and the plate edge overlay are decoration and excluded.
	 */
	protected static final VoxelShape[][] STAGE_SHAPES = {
			VoxelBuilder.buildRotatedShapes(List.of(PLATE)),
			served(3, 2, 3, 15, 5, 15),
			served(3, 2, 3, 15, 8, 15),
			served(3, 2, 3, 15, 11, 15),
			served(3, 2, 3, 15, 14, 15)};

	private static VoxelShape[] served(int x0, int y0, int z0, int x1, int y1, int z1) {
		return VoxelBuilder.buildRotatedShapes(List.of(PLATE, new VoxelBuilder(x0, y0, z0, x1, y1, z1)));
	}

	public SaucyGrilledNagaBlock() {
		super(Properties.copy(Blocks.WHITE_WOOL).pushReaction(PushReaction.DESTROY), DelightFood.PLATE_OF_SAUCY_GRILLED_NAGA.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return STAGE_SHAPES[state.getValue(SERVINGS)][state.getValue(FACING).get2DDataValue()];
	}

}