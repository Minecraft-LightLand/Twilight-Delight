package dev.xkmc.twilightdelight.util;

import dev.xkmc.twilightdelight.content.item.tool.FieryKnifeItem;
import dev.xkmc.twilightdelight.mixin.CuttingBoardBlockEntityAccessor;
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
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModAdvancements;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.List;
import java.util.Optional;

//FIXME clean up old shit

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
					} else if (tool.hurt(1, level.random, null)) {
						tool.setCount(0);
					}

					board.playProcessingSound(recipe.getSoundEventID(), tool, board.getStoredItem());
					board.removeItem();

					if (player instanceof ServerPlayer) {
						ModAdvancements.CUTTING_BOARD.trigger((ServerPlayer) player);
					}

				});
			}
		}
	}

	// use mixin instead of reflection
	protected static Optional<CuttingBoardRecipe> getMatchingRecipe(CuttingBoardBlockEntity blockEntity, ItemStack toolItem, Player player) {
		CuttingBoardBlockEntityAccessor accessor = (CuttingBoardBlockEntityAccessor) blockEntity;
		return accessor.callGetMatchingRecipe(new RecipeWrapper(accessor.getInventory()), toolItem, player);
	}
}
