package dev.xkmc.twilightdelight.compat.jeed;

import com.google.gson.JsonObject;
import dev.xkmc.l2library.serial.codec.JsonCodec;
import net.mehvahdjukaar.jeed.forge.JeedImpl;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public record JeedEffectRecipeBuilded(ResourceLocation id, JeedEffectRecipeData data) implements FinishedRecipe {

	@Override
	public void serializeRecipeData(JsonObject json) {
		JsonObject obj = JsonCodec.toJson(data).getAsJsonObject();
		for (var e : obj.entrySet()) json.add(e.getKey(), e.getValue());
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getType() {
		return JeedImpl.getEffectProviderSerializer();
	}

	@Nullable
	@Override
	public JsonObject serializeAdvancement() {
		return null;
	}

	@Nullable
	@Override
	public ResourceLocation getAdvancementId() {
		return null;
	}
}
