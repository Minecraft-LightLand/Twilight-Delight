package dev.xkmc.twilightdelight.init.registrate;

import dev.xkmc.l2library.base.recipe.BaseRecipe;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.twilightdelight.content.recipe.BaseEffectRecipe;
import dev.xkmc.twilightdelight.content.recipe.SimpleFrozenRecipe;
import dev.xkmc.twilightdelight.content.recipe.WorldInv;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDRecipes {

	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, TwilightDelight.MODID);
	public static RegistryObject<RecipeType<BaseEffectRecipe<?>>> WORLD_RECIPE = TwilightDelight.REGISTRATE.recipe(RECIPE_TYPES, "effects");

	public static final RegistryEntry<BaseRecipe.RecType<SimpleFrozenRecipe, BaseEffectRecipe<?>, WorldInv>> RS_FROZEN =
			TwilightDelight.REGISTRATE.simple("frozen", ForgeRegistries.Keys.RECIPE_SERIALIZERS,
					() -> new BaseRecipe.RecType<>(SimpleFrozenRecipe.class, WORLD_RECIPE));

	public static void register(IEventBus bus) {
		RECIPE_TYPES.register(bus);
	}

}
