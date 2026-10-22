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

/**
 * A phantom knight helmet filled with the meal of the maze. The helmet is the
 * serving vessel, so it is what the block leaves behind once it is eaten.
 */
public class DeliciousInMazeBlock extends FeastBlock {

	protected static final VoxelBuilder HELMET = new VoxelBuilder(4, 0, 5, 12, 8, 13);

	/**
	 * Indexed by {@link FeastBlock#SERVINGS}, then by
	 * {@link net.minecraft.core.Direction#get2DDataValue()}: entry {@code i} is the state with
	 * {@code servings == i}, so index 0 is the empty helmet and index 4 is the brim-full one.
	 * The meal sits inside the helmet and sinks as it is eaten.
	 */
	protected static final VoxelShape[][] STAGE_SHAPES = {
			VoxelBuilder.buildRotatedShapes(List.of(HELMET)),
			served(2),
			served(4),
			served(6),
			served(13)};

	private static VoxelShape[] served(int height) {
		return VoxelBuilder.buildRotatedShapes(List.of(HELMET, new VoxelBuilder(4, 1, 5, 12, height, 13)));
	}

	public DeliciousInMazeBlock() {
		super(Properties.copy(Blocks.WHITE_WOOL).pushReaction(PushReaction.DESTROY),
				DelightFood.PLATE_OF_DELICIOUS_IN_MAZE.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return STAGE_SHAPES[state.getValue(SERVINGS)][state.getValue(FACING).get2DDataValue()];
	}

}
