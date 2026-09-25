package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class TwilightCakeBlock extends CakeBlock {

	private final FoodProperties food;

	// Models are eaten from the north side, unlike vanilla cake: bite n leaves z >= 1 + 2n.
	// The arch decoration at the back persists in every state.
	protected static final VoxelShape[] SHAPE_BY_BITE = {
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D),
			Block.box(1.0D, 0.0D, 3.0D, 15.0D, 8.0D, 15.0D),
			Block.box(1.0D, 0.0D, 5.0D, 15.0D, 8.0D, 15.0D),
			Block.box(1.0D, 0.0D, 7.0D, 15.0D, 8.0D, 15.0D),
			Block.box(1.0D, 0.0D, 9.0D, 15.0D, 8.0D, 15.0D),
			Block.box(1.0D, 0.0D, 11.0D, 15.0D, 8.0D, 15.0D),
			Block.box(1.0D, 0.0D, 13.0D, 15.0D, 8.0D, 15.0D)
	};
	protected static final VoxelShape ARCH_SHAPE = Block.box(1.0D, 8.0D, 13.0D, 6.0D, 14.0D, 15.0D);

	public TwilightCakeBlock(FoodProperties food, Properties properties) {
		super(properties);
		this.food = food;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.or(SHAPE_BY_BITE[state.getValue(BITES)], ARCH_SHAPE);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide) {
			if (eatSlice(level, pos, state, player, food).consumesAction()) {
				return InteractionResult.SUCCESS;
			}
			if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
				return InteractionResult.CONSUME;
			}
		}
		return eatSlice(level, pos, state, player, food);
	}

	private static InteractionResult eatSlice(LevelAccessor level, BlockPos pos, BlockState state, Player player, FoodProperties food) {
		if (!player.canEat(food.canAlwaysEat())) {
			return InteractionResult.PASS;
		}
		player.awardStat(Stats.EAT_CAKE_SLICE);
		player.getFoodData().eat(food.nutrition(), food.saturation());
		if (!level.isClientSide()) {
			for (var e : food.effects()) {
				if (level.getRandom().nextFloat() < e.probability()) {
					player.addEffect(e.effect());
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
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		TDFoodItem.getFoodEffects(food, tooltip);
	}

}
