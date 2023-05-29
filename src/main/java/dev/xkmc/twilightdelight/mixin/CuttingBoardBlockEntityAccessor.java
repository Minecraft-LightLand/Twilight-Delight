package dev.xkmc.twilightdelight.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(CuttingBoardBlockEntity.class)
public interface CuttingBoardBlockEntityAccessor {

	@Accessor(remap = false)
	ItemStackHandler getInventory();

	@Invoker(remap = false)
	Optional<CuttingBoardRecipe> callGetMatchingRecipe(RecipeWrapper recipeWrapper, ItemStack toolStack, @Nullable Player player);

}
