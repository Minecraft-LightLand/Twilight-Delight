package dev.xkmc.twilightdelight.content.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ScepterDuplicateRecipeBuilder {

	private final Item result;
	private final List<String> rows = Lists.newArrayList();
	private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	@Nullable
	private String group;

	public static ScepterDuplicateRecipeBuilder shaped(ItemLike result) {
		return new ScepterDuplicateRecipeBuilder(result.asItem());
	}

	private ScepterDuplicateRecipeBuilder(Item result) {
		this.result = result;
	}

	public ScepterDuplicateRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public ScepterDuplicateRecipeBuilder define(Character symbol, TagKey<Item> tag) {
		return define(symbol, Ingredient.of(tag));
	}

	public ScepterDuplicateRecipeBuilder define(Character symbol, ItemLike item) {
		return define(symbol, Ingredient.of(item));
	}

	public ScepterDuplicateRecipeBuilder define(Character symbol, Ingredient ingredient) {
		if (this.key.containsKey(symbol)) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
		} else if (symbol == ' ') {
			throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
		} else {
			this.key.put(symbol, ingredient);
			return this;
		}
	}

	public ScepterDuplicateRecipeBuilder pattern(String pattern) {
		if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
			throw new IllegalArgumentException("Pattern must be the same width on every line!");
		} else {
			this.rows.add(pattern);
			return this;
		}
	}

	public ScepterDuplicateRecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
		this.advancement.addCriterion(name, trigger);
		return this;
	}

	public void save(Consumer<FinishedRecipe> out, ResourceLocation id) {
		ensureValid(id);
		this.advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id))
				.requirements(RequirementsStrategy.OR);
		ResourceLocation advancementId = id.withPrefix("recipes/equipment/");
		out.accept(new Result(id, this.group == null ? "" : this.group,
				this.rows, this.key, this.result, this.advancement, advancementId));
	}

	private void ensureValid(ResourceLocation id) {
		if (this.rows.size() != 3 || this.rows.stream().anyMatch(e -> e.length() != 3)) {
			throw new IllegalStateException("Scepter duplication recipe " + id + " must have a 3x3 pattern!");
		}
		int scepters = 0;
		for (String row : this.rows) {
			for (int i = 0; i < row.length(); i++) {
				if (row.charAt(i) == 'S') scepters++;
			}
		}
		if (scepters != 1 || this.rows.get(1).charAt(1) != 'S') {
			throw new IllegalStateException("Scepter duplication recipe " + id + " must have exactly one 'S' in the middle!");
		}
		Set<Character> set = Sets.newHashSet(this.key.keySet());
		set.remove(' ');
		for (String row : this.rows) {
			for (int i = 0; i < row.length(); i++) {
				char c = row.charAt(i);
				if (!this.key.containsKey(c) && c != ' ') {
					throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c + "'");
				}
				set.remove(c);
			}
		}
		if (!set.isEmpty()) {
			throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
		}
		if (this.advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}

	public record Result(ResourceLocation id, String group, List<String> pattern, Map<Character, Ingredient> key, Item result,
						 Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {

		@Override
		public void serializeRecipeData(JsonObject json) {
			json.addProperty("group", group);
			json.addProperty("category", CraftingBookCategory.EQUIPMENT.getSerializedName());
			JsonArray patternArray = new JsonArray();
			for (String row : pattern) {
				patternArray.add(row);
			}
			json.add("pattern", patternArray);
			JsonObject keyJson = new JsonObject();
			for (Map.Entry<Character, Ingredient> entry : key.entrySet()) {
				keyJson.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
			}
			json.add("key", keyJson);
			JsonObject resultJson = new JsonObject();
			resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString());
			json.add("result", resultJson);
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return TDRecipes.RS_SCEPTER_DUPLICATE.get();
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return advancement.serializeToJson();
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return advancementId;
		}

	}

}
