package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

/**
 * A cauldron of yeti borscht. The cauldron is both the cooking vessel and the
 * serving vessel, so the block leaves a bowl behind once all servings are taken
 * and an empty cauldron as the leftover model.
 */
public class YetiBorschtBlock extends FeastBlock {

	/**
	 * The same hollow shell vanilla cauldrons use: the outer walls and the rim,
	 * with the legs and the inside carved out.
	 */
	protected static final VoxelShape SHAPE = Shapes.join(
			Shapes.block(),
			Shapes.or(
					Block.box(0, 0, 4, 16, 3, 12),
					Block.box(4, 0, 0, 12, 3, 16),
					Block.box(2, 0, 2, 14, 3, 14),
					Block.box(2, 4, 2, 14, 16, 14)),
			BooleanOp.ONLY_FIRST);

	public YetiBorschtBlock() {
		super(Properties.of().mapColor(MapColor.STONE).strength(2.0F)
				.sound(SoundType.METAL).noOcclusion().pushReaction(PushReaction.DESTROY),
				DelightFood.BOWL_OF_YETI_BORSCHT.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

}
