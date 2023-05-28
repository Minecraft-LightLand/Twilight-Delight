package dev.xkmc.twilightdelight.util;

import dev.xkmc.twilightdelight.content.item.tool.FieryKnifeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModAdvancements;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author Goulixiaoji
 */
public class FieryClickCuttingBoardUtil {

	public static void clickBoard(ItemStack tool, BlockPos posIn, Player player) {
		Level level = player.getLevel();
		BlockState state = level.getBlockState(posIn);
		BlockEntity be = level.getBlockEntity(posIn);

		if (state.getBlock() instanceof CuttingBoardBlock && tool.getItem() instanceof FieryKnifeItem) {
			if (be instanceof CuttingBoardBlockEntity board) {
				Optional<CuttingBoardRecipe> matchingRecipe = getMatchingRecipe(board, tool, player);

				matchingRecipe.ifPresent((recipe) -> {
					List<ItemStack> results = recipe.rollResults(level.random, tool.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE));
					for (ItemStack result : results) {
						Direction direction = state.getValue(CuttingBoardBlock.FACING).getCounterClockWise();
						ItemStack stack = result.copy();
						Container container = new SimpleContainer(stack);
						ItemStack newStack = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, container, level)
								.map((smeltingRecipe) -> smeltingRecipe.assemble(container)).orElse(stack);
						newStack.setCount(result.getCount());
						ItemUtils.spawnItemEntity(level, newStack.copy(),
								posIn.getX() + 0.5D + direction.getStepX() * 0.2D,
								posIn.getY() + 0.2D,
								posIn.getZ() + 0.5D + direction.getStepZ() * 0.2D,
								direction.getStepX() * 0.2F,
								0.0D,
								direction.getStepZ() * 0.2F);
					}


					if (player != null) {
						tool.hurtAndBreak(1, player, (user) -> {
							user.broadcastBreakEvent(EquipmentSlot.MAINHAND);
						});
					} else if (tool.hurt(1, level.random, (ServerPlayer) null)) {
						tool.setCount(0);
					}

					board.playProcessingSound(recipe.getSoundEventID(), tool, board.getStoredItem());
					board.removeItem();

					if (player instanceof ServerPlayer) {
						ModAdvancements.CUTTING_BOARD.trigger((ServerPlayer) player);
					}

				});
			}
		}//TODO validate
	}

	/**
	 * To get vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity.getMatchingRecipe(RecipeWrapper.class, ItemStack.class, Player.class)
	 */
	protected static Optional<CuttingBoardRecipe> getMatchingRecipe(CuttingBoardBlockEntity blockEntity, ItemStack toolItem, Player player) {
		try {
			Field inventory = blockEntity.getClass().getDeclaredField("inventory");
			Method getMatchingRecipe = blockEntity.getClass().getDeclaredMethod("getMatchingRecipe", RecipeWrapper.class, ItemStack.class, Player.class);

			// visit private
			inventory.setAccessible(true);
			getMatchingRecipe.setAccessible(true);

			return (Optional<CuttingBoardRecipe>) getMatchingRecipe.invoke(blockEntity, new RecipeWrapper((IItemHandlerModifiable) inventory.get(blockEntity)), toolItem, player);
		} catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			return Optional.empty();
		}//TODO validate
	}
}
