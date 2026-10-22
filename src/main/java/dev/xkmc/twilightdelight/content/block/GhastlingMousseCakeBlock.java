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
 * A ghastling mousse cake on a tray. The cake is cut into four servings, the
 * tray is what the block leaves behind once it is eaten.
 */
public class GhastlingMousseCakeBlock extends FeastBlock {

	protected static final VoxelBuilder TRAY = new VoxelBuilder(1, 0, 1, 15, 2, 15);

	/**
	 * Indexed by {@link FeastBlock#SERVINGS}, then by
	 * {@link net.minecraft.core.Direction#get2DDataValue()}: entry {@code i} is the state with
	 * {@code servings == i}, so index 0 is the empty tray and index 4 is the untouched cake.
	 * The cake is eaten quarter by quarter, from the back left corner.
	 */
	protected static final VoxelShape[][] STAGE_SHAPES = {
			VoxelBuilder.buildRotatedShapes(List.of(TRAY)),
			served(2, 8, 8, 8, 14, 14),
			served(2, 8, 2, 14, 14, 14),
			served(2, 2, 2, 14, 14, 14),
			served(2, 2, 2, 14, 14, 14)};

	private static VoxelShape[] served(int x0, int y0, int z0, int x1, int y1, int z1) {
		return VoxelBuilder.buildRotatedShapes(List.of(TRAY, new VoxelBuilder(x0, y0, z0, x1, y1, z1)));
	}

	public GhastlingMousseCakeBlock() {
		super(Properties.ofFullCopy(Blocks.WHITE_WOOL).pushReaction(PushReaction.DESTROY),
				DelightFood.BOWL_OF_GHASTLING_MOUSSE_CAKE.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return STAGE_SHAPES[state.getValue(SERVINGS)][state.getValue(FACING).get2DDataValue()];
	}

}
