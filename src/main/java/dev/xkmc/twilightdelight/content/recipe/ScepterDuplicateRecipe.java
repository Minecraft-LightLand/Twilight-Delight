package dev.xkmc.twilightdelight.content.recipe;

import dev.xkmc.l2core.serial.recipe.AbstractShapedRecipe;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

/**
 * Duplicates a scepter with a fixed shaped layout: scepter in the middle,
 * witchcraft bone below it, material in the remaining slots. A fresh scepter
 * of the same kind is crafted while the input scepter stays in the grid with
 * all its uses. Follows Twilight Forest repair recipes.
 */
public class ScepterDuplicateRecipe extends AbstractShapedRecipe<ScepterDuplicateRecipe> {

	private final Item scepter;

	public ScepterDuplicateRecipe(String group, ShapedRecipePattern pattern, ItemStack result) {
		super(group, pattern, result);
		this.scepter = result.getItem();
	}

	@Override
	public boolean matches(CraftingInput inv, Level level) {
		if (!super.matches(inv, level)) return false;
		ItemStack center = inv.getItem(4);
		return !center.isEmpty() && center.is(scepter) && !center.isDamaged();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput inv) {
		NonNullList<ItemStack> ans = super.getRemainingItems(inv);
		ItemStack center = inv.getItem(4);
		if (!center.isEmpty() && center.is(scepter)) {
			ans.set(4, center.copy());
		}
		return ans;
	}

	@Override
	public Serializer getSerializer() {
		return TDRecipes.RS_SCEPTER_DUPLICATE.get();
	}

	public static class Serializer extends AbstractShapedRecipe.Serializer<ScepterDuplicateRecipe> {

		public Serializer() {
			super(ScepterDuplicateRecipe::new);
		}

	}

}
