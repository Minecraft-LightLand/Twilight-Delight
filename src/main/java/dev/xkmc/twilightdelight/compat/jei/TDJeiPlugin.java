package dev.xkmc.twilightdelight.compat.jei;

import com.teamabnormals.neapolitan.core.Neapolitan;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCakes;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCauldron;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import twilightforest.init.TFItems;

@JeiPlugin
public class TDJeiPlugin implements IModPlugin {

	public static final ResourceLocation ID = new ResourceLocation(TwilightDelight.MODID, "main");

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
		registration.addRecipes(FROZEN.getRecipeType(), Proxy.getClientWorld().getRecipeManager().getAllRecipesFor(TDRecipes.WORLD_RECIPE.get()));
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(TFItems.ICE_BOMB.get().getDefaultInstance(), FROZEN.getRecipeType());
		registration.addRecipeCatalyst(DelightFood.GLACIER_ICE_TEA.item.asStack(), FROZEN.getRecipeType());
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			registration.addRecipeCatalyst(NeapolitanCakes.GLACIER.block.asStack(), FROZEN.getRecipeType());
			registration.addRecipeCatalyst(NeapolitanCauldron.GLACIER.iceCream.asStack(), FROZEN.getRecipeType());
			registration.addRecipeCatalyst(NeapolitanCauldron.GLACIER.milkshake.asStack(), FROZEN.getRecipeType());
		}
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
