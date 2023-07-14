package dev.xkmc.twilightdelight.content.recipe;

import dev.xkmc.l2library.serial.recipe.BaseRecipeBuilder;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class SimpleFrozenRecipeBuilder extends BaseRecipeBuilder<SimpleFrozenRecipeBuilder, SimpleFrozenRecipe, BaseEffectRecipe<?>, WorldInv> {

	public SimpleFrozenRecipeBuilder(Ingredient in, ItemStack out) {
		super(TDRecipes.RS_FROZEN.get());
		recipe.ingredient = in;
		recipe.result = out;
	}

	public SimpleFrozenRecipeBuilder(Ingredient in, Item out) {
		this(in, out.getDefaultInstance());
	}

}
