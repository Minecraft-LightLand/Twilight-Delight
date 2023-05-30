package dev.xkmc.twilightdelight.init.data;

import com.google.gson.JsonObject;
import com.teamabnormals.neapolitan.core.Neapolitan;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCakes;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanFood;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
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

	private static String path = "";

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
					Ingredient.of(DelightFood.EXPERIMENT_113.item.get()),
					TDItems.TEARDROP_SWORD.get())::unlocks, DelightFood.EXPERIMENT_113.item.get())
					.save(pvd, TDItems.TEARDROP_SWORD.getId());
		}

		// smelting
		{

			tripleCook(pvd, DataIngredient.items(DelightFood.RAW_MEEF_SLICE.item.get()), DelightFood.COOKED_MEEF_SLICE.item, 1);
			tripleCook(pvd, DataIngredient.items(DelightFood.RAW_INSECT.item.get()), DelightFood.COOKED_INSECT.item, 1);
			tripleCook(pvd, DataIngredient.items(DelightFood.RAW_VENISON_RIB.item.get()), DelightFood.COOKED_VENISON_RIB.item, 1);
		}

		// food crafting
		{

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.BERRY_STICK.item.get(), 1)::unlockedBy, TFItems.TORCHBERRIES.get())
					.requires(Items.SWEET_BERRIES).requires(Items.GLOW_BERRIES).requires(TFItems.TORCHBERRIES.get())
					.requires(Items.STICK).save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(DelightFood.CHOCOLATE_WAFER.item.get(), 1)::unlockedBy, TFItems.MAZE_WAFER.get())
					.pattern("A").pattern("B").pattern("A")
					.define('A', TFItems.MAZE_WAFER.get())
					.define('B', Items.COCOA_BEANS)
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.THOUSAND_PLANT_STEW.item.get(), 1)::unlockedBy, TFBlocks.MAYAPPLE.get().asItem())
					.requires(Items.BOWL)
					.requires(TFBlocks.MAYAPPLE.get().asItem())
					.requires(TFBlocks.ROOT_STRAND.get().asItem())
					.requires(TFBlocks.FALLEN_LEAVES.get().asItem())
					.requires(TFBlocks.MOSS_PATCH.get().asItem())
					.requires(TFItems.LIVEROOT.get())
					.requires(TFBlocks.TORCHBERRY_PLANT.get().asItem())
					.requires(Items.VINE)
					.requires(TFBlocks.FIDDLEHEAD.get().asItem())
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(DelightFood.TORCHBERRY_COOKIE.item.get(), 8)::unlockedBy, TFItems.TORCHBERRIES.get())
					.pattern("BAB")
					.define('A', TFItems.TORCHBERRIES.get())
					.define('B', Items.WHEAT)
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.GLACIER_ICE_TEA.item.get(), 1)::unlockedBy, TFItems.ICE_BOMB.get())
					.requires(Items.GLASS_BOTTLE).requires(TFItems.ICE_BOMB.get())
					.requires(Items.ICE).requires(TFItems.ARCTIC_FUR.get())
					.requires(Items.SUGAR).save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.TWILIGHT_SPRING.item.get(), 1)::unlockedBy, TFItems.RAW_IRONWOOD.get())
					.requires(Items.GLASS_BOTTLE).requires(TFItems.RAW_IRONWOOD.get()).requires(Items.ICE).save(pvd);

		}

		//food cooking
		{
			path = "cooking/";
			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDBlocks.FIERY_SNAKES.get().asItem(),
							1, 800, 0.5f, Items.BOWL)::unlockedBy,
					TFItems.FIERY_BLOOD.get())
					.addIngredient(TagGen.HYDRA_MEAT)
					.addIngredient(TFItems.FIERY_BLOOD.get())
					.addIngredient(TFItems.NAGA_SCALE.get())
					.addIngredient(ModItems.TOMATO_SAUCE.get())
					.addIngredient(TFItems.TORCHBERRIES.get())
					.build(pvd, getID(TDBlocks.FIERY_SNAKES.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.FRIED_INSECT.item.get().asItem(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					DelightFood.RAW_INSECT.item.get())
					.addIngredient(DelightFood.RAW_INSECT.item.get())
					.addIngredient(ModItems.ONION.get())
					.addIngredient(Items.CARROT)
					.build(pvd, getID(DelightFood.FRIED_INSECT.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.GLOWSTEW.item.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					TFBlocks.MUSHGLOOM.get().asItem())
					.addIngredient(Items.GLOWSTONE_DUST)
					.addIngredient(TFBlocks.MUSHGLOOM.get().asItem())
					.addIngredient(TFItems.TORCHBERRIES.get())
					.build(pvd, getID(DelightFood.GLOWSTEW.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.GLOW_VENISON_RIB_WITH_PASTA.item.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					DelightFood.GLOWSTEW.item.get())
					.addIngredient(DelightFood.GLOWSTEW.item.get())
					.addIngredient(TagGen.VENSION_COOKED)
					.addIngredient(ModItems.RAW_PASTA.get())
					.addIngredient(TFItems.LIVEROOT.get())
					.addIngredient(Items.BEETROOT)
					.build(pvd, getID(DelightFood.GLOW_VENISON_RIB_WITH_PASTA.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TFItems.MEEF_STROGANOFF.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					TFItems.RAW_MEEF.get())
					.addIngredient(Items.MUSHROOM_STEW)
					.addIngredient(TagGen.MEEF_COOKED)
					.addIngredient(TFItems.LIVEROOT.get())
					.addIngredient(TFItems.TORCHBERRIES.get())
					.addIngredient(ModItems.ONION.get())
					.build(pvd, getID(TFItems.MEEF_STROGANOFF.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.GRILLED_GHAST.item.get(),
							1, 800, 0.35f, Items.BOWL)::unlockedBy,
					TFItems.EXPERIMENT_115.get())
					.addIngredient(ModItems.TOMATO.get())
					.addIngredient(Items.BEETROOT)
					.addIngredient(TFItems.FIERY_TEARS.get())
					.addIngredient(TFItems.EXPERIMENT_115.get(), 2)
					.build(pvd, getID(DelightFood.GRILLED_GHAST.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(TDBlocks.LILY_CHICKEN.get().asItem(),
							1, 400, 0.35f, Items.BOWL)::unlockedBy,
					TFBlocks.HUGE_LILY_PAD.get().asItem())
					.addIngredient(TFBlocks.HUGE_LILY_PAD.get().asItem())
					.addIngredient(ModBlocks.ROAST_CHICKEN_BLOCK.get().asItem())
					.addIngredient(TFBlocks.HUGE_WATER_LILY.get().asItem())
					.build(pvd, getID(TDBlocks.LILY_CHICKEN.getId()));
		}

		// drink cooking
		{

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.TEAR_DRINK.item.get().asItem(),
							1, 800, 0.35f, Items.GLASS_BOTTLE)::unlockedBy,
					TFItems.FIERY_TEARS.get())
					.addIngredient(TFItems.FIERY_TEARS.get())
					.addIngredient(Items.GHAST_TEAR)
					.build(pvd, getID(DelightFood.TEAR_DRINK.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.PHYTOCHEMICAL_JUICE.item.get().asItem(),
							1, 200, 0.35f, Items.GLASS_BOTTLE)::unlockedBy,
					TFItems.LIVEROOT.get())
					.addIngredient(TFItems.LIVEROOT.get())
					.addIngredient(TFItems.STEELEAF_INGOT.get())
					.addIngredient(Items.SUGAR)
					.build(pvd, getID(DelightFood.PHYTOCHEMICAL_JUICE.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.THORN_ROSE_TEA.item.get().asItem(),
							1, 200, 0.35f, Items.GLASS_BOTTLE)::unlockedBy,
					TFBlocks.THORN_ROSE.get().asItem())
					.addIngredient(TFBlocks.THORN_ROSE.get().asItem())
					.addIngredient(Items.SUGAR)
					.build(pvd, getID(DelightFood.THORN_ROSE_TEA.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.TORCHBERRY_JUICE.item.get().asItem(),
							1, 200, 0.35f, Items.GLASS_BOTTLE)::unlockedBy,
					TFItems.TORCHBERRIES.get())
					.addIngredient(TFItems.TORCHBERRIES.get())
					.addIngredient(Items.SUGAR)
					.build(pvd, getID(DelightFood.TORCHBERRY_JUICE.item.getId()));
		}

		// cutting
		{
			path = "cutting/";

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.RAW_MEEF.get()),
							Ingredient.of(ModTags.KNIVES),
							DelightFood.RAW_MEEF_SLICE.item.get(), 2)
					.build(pvd, getID(DelightFood.RAW_MEEF_SLICE.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.COOKED_MEEF.get()),
							Ingredient.of(ModTags.KNIVES),
							DelightFood.COOKED_MEEF_SLICE.item.get(), 2)
					.build(pvd, getID(DelightFood.COOKED_MEEF_SLICE.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.RAW_VENISON.get()),
							Ingredient.of(ModTags.KNIVES),
							DelightFood.RAW_VENISON_RIB.item.get(), 2)
					.build(pvd, getID(DelightFood.RAW_VENISON_RIB.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.COOKED_VENISON.get()),
							Ingredient.of(ModTags.KNIVES),
							DelightFood.COOKED_VENISON_RIB.item.get(), 2)
					.build(pvd, getID(DelightFood.COOKED_VENISON_RIB.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.HYDRA_CHOP.get()),
							Ingredient.of(ModTags.KNIVES),
							DelightFood.HYDRA_PIECE.item.get(), 2)
					.build(pvd, getID(DelightFood.HYDRA_PIECE.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TagGen.INSECT),
							Ingredient.of(ModTags.KNIVES),
							DelightFood.RAW_INSECT.item.get(), 2)
					.build(pvd, getID(DelightFood.RAW_INSECT.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.UR_GHAST_TROPHY.get()),
							Ingredient.of(ModTags.KNIVES),
							DelightFood.EXPERIMENT_113.item.get(), 9)
					.addResult(TFItems.EXPERIMENT_115.get(), 4)
					.addResultWithChance(DelightFood.EXPERIMENT_110.item.get(), 0.1f)
					.build(pvd, getID(DelightFood.EXPERIMENT_113.item.getId()));

		}

		// neapolitan
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			path = "neapolitan/";
			neapolitan(pvd, NeapolitanFood.AURORA_ICE_CREAM.item,
					NeapolitanFood.AURORA_MILKSHAKE.item,
					NeapolitanCakes.AURORA.block,
					TFBlocks.AURORA_BLOCK.get().asItem());
			neapolitan(pvd, NeapolitanFood.GLACIER_ICE_CREAM.item,
					NeapolitanFood.GLACIER_MILKSHAKE.item,
					NeapolitanCakes.GLACIER.block,
					TFItems.ICE_BOMB.get());
			neapolitan(pvd, NeapolitanFood.PHYTOCHEMICAL_ICE_CREAM.item,
					NeapolitanFood.PHYTOCHEMICAL_MILKSHAKE.item,
					NeapolitanCakes.PHYTOCHEMICAL.block,
					TFItems.STEELEAF_INGOT.get());
			neapolitan(pvd, NeapolitanFood.TORCHBERRY_ICE_CREAM.item,
					NeapolitanFood.TORCHBERRY_MILKSHAKE.item,
					NeapolitanCakes.TORCHBERRY.block,
					TFItems.TORCHBERRIES.get());
		}
	}

	private static void neapolitan(RegistrateRecipeProvider pvd, ItemEntry<?> ice_cream, ItemEntry<?> milkshake, BlockEntry<?> cake, Item ingredient) {
		TagKey<Item> milk = ItemTags.create(new ResourceLocation("forge", "milk"));
		unlock(pvd, new ShapelessRecipeBuilder(ice_cream.get(), 1)::unlockedBy, ingredient)
				.requires(Items.BOWL).requires(milk).requires(NeapolitanItems.ICE_CUBES.get()).requires(Items.SUGAR)
				.requires(ingredient)
				.save(ConditionalRecipeWrapper.mod(pvd, Neapolitan.MOD_ID), getID(ice_cream.getId()));

		unlock(pvd, new ShapelessRecipeBuilder(milkshake.get(), 3)::unlockedBy, ingredient)
				.requires(Items.GLASS_BOTTLE, 3).requires(milk).requires(NeapolitanItems.VANILLA_ICE_CREAM.get())
				.requires(ingredient)
				.save(ConditionalRecipeWrapper.mod(pvd, Neapolitan.MOD_ID), getID(milkshake.getId()));

		unlock(pvd, new ShapelessRecipeBuilder(milkshake.get(), 3)::unlockedBy, ingredient)
				.requires(Items.GLASS_BOTTLE, 3).requires(milk).requires(ice_cream.get())
				.requires(NeapolitanItems.DRIED_VANILLA_PODS.get())
				.save(ConditionalRecipeWrapper.mod(pvd, Neapolitan.MOD_ID), getID(milkshake.getId()) + "_alt");
	}

	private static String getID(ResourceLocation item) {
		return TwilightDelight.MODID + ":" + path + item.getPath();
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
