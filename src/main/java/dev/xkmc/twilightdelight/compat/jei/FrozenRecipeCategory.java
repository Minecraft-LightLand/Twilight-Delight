package dev.xkmc.twilightdelight.compat.jei;

import dev.xkmc.l2core.serial.recipe.BaseRecipeCategory;
import dev.xkmc.l2serial.util.Wrappers;
import dev.xkmc.twilightdelight.content.recipe.BaseEffectRecipe;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.data.LangData;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import twilightforest.init.TFItems;

public class FrozenRecipeCategory extends BaseRecipeCategory<BaseEffectRecipe<?>, FrozenRecipeCategory> {

	protected static final ResourceLocation BG = TwilightDelight.loc("textures/jei/background.png");

	public FrozenRecipeCategory() {
		super(TwilightDelight.loc("frozen"), Wrappers.cast(BaseEffectRecipe.class));
	}

	public FrozenRecipeCategory init(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(BG, 0, 0, 72, 18);
		icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, TFItems.ICE_BOMB.get().getDefaultInstance());
		return this;
	}

	@Override
	public Component getTitle() {
		return LangData.FROZEN_TITLE.get();
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, BaseEffectRecipe<?> recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.ingredient);
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 1).addItemStack(recipe.result);
	}

}
