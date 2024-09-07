package dev.xkmc.twilightdelight.compat.jei;

import dev.xkmc.l2serial.util.Wrappers;
import dev.xkmc.twilightdelight.content.recipe.BaseEffectRecipe;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.integration.jei.FDRecipeTypes;

@JeiPlugin
public class TDJeiPlugin implements IModPlugin {

	public static final ResourceLocation ID = TwilightDelight.loc("main");

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	public final FrozenRecipeCategory FROZEN = new FrozenRecipeCategory();

	public IGuiHelper GUI_HELPER;

	public TDJeiPlugin() {
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {

	}

	@Override
	public void registerIngredients(IModIngredientRegistration registration) {
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(FROZEN.init(helper));
		GUI_HELPER = helper;
	}

	@Override
	public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(FROZEN.getRecipeType(),
				Minecraft.getInstance().level.getRecipeManager()
						.getAllRecipesFor(TDRecipes.WORLD_RECIPE.get())
						.stream().<BaseEffectRecipe<?>>map(e -> Wrappers.cast(e.value())).toList());
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(TFItems.ICE_BOMB.get().getDefaultInstance(), FROZEN.getRecipeType());
		registration.addRecipeCatalyst(DelightFood.GLACIER_ICE_TEA.item.asStack(), FROZEN.getRecipeType());
		/*TODO neapolitan
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			registration.addRecipeCatalyst(NeapolitanCakes.GLACIER.block.asStack(), FROZEN.getRecipeType());
			registration.addRecipeCatalyst(NeapolitanFood.GLACIER_ICE_CREAM.item.asStack(), FROZEN.getRecipeType());
			registration.addRecipeCatalyst(NeapolitanFood.GLACIER_MILKSHAKE.item.asStack(), FROZEN.getRecipeType());
		}*/
		registration.addRecipeCatalyst(TDBlocks.FIERY_POT.asStack(), FDRecipeTypes.COOKING);
		registration.addRecipeCatalyst(TDBlocks.MAZE_STOVE.asStack(), RecipeTypes.CAMPFIRE_COOKING);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
	}

	@Override
	public void registerAdvanced(IAdvancedRegistration registration) {
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}


}
