package dev.xkmc.twilightdelight.init.registrate;

import dev.xkmc.l2core.init.reg.simple.SR;
import dev.xkmc.l2core.init.reg.simple.Val;
import dev.xkmc.l2core.serial.recipe.BaseRecipe;
import dev.xkmc.twilightdelight.content.recipe.BaseEffectRecipe;
import dev.xkmc.twilightdelight.content.recipe.SimpleFrozenRecipe;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class TDRecipes {

	private static final SR<RecipeType<?>> RT = SR.of(TwilightDelight.REG, BuiltInRegistries.RECIPE_TYPE);
	private static final SR<RecipeSerializer<?>> RS = SR.of(TwilightDelight.REG, BuiltInRegistries.RECIPE_SERIALIZER);
	public static Val<RecipeType<BaseEffectRecipe<?>>> WORLD_RECIPE = RT.reg("effects", RecipeType::simple);

	public static final Val<BaseRecipe.RecType<SimpleFrozenRecipe, BaseEffectRecipe<?>, SingleRecipeInput>> RS_FROZEN =
			RS.reg("frozen", () -> new BaseRecipe.RecType<>(SimpleFrozenRecipe.class, WORLD_RECIPE));

	public static void register() {
	}

}
