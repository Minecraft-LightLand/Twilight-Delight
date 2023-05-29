package dev.xkmc.twilightdelight.init.data;

import com.google.gson.JsonObject;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class RecipeGen {

	public static void genRecipes(RegistrateRecipeProvider pvd) {

		// tool crafting
		{

			unlock(pvd, new ShapedRecipeBuilder(TDItems.FIERY_KNIFE.get(), 1)::unlockedBy, TFItems.FIERY_INGOT.get())
					.pattern(" A").pattern("B ")
					.define('A', TFItems.FIERY_INGOT.get())
					.define('B', Items.BLAZE_ROD)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(TDItems.IRONWOOD_KNIFE.get(), 1)::unlockedBy, TFItems.IRONWOOD_INGOT.get())
					.pattern(" A").pattern("B ")
					.define('A', TFItems.IRONWOOD_INGOT.get())
					.define('B', Tags.Items.RODS_WOODEN)
					.save(e -> pvd.accept(new NBTRecipe(e, TDItems.IRONWOOD_KNIFE.get().getDefault())));

			unlock(pvd, new ShapedRecipeBuilder(TDItems.STEELEAF_KNIFE.get(), 1)::unlockedBy, TFItems.STEELEAF_INGOT.get())
					.pattern(" A").pattern("B ")
					.define('A', TFItems.STEELEAF_INGOT.get())
					.define('B', Tags.Items.RODS_WOODEN)
					.save(e -> pvd.accept(new NBTRecipe(e, TDItems.STEELEAF_KNIFE.get().getDefault())));

			unlock(pvd, new ShapedRecipeBuilder(TDItems.KNIGHTMETAL_KNIFE.get(), 1)::unlockedBy, TFItems.KNIGHTMETAL_INGOT.get())
					.pattern(" A").pattern("B ")
					.define('A', TFItems.KNIGHTMETAL_INGOT.get())
					.define('B', Tags.Items.RODS_WOODEN)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(TDBlocks.MAZE_STOVE.get(), 1)::unlockedBy, TFItems.KNIGHTMETAL_INGOT.get())
					.pattern("KKK").pattern("MTM").pattern("MCM")
					.define('K', TFItems.KNIGHTMETAL_INGOT.get())
					.define('M', TFBlocks.MAZESTONE_BRICK.get())
					.define('T', TFItems.TORCHBERRIES.get())
					.define('C', Items.CAMPFIRE)
					.save(pvd);

			unlock(pvd, UpgradeRecipeBuilder.smithing(Ingredient.of(TFItems.FIERY_SWORD.get()),
					Ingredient.of(TDItems.Food.EXPERIMENT_113.item.get()),
					TDItems.TEARDROP_SWORD.get())::unlocks, TDItems.Food.EXPERIMENT_113.item.get())
					.save(pvd, TFItems.FIERY_SWORD.getId());
		}

		// smelting
		{
			tripleCook(pvd, DataIngredient.items(TDItems.Food.RAW_MEEF_SLICE.item.get()), TDItems.Food.COOKED_MEEF_SLICE.item, 1);
			tripleCook(pvd, DataIngredient.items(TDItems.Food.RAW_INSECT.item.get()), TDItems.Food.COOKED_INSECT.item, 1);
			tripleCook(pvd, DataIngredient.items(TDItems.Food.RAW_VENISON_RIB.item.get()), TDItems.Food.COOKED_VENISON_RIB.item, 1);
		}

		// food crafting
		{
			unlock(pvd, new ShapelessRecipeBuilder(TDItems.Food.AURORA_ICE_CREAM.item.get(), 1)::unlockedBy, TFBlocks.AURORA_BLOCK.get().asItem())
					.requires(Items.BOWL).requires(Items.SNOWBALL, 3).requires(TFBlocks.AURORA_BLOCK.get().asItem())
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(TDItems.Food.BERRY_STICK.item.get(), 1)::unlockedBy, TFItems.TORCHBERRIES.get())
					.requires(Items.SWEET_BERRIES).requires(Items.GLOW_BERRIES).requires(TFItems.TORCHBERRIES.get())
					.requires(Items.STICK).save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(TDItems.Food.CHOCOLATE_WAFER.item.get(), 1)::unlockedBy, TFItems.MAZE_WAFER.get())
					.pattern("A").pattern("B").pattern("A")
					.define('A', TFItems.MAZE_WAFER.get())
					.define('B', Items.COCOA_BEANS)
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(TDItems.Food.THOUSAND_PLANT_STEW.item.get(), 1)::unlockedBy, TFBlocks.MAYAPPLE.get().asItem())
					.requires(Items.BOWL)
					.requires(TFBlocks.MAYAPPLE.get().asItem())
					.requires(TFBlocks.ROOT_STRAND.get().asItem())
					.requires(TFBlocks.FALLEN_LEAVES.get().asItem())
					.requires(TFBlocks.MOSS_PATCH.get().asItem())
					.requires(TFItems.LIVEROOT.get())
					.requires(TFBlocks.TORCHBERRY_PLANT.get().asItem())
					.requires(Items.VINE)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(TDItems.Food.TORCHBERRY_COOKIE.item.get(), 8)::unlockedBy, TFItems.TORCHBERRIES.get())
					.pattern("BAB")
					.define('A', TFItems.TORCHBERRIES.get())
					.define('B', Items.WHEAT)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(TDBlocks.LILY_CHICKEN.get().asItem(), 1)::unlockedBy, TFBlocks.HUGE_LILY_PAD.get().asItem())
					.pattern("A").pattern("B").pattern("C")
					.define('A', TFBlocks.HUGE_LILY_PAD.get().asItem())
					.define('B', ModBlocks.ROAST_CHICKEN_BLOCK.get().asItem())
					.define('C', TFBlocks.HUGE_WATER_LILY.get().asItem())
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(TDItems.Food.GLACIER_ICE_TEA.item.get(), 1)::unlockedBy, TFItems.ICE_BOMB.get())
					.requires(Items.GLASS_BOTTLE).requires(TFItems.ICE_BOMB.get())
					.requires(Items.ICE).requires(TFItems.ARCTIC_FUR.get())
					.requires(Items.SUGAR).save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(TDItems.Food.TWILIGHT_SPRING.item.get(), 1)::unlockedBy, TFItems.RAW_IRONWOOD.get())
					.requires(Items.GLASS_BOTTLE).requires(TFItems.RAW_IRONWOOD.get()).requires(Items.ICE).save(pvd);

		}

		//food cooking
		{
			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDBlocks.FIERY_SNAKES.get().asItem(),
							1, 800, 0.5f, Items.BOWL)::unlockedBy,
					TFItems.FIERY_BLOOD.get())
					.addIngredient(TagGen.HYDRA_MEAT)
					.addIngredient(TFItems.FIERY_BLOOD.get())
					.addIngredient(TFItems.NAGA_SCALE.get())
					.addIngredient(ModItems.TOMATO_SAUCE.get())
					.addIngredient(TFItems.TORCHBERRIES.get())
					.build(pvd);

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDItems.Food.FRIED_INSECT.item.get().asItem(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					TDItems.Food.RAW_INSECT.item.get())
					.addIngredient(TDItems.Food.RAW_INSECT.item.get())
					.addIngredient(ModItems.ONION.get())
					.addIngredient(Items.CARROT)
					.build(pvd);

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDItems.Food.GLOWSTEW.item.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					TFBlocks.MUSHGLOOM.get().asItem())
					.addIngredient(Items.GLOWSTONE_DUST)
					.addIngredient(TFBlocks.MUSHGLOOM.get().asItem())
					.addIngredient(TFItems.TORCHBERRIES.get())
					.build(pvd);

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDItems.Food.GLOW_VENISON_RIB_WITH_PASTA.item.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					TDItems.Food.GLOWSTEW.item.get())
					.addIngredient(TagGen.VENSION_COOKED)
					.addIngredient(ModItems.RAW_PASTA.get())
					.addIngredient(TFItems.LIVEROOT.get())
					.addIngredient(Items.BEETROOT)
					.build(pvd);

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TFItems.MEEF_STROGANOFF.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					Items.MUSHROOM_STEW)
					.addIngredient(Items.MUSHROOM_STEW)
					.addIngredient(TagGen.MEEF_COOKED)
					.addIngredient(TFItems.LIVEROOT.get())
					.addIngredient(TFItems.TORCHBERRIES.get())
					.addIngredient(ModItems.ONION.get())
					.build(pvd);

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDItems.Food.GRILLED_GHAST.item.get(),
							1, 800, 0.35f, Items.BOWL)::unlockedBy,
					TFItems.EXPERIMENT_115.get())
					.addIngredient(ModItems.TOMATO.get())
					.addIngredient(Items.BEETROOT)
					.addIngredient(TFItems.FIERY_TEARS.get())
					.addIngredient(TFItems.EXPERIMENT_115.get(), 2)
					.build(pvd);

		}

		// drink cooking
		{

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDItems.Food.TEAR_DRINK.item.get().asItem(),
							1, 800, 0.35f, Items.GLASS_BOTTLE)::unlockedBy,
					TFItems.FIERY_TEARS.get())
					.addIngredient(TFItems.FIERY_TEARS.get())
					.addIngredient(TFItems.FIERY_BLOOD.get())
					.build(pvd);

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDItems.Food.PHYTOCHEMICAL_JUICE.item.get().asItem(),
							1, 200, 0.35f, Items.GLASS_BOTTLE)::unlockedBy,
					TFItems.LIVEROOT.get())
					.addIngredient(TFItems.LIVEROOT.get())
					.addIngredient(TFItems.STEELEAF_INGOT.get())
					.addIngredient(Items.SUGAR)
					.build(pvd);

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDItems.Food.THORN_ROSE_TEA.item.get().asItem(),
							1, 200, 0.35f, Items.GLASS_BOTTLE)::unlockedBy,
					TFBlocks.THORN_ROSE.get().asItem())
					.addIngredient(TFBlocks.THORN_ROSE.get().asItem())
					.addIngredient(Items.SUGAR)
					.build(pvd);

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDItems.Food.TORCHBERRY_JUICE.item.get().asItem(),
							1, 200, 0.35f, Items.GLASS_BOTTLE)::unlockedBy,
					TFItems.TORCHBERRIES.get())
					.addIngredient(TFItems.TORCHBERRIES.get())
					.addIngredient(Items.SUGAR)
					.build(pvd);
		}

		// cutting
		{

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.RAW_MEEF.get()),
							Ingredient.of(ModTags.KNIVES),
							TDItems.Food.RAW_MEEF_SLICE.item.get(), 2)
					.build(pvd);

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.COOKED_MEEF.get()),
							Ingredient.of(ModTags.KNIVES),
							TDItems.Food.COOKED_MEEF_SLICE.item.get(), 2)
					.build(pvd);

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.RAW_VENISON.get()),
							Ingredient.of(ModTags.KNIVES),
							TDItems.Food.RAW_VENISON_RIB.item.get(), 2)
					.build(pvd);

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.COOKED_VENISON.get()),
							Ingredient.of(ModTags.KNIVES),
							TDItems.Food.COOKED_VENISON_RIB.item.get(), 2)
					.build(pvd);

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.HYDRA_CHOP.get()),
							Ingredient.of(ModTags.KNIVES),
							TDItems.Food.HYDRA_PIECE.item.get(), 2)
					.build(pvd);

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TagGen.INSECT),
							Ingredient.of(ModTags.KNIVES),
							TDItems.Food.RAW_INSECT.item.get(), 2)
					.build(pvd);

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.UR_GHAST_TROPHY.get()),
							Ingredient.of(ModTags.KNIVES),
							TDItems.Food.EXPERIMENT_113.item.get(), 9)
					.addResult(TFItems.EXPERIMENT_115.get(), 4)
					.addResultWithChance(TDItems.Food.EXPERIMENT_113.item.get(), 0.1f)
					.build(pvd);

		}
	}

	private static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	private static void tripleCook(RegistrateRecipeProvider pvd, DataIngredient in, Supplier<Item> out, int exp) {
		pvd.smelting(in, out, exp, 200);
		pvd.smoking(in, out, exp, 100);
		pvd.campfire(in, out, exp, 300);
	}

	private record NBTRecipe(FinishedRecipe recipe, ItemStack stack) implements FinishedRecipe {

		@Override
		public void serializeRecipeData(JsonObject json) {
			recipe.serializeRecipeData(json);
			json.getAsJsonObject("result").addProperty("nbt", stack.getOrCreateTag().toString());
		}

		@Override
		public ResourceLocation getId() {
			return recipe.getId();
		}

		@Override
		public RecipeSerializer<?> getType() {
			return recipe.getType();
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return recipe.serializeAdvancement();
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return recipe.getAdvancementId();
		}

	}

}
