package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;
import java.util.Random;

/**
 * The claw of a Helmet Crab. It is a knife with the stats of an iron knife, and it
 * keeps the shears behaviour of the vanilla shears on top of that.
 */
public class HelmetCrabClawItem extends KnifeItem {

	public HelmetCrabClawItem(Properties properties) {
		super(Tiers.IRON, 0.5F, -2.0F, properties);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		return super.canPerformAction(stack, toolAction) || ToolActions.DEFAULT_SHEARS_ACTIONS.contains(toolAction);
	}

	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		return super.isCorrectToolForDrops(stack, state) || isShearable(state);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (state.is(Blocks.COBWEB) || state.is(BlockTags.LEAVES)) {
			return 15.0F;
		}
		if (state.is(BlockTags.WOOL)) {
			return 5.0F;
		}
		if (state.is(Blocks.VINE) || state.is(Blocks.GLOW_LICHEN)) {
			return 2.0F;
		}
		return super.getDestroySpeed(stack, state);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
		if (entity instanceof IForgeShearable target) {
			if (entity.level().isClientSide) {
				return InteractionResult.SUCCESS;
			}
			BlockPos pos = entity.blockPosition();
			if (target.isShearable(stack, entity.level(), pos)) {
				List<ItemStack> drops = target.onSheared(player, stack, entity.level(), pos,
						EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack));
				Random rand = new Random();
				drops.forEach(d -> {
					ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
					ent.setDeltaMovement(ent.getDeltaMovement().add(
							(rand.nextFloat() - rand.nextFloat()) * 0.1F,
							rand.nextFloat() * 0.05F,
							(rand.nextFloat() - rand.nextFloat()) * 0.1F));
				});
				stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof GrowingPlantHeadBlock head && !head.isMaxAge(state)) {
			Player player = context.getPlayer();
			ItemStack stack = context.getItemInHand();
			if (player instanceof ServerPlayer serverPlayer) {
				CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
			}
			level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
			BlockState grown = head.getMaxAgeState(state);
			level.setBlockAndUpdate(pos, grown);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, grown));
			if (player != null) {
				stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(context.getHand()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.useOn(context);
	}

	private static boolean isShearable(BlockState state) {
		return state.is(Blocks.COBWEB) || state.is(Blocks.REDSTONE_WIRE) || state.is(Blocks.TRIPWIRE);
	}

}
