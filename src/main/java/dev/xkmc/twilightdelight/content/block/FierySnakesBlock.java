package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;


/**
 * @author Goulixiaoji
 */
public class FierySnakesBlock extends FeastBlock {
	protected static final VoxelShape PLATE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);

	public FierySnakesBlock() {
		super(Properties.copy(Blocks.WHITE_WOOL), TDItems.Food.PLATE_OF_FIERY_SNAKES.item, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(SERVINGS) == 0 ? PLATE_SHAPE : thisGetShape(state, worldIn, pos, context);
	}

	protected static VoxelShape thisGetShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(world, pos);
		return switch (state.getValue(FACING)) {
			default -> Shapes.or(box(3, 2, 4, 13, 7, 10), box(2, 0, 2, 15, 2, 15)).move(offset.x, offset.y, offset.z);
			case NORTH -> Shapes.or(box(3, 2, 6, 13, 7, 12), box(1, 0, 1, 14, 2, 14)).move(offset.x, offset.y, offset.z);
			case EAST -> Shapes.or(box(4, 2, 3, 10, 7, 13), box(2, 0, 1, 15, 2, 14)).move(offset.x, offset.y, offset.z);
			case WEST -> Shapes.or(box(6, 2, 3, 12, 7, 13), box(1, 0, 2, 14, 2, 15)).move(offset.x, offset.y, offset.z);
		};
	}

}
