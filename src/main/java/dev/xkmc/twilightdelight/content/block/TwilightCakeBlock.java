package dev.xkmc.twilightdelight.content.block;

import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

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
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.or(SHAPE_BY_BITE[state.getValue(BITES)], ARCH_SHAPE);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(ItemTags.CANDLES) && state.getValue(BITES) == 0 &&
				Block.byItem(stack.getItem()) instanceof CandleBlock candle) {
			if (!player.isCreative()) stack.shrink(1);
			level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.setBlockAndUpdate(pos, CandleCakeBlock.byCandle(candle));
			level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
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
