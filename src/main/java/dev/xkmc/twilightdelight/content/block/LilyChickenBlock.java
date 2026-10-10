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
 * @author Goulixiaoji
 */
public class LilyChickenBlock extends FeastBlock {
	protected static final VoxelBuilder PLATE = new VoxelBuilder(1, 0, 1, 15, 2, 15);

	/**
	 * Indexed by {@link FeastBlock#SERVINGS}, then by
	 * {@link net.minecraft.core.Direction#get2DDataValue()}: entry {@code i} is the state with
	 * {@code servings == i}, so index 0 is the leftovers and index 4 is the untouched plate.
	 * The leftovers are the bare plate: the remaining ribcage and spine are flat decorative
	 * planes, like the plate rim glow, and are excluded.
	 */
	protected static final VoxelShape[][] STAGE_SHAPES = {
			VoxelBuilder.buildRotatedShapes(List.of(PLATE)),
			served(4, 2, 9, 14, 9, 13),
			served(2, 2, 8, 14, 9, 13),
			served(2, 2, 3, 14, 9, 13),
			served(2, 2, 3, 14, 9, 13)};

	private static VoxelShape[] served(int x0, int y0, int z0, int x1, int y1, int z1) {
		return VoxelBuilder.buildRotatedShapes(List.of(PLATE, new VoxelBuilder(x0, y0, z0, x1, y1, z1)));
	}

	public LilyChickenBlock() {
		super(Properties.ofFullCopy(Blocks.WHITE_WOOL).pushReaction(PushReaction.DESTROY), DelightFood.PLATE_OF_LILY_CHICKEN.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return STAGE_SHAPES[state.getValue(SERVINGS)][state.getValue(FACING).get2DDataValue()];
	}

}