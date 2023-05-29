package dev.xkmc.twilightdelight.init.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public record ConditionalRecipeWrapper(FinishedRecipe base, String modid) implements FinishedRecipe {

	public ConditionalRecipeWrapper(FinishedRecipe base, String modid) {
		this.base = base;
		this.modid = modid;
	}

	public static Consumer<FinishedRecipe> mod(RegistrateRecipeProvider pvd, String modid) {
		return (r) -> pvd.accept(new ConditionalRecipeWrapper(r, modid));
	}

	public void serializeRecipeData(JsonObject pJson) {
		this.base.serializeRecipeData(pJson);
	}

	public ResourceLocation getId() {
		return this.base.getId();
	}

	public RecipeSerializer<?> getType() {
		return this.base.getType();
	}

	@Nullable
	public JsonObject serializeAdvancement() {
		return this.base.serializeAdvancement();
	}

	@Nullable
	public ResourceLocation getAdvancementId() {
		return this.base.getAdvancementId();
	}

	public JsonObject serializeRecipe() {
		JsonObject ans = this.base.serializeRecipe();
		JsonArray conditions = new JsonArray();
		JsonObject condition = new JsonObject();
		condition.addProperty("type", "forge:mod_loaded");
		condition.addProperty("modid", this.modid);
		conditions.add(condition);
		ans.add("conditions", conditions);
		return ans;
	}

	public FinishedRecipe base() {
		return this.base;
	}

	public String modid() {
		return this.modid;
	}
}
