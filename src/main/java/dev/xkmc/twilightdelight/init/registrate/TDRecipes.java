package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.serial.recipe.BaseRecipe;
import dev.xkmc.twilightdelight.content.recipe.BaseEffectRecipe;
import dev.xkmc.twilightdelight.content.recipe.SimpleFrozenRecipe;
import dev.xkmc.twilightdelight.content.recipe.WorldInv;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class TDRecipes {

	public static RegistryEntry<RecipeType<BaseEffectRecipe<?>>> WORLD_RECIPE = TwilightDelight.REGISTRATE.recipe("effects");

	public static final RegistryEntry<BaseRecipe.RecType<SimpleFrozenRecipe, BaseEffectRecipe<?>, WorldInv>> RS_FROZEN =
			TwilightDelight.REGISTRATE.simple("frozen", ForgeRegistries.Keys.RECIPE_SERIALIZERS,
					() -> new BaseRecipe.RecType<>(SimpleFrozenRecipe.class, WORLD_RECIPE));

	public static void register(IEventBus bus) {
	}

}
