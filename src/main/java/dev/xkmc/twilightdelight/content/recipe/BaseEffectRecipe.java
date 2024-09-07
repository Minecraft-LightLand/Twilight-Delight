package dev.xkmc.twilightdelight.content.recipe;

import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

@SerialClass
public class BaseEffectRecipe<T extends BaseEffectRecipe<T>> extends BaseRecipe<T, BaseEffectRecipe<?>, SingleRecipeInput> {

	@SerialField
	public Ingredient ingredient;

	@SerialField
	public ItemStack result;

	public BaseEffectRecipe(BaseRecipe.RecType<T, BaseEffectRecipe<?>, SingleRecipeInput> type) {
		super(type);
	}

	@Override
	public boolean matches(SingleRecipeInput worldInv, Level level) {
		return ingredient.test(worldInv.getItem(0));
	}

	@Override
	public ItemStack assemble(SingleRecipeInput worldInv, HolderLookup.Provider access) {
		var ans = result.copy();
		ans.setCount(result.getCount() * worldInv.getItem(0).getCount());
		return ans;
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider access) {
		return result;
	}

}
