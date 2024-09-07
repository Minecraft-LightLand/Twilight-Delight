package dev.xkmc.twilightdelight.content.recipe;

import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;

@SerialClass
public class SimpleFrozenRecipe extends BaseEffectRecipe<SimpleFrozenRecipe> {

	public SimpleFrozenRecipe() {
		super(TDRecipes.RS_FROZEN.get());
	}

}
