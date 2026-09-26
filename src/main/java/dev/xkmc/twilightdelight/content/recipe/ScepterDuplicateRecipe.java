package dev.xkmc.twilightdelight.content.recipe;

import com.google.gson.JsonObject;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

/**
 * Duplicates a scepter with a fixed shaped layout: scepter in the middle,
 * witchcraft bone below it, material in the remaining slots. A fresh scepter
 * of the same kind is crafted while the input scepter stays in the grid with
 * all its uses. Follows Twilight Forest repair recipes.
 */
public class ScepterDuplicateRecipe extends ShapedRecipe {

	private final Item scepter;

	public ScepterDuplicateRecipe(ShapedRecipe base) {
		super(base.getId(), base.getGroup(), base.category(), base.getWidth(), base.getHeight(),
				base.getIngredients(), base.getResultItem(null).copy(), true);
	 // getResultItem ignores its argument and returns the plain result
		this.scepter = getResultItem(null).getItem();
	}

	@Override
	public boolean matches(CraftingContainer inv, Level level) {
		if (!super.matches(inv, level)) return false;
		ItemStack center = inv.getItem(4);
		return !center.isEmpty() && center.is(scepter) && !center.isDamaged();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
		NonNullList<ItemStack> ans = super.getRemainingItems(inv);
		ItemStack center = inv.getItem(4);
		if (!center.isEmpty() && center.is(scepter)) {
			ans.set(4, center.copy());
		}
		return ans;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return TDRecipes.RS_SCEPTER_DUPLICATE.get();
	}

	public static class Serializer implements RecipeSerializer<ScepterDuplicateRecipe> {

		@Override
		public ScepterDuplicateRecipe fromJson(ResourceLocation id, JsonObject json) {
			return new ScepterDuplicateRecipe(RecipeSerializer.SHAPED_RECIPE.fromJson(id, json));
		}

		@Override
		public ScepterDuplicateRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			return new ScepterDuplicateRecipe(RecipeSerializer.SHAPED_RECIPE.fromNetwork(id, buf));
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, ScepterDuplicateRecipe recipe) {
			RecipeSerializer.SHAPED_RECIPE.toNetwork(buf, recipe);
		}

	}

}
