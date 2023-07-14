package dev.xkmc.twilightdelight.content.recipe;

import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import net.minecraft.resources.ResourceLocation;

@SerialClass
public class SimpleFrozenRecipe extends BaseEffectRecipe<SimpleFrozenRecipe> {

	public SimpleFrozenRecipe(ResourceLocation id) {
		super(id, TDRecipes.RS_FROZEN.get());
	}

}
