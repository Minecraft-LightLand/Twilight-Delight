package dev.xkmc.twilightdelight.content.recipe;

import dev.xkmc.l2core.serial.recipe.BaseRecipeBuilder;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class SimpleFrozenRecipeBuilder extends BaseRecipeBuilder<SimpleFrozenRecipeBuilder, SimpleFrozenRecipe, BaseEffectRecipe<?>, SingleRecipeInput> {

	public SimpleFrozenRecipeBuilder(Ingredient in, ItemStack out) {
		super(TDRecipes.RS_FROZEN.get(), out.getItem());
		recipe.ingredient = in;
		recipe.result = out;
	}

	public SimpleFrozenRecipeBuilder(Ingredient in, Item out) {
		this(in, out.getDefaultInstance());
	}

}
