package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.List;

/**
 * The claw of a Helmet Crab. It is a knife with the stats of an iron knife, and it
 * keeps the shears behaviour of the vanilla shears on top of that.
 */
public class HelmetCrabClawItem extends KnifeItem {

	public HelmetCrabClawItem(Properties properties) {
		super(Tiers.IRON, properties.component(DataComponents.TOOL, createToolProperties()));
	}

	/**
	 * The knife rules of the iron tier, plus the shears rules of the vanilla shears.
	 */
	private static Tool createToolProperties() {
		return new Tool(List.of(
				Tool.Rule.deniesDrops(Tiers.IRON.getIncorrectBlocksForDrops()),
				Tool.Rule.minesAndDrops(ModTags.Blocks.MINEABLE_WITH_KNIFE, Tiers.IRON.getSpeed()),
				Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F),
				Tool.Rule.overrideSpeed(BlockTags.LEAVES, 15.0F),
				Tool.Rule.overrideSpeed(BlockTags.WOOL, 5.0F),
				Tool.Rule.overrideSpeed(List.of(Blocks.VINE, Blocks.GLOW_LICHEN), 2.0F)),
				1.0F, 1);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
		return super.canPerformAction(stack, itemAbility) || ItemAbilities.DEFAULT_SHEARS_ACTIONS.contains(itemAbility);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
		if (entity instanceof IShearable target) {
			BlockPos pos = entity.blockPosition();
			boolean isClient = entity.level().isClientSide;
			if (target.isShearable(player, stack, entity.level(), pos)) {
				List<ItemStack> drops = target.onSheared(player, stack, entity.level(), pos);
				if (!isClient) {
					for (ItemStack drop : drops) {
						target.spawnShearedDrop(entity.level(), pos, drop);
					}
				}
				entity.gameEvent(GameEvent.SHEAR, player);
				if (!isClient) {
					stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
				}
				return InteractionResult.sidedSuccess(isClient);
			}
		}
		return InteractionResult.PASS;
	}

	/**
	 * Same as the vanilla shears: the claw takes damage when it breaks a block it
	 * can shear, the tool component takes care of the speeds and the drops.
	 */
	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		if (!level.isClientSide && !state.is(BlockTags.FIRE)) {
			stack.hurtAndBreak(1, entity, EquipmentSlot.MAINHAND);
		}
		return state.is(BlockTags.LEAVES)
				|| state.is(Blocks.COBWEB)
				|| state.is(Blocks.SHORT_GRASS)
				|| state.is(Blocks.FERN)
				|| state.is(Blocks.DEAD_BUSH)
				|| state.is(Blocks.HANGING_ROOTS)
				|| state.is(Blocks.VINE)
				|| state.is(Blocks.TRIPWIRE)
				|| state.is(BlockTags.WOOL);
	}

	/**
	 * The shears trim the crops they are used on, the knife does not.
	 */
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		BlockState trimmed = state.getToolModifiedState(context, ItemAbilities.SHEARS_TRIM, false);
		if (trimmed != null) {
			Player player = context.getPlayer();
			ItemStack stack = context.getItemInHand();
			if (player instanceof ServerPlayer serverPlayer) {
				CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
			}
			level.setBlockAndUpdate(pos, trimmed);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, trimmed));
			if (player != null) {
				stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.useOn(context);
	}

}
