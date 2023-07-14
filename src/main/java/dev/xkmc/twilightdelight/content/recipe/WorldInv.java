package dev.xkmc.twilightdelight.content.recipe;

import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class WorldInv extends SimpleContainer implements BaseRecipe.RecInv<BaseEffectRecipe<?>> {

	public WorldInv(ItemStack stack) {
		super(1);
		addItem(stack);
	}
}
