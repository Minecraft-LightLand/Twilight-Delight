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

public class ChaosStewBlock extends FeastBlock {

	protected static final VoxelBuilder BOWL = new VoxelBuilder(4, 0, 4, 12, 8, 12);

	/**
	 * Indexed by {@link FeastBlock#SERVINGS}, then by
	 * {@link net.minecraft.core.Direction#get2DDataValue()}: entry {@code i} is the state with
	 * {@code servings == i}, so index 0 is the empty bowl and index 4 is the brim-full one.
	 * The bowl is the container, the contents sit on top of it. The lid and the
	 * floating garnish are decoration and excluded.
	 */
	protected static final VoxelShape[][] STAGE_SHAPES = {
			VoxelBuilder.buildRotatedShapes(List.of(BOWL)),
			served(8),
			served(10),
			served(13),
			served(14)};

	private static VoxelShape[] served(int height) {
		return VoxelBuilder.buildRotatedShapes(List.of(BOWL, new VoxelBuilder(4, 8, 4, 12, height, 12)));
	}

	public ChaosStewBlock() {
		super(Properties.ofFullCopy(Blocks.WHITE_WOOL).pushReaction(PushReaction.DESTROY), DelightFood.BOWL_OF_CHAOS_STEW.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return STAGE_SHAPES[state.getValue(SERVINGS)][state.getValue(FACING).get2DDataValue()];
	}

}