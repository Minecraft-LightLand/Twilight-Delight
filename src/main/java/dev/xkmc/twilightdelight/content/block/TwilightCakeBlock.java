package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import dev.xkmc.twilightdelight.init.data.TagRef;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.List;
import java.util.function.Supplier;

public class TwilightCakeBlock extends CakeBlock {

	private final FoodProperties food;
	private final Supplier<Item> slice;

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	// Models are eaten from the north side, unlike vanilla cake: bite n leaves z >= 1 + 2n.
	// The arch decoration at the back persists in every state.
	// Shapes below are authored for FACING == NORTH (model as-is) and rotated for other facings.
	private static VoxelShape boxFor(Direction facing, double x0, double y0, double z0, double x1, double y1, double z1) {
		return switch (facing) {
			case SOUTH -> Block.box(16 - x1, y0, 16 - z1, 16 - x0, y1, 16 - z0);
			case EAST -> Block.box(16 - z1, y0, x0, 16 - z0, y1, x1);
			case WEST -> Block.box(z0, y0, 16 - x1, z1, y1, 16 - x0);
			default -> Block.box(x0, y0, z0, x1, y1, z1);
		};
	}

	public TwilightCakeBlock(FoodProperties food, Properties properties, Supplier<Item> slice) {
		super(properties);
		this.food = food;
		this.slice = slice;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BITES, FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Direction facing = state.getValue(FACING);
		double z0 = 1.0D + state.getValue(BITES) * 2.0D;
		return Shapes.or(
				boxFor(facing, 1.0D, 0.0D, z0, 15.0D, 8.0D, 15.0D),
				boxFor(facing, 1.0D, 8.0D, 13.0D, 6.0D, 14.0D, 15.0D));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(TagRef.TOOLS_KNIVES)) {
			if (level.isClientSide) {
				return InteractionResult.SUCCESS;
			}
			cutSlice(level, pos, state, player, hand, stack);
			return InteractionResult.SUCCESS;
		}
		if (level.isClientSide) {
			if (eatSlice(level, pos, state, player, food).consumesAction()) {
				return InteractionResult.SUCCESS;
			}
			if (stack.isEmpty()) {
				return InteractionResult.CONSUME;
			}
		}
		return eatSlice(level, pos, state, player, food);
	}

	private void cutSlice(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack knife) {
		int i = state.getValue(BITES);
		if (i < MAX_BITES) {
			level.setBlock(pos, state.setValue(BITES, i + 1), 3);
		} else {
			level.removeBlock(pos, false);
		}
		// Follows KnifeEvents.onCakeInteraction: bite-axis offset, rotated by facing.
		// Bites advance along model +Z, i.e. facing.getOpposite() in world.
		Direction advance = state.getValue(FACING).getOpposite();
		ItemUtils.spawnItemEntity(level, new ItemStack(slice.get()),
				pos.getX() + 0.5 + advance.getStepX() * (i * 0.1 - 0.5),
				pos.getY() + 0.2,
				pos.getZ() + 0.5 + advance.getStepZ() * (i * 0.1 - 0.5),
				advance.getStepX() * -0.05, 0, advance.getStepZ() * -0.05);
		knife.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
		level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
		level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
	}

	private static InteractionResult eatSlice(LevelAccessor level, BlockPos pos, BlockState state, Player player, FoodProperties food) {
		if (!player.canEat(food.canAlwaysEat())) {
			return InteractionResult.PASS;
		}
		player.awardStat(Stats.EAT_CAKE_SLICE);
		player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
		if (!level.isClientSide()) {
			for (var e : food.getEffects()) {
				if (level.getRandom().nextFloat() < e.getSecond()) {
					player.addEffect(new MobEffectInstance(e.getFirst()));
				}
			}
		}
		int i = state.getValue(BITES);
		level.gameEvent(player, GameEvent.EAT, pos);
		if (i < MAX_BITES) {
			level.setBlock(pos, state.setValue(BITES, i + 1), 3);
		} else {
			level.removeBlock(pos, false);
			level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		TDFoodItem.getFoodEffects(food, tooltip);
	}

}
