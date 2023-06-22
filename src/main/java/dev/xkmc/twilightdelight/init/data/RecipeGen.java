package dev.xkmc.twilightdelight.init.data;

import com.google.gson.JsonObject;
import com.teamabnormals.neapolitan.core.Neapolitan;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.content.recipe.SimpleFrozenRecipeBuilder;
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
import vectorwing.farmersdelight.common.tag.ForgeTags;
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
					Ingredient.of(DelightFood.EXPERIMENT_110.item.get()),
					TDItems.TEARDROP_SWORD.get())::unlocks, DelightFood.EXPERIMENT_110.item.get())
					.save(pvd, TDItems.TEARDROP_SWORD.getId());
		}

		//misc
		{
			unlock(pvd, new ShapelessRecipeBuilder(TFItems.RAW_IRONWOOD.get(), 1)::unlockedBy,
					TFItems.LIVEROOT.get()).requires(TFItems.LIVEROOT.get())
					.requires(Items.IRON_INGOT).requires(Items.GOLD_NUGGET)
					.save(pvd, getID(TFItems.RAW_IRONWOOD.getId()));

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

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.CHOCOLATE_113.item.get(), 1)::unlockedBy, DelightFood.EXPERIMENT_113.item.get())
					.requires(DelightFood.EXPERIMENT_113.item.get())
					.requires(TagGen.MILK)
					.requires(Items.SUGAR)
					.requires(Items.COCOA_BEANS)
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.MILKY_113.item.get(), 1)::unlockedBy, DelightFood.EXPERIMENT_113.item.get())
					.requires(DelightFood.EXPERIMENT_113.item.get())
					.requires(TagGen.MILK)
					.requires(Items.SUGAR)
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.HONEY_113.item.get(), 1)::unlockedBy, DelightFood.EXPERIMENT_113.item.get())
					.requires(DelightFood.EXPERIMENT_113.item.get())
					.requires(Items.HONEY_BOTTLE)
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.GLOW_113.item.get(), 1)::unlockedBy, DelightFood.EXPERIMENT_113.item.get())
					.requires(DelightFood.EXPERIMENT_113.item.get())
					.requires(DelightFood.GLOWSTEW.item.get())
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.GHAST_BURGER.item.get(), 1)::unlockedBy, TFItems.EXPERIMENT_115.get())
					.requires(ForgeTags.BREAD)
					.requires(TFItems.EXPERIMENT_115.get())
					.requires(ForgeTags.VEGETABLES_BEETROOT)
					.requires(ForgeTags.CROPS_TOMATO)
					.requires(ForgeTags.CROPS_ONION)
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.HYDRA_BURGER.item.get(), 1)::unlockedBy, TFItems.HYDRA_CHOP.get())
					.requires(ForgeTags.BREAD)
					.requires(TagGen.HYDRA_MEAT)
					.requires(ForgeTags.SALAD_INGREDIENTS)
					.requires(ForgeTags.CROPS_TOMATO)
					.requires(ForgeTags.CROPS_ONION)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(DelightFood.CHOCOLATE_WAFER.item.get(), 1)::unlockedBy, TFItems.MAZE_WAFER.get())
					.pattern("A").pattern("B").pattern("A")
					.define('A', TFItems.MAZE_WAFER.get())
					.define('B', Items.COCOA_BEANS)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(DelightFood.TORCHBERRY_COOKIE.item.get(), 8)::unlockedBy, TFItems.TORCHBERRIES.get())
					.pattern("BAB")
					.define('A', TFItems.TORCHBERRIES.get())
					.define('B', Items.WHEAT)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(TFItems.MAZE_WAFER.get(), 12)::unlockedBy, TFItems.LIVEROOT.get())
					.pattern("AAA").pattern("BCB").pattern("AAA")
					.define('A', Items.WHEAT)
					.define('B', TagGen.MILK)
					.define('C', TFItems.LIVEROOT.get())
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.GLACIER_ICE_TEA.item.get(), 1)::unlockedBy, TFItems.ICE_BOMB.get())
					.requires(Items.GLASS_BOTTLE).requires(TFItems.ICE_BOMB.get())
					.requires(Items.ICE).requires(TFItems.ARCTIC_FUR.get())
					.requires(Items.SUGAR).save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.TWILIGHT_SPRING.item.get(), 1)::unlockedBy, TFItems.RAW_IRONWOOD.get())
					.requires(Items.GLASS_BOTTLE).requires(TFItems.RAW_IRONWOOD.get()).requires(Items.ICE).save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(TDBlocks.MEEF_WELLINGTON.get(), 1)::unlockedBy, DelightFood.MUSHGLOOM_SAUCE.item.get())
					.pattern("BAB").pattern("DCD").pattern("FEF")
					.define('A', ModItems.PIE_CRUST.get())
					.define('B', Tags.Items.EGGS)
					.define('C', DelightFood.MUSHGLOOM_SAUCE.item.get())
					.define('D', TagGen.MEEF_COOKED)
					.define('E', Items.BOWL)
					.define('F', ModItems.BACON.get())
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(DelightFood.MEEF_WRAP.item.get(), 1)::unlockedBy, TFItems.RAW_MEEF.get())
					.requires(ForgeTags.BREAD).requires(TagGen.MEEF_COOKED)
					.requires(ForgeTags.SALAD_INGREDIENTS)
					.requires(ForgeTags.CROPS_ONION)
					.save(pvd);

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

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.MUSHGLOOM_SAUCE.item.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					TFBlocks.MUSHGLOOM.get().asItem())
					.addIngredient(Items.BROWN_MUSHROOM)
					.addIngredient(TFBlocks.MUSHGLOOM.get().asItem())
					.addIngredient(ModItems.ONION.get())
					.build(pvd, getID(DelightFood.MUSHGLOOM_SAUCE.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.GLOW_VENISON_RIB_WITH_PASTA.item.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					DelightFood.GLOWSTEW.item.get())
					.addIngredient(DelightFood.GLOWSTEW.item.get())
					.addIngredient(TagGen.VENSION_RAW)
					.addIngredient(ModItems.RAW_PASTA.get())
					.addIngredient(TFItems.LIVEROOT.get())
					.addIngredient(Items.BEETROOT)
					.build(pvd, getID(DelightFood.GLOW_VENISON_RIB_WITH_PASTA.item.getId()));

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.MUSHGLOOM_MEEF_PASTA.item.get(),
							1, 200, 0.35f, Items.BOWL)::unlockedBy,
					DelightFood.MUSHGLOOM_SAUCE.item.get())
					.addIngredient(DelightFood.MUSHGLOOM_SAUCE.item.get())
					.addIngredient(TagGen.MEEF_RAW)
					.addIngredient(ModItems.RAW_PASTA.get())
					.build(pvd, getID(DelightFood.MUSHGLOOM_MEEF_PASTA.item.getId()));

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

			unlock(pvd, CookingPotRecipeBuilder.cookingPotRecipe(DelightFood.THOUSAND_PLANT_STEW.item.get(),
							1, 400, 0.35f, Items.BOWL)::unlockedBy,
					TFItems.LIVEROOT.get())
					.addIngredient(TFBlocks.ROOT_STRAND.get().asItem())
					.addIngredient(TFBlocks.FALLEN_LEAVES.get().asItem())
					.addIngredient(TFItems.LIVEROOT.get())
					.addIngredient(TFBlocks.TORCHBERRY_PLANT.get().asItem())
					.addIngredient(Items.VINE)
					.addIngredient(TFBlocks.FIDDLEHEAD.get().asItem())
					.build(pvd, getID(DelightFood.THOUSAND_PLANT_STEW.item.getId()));
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
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							DelightFood.RAW_MEEF_SLICE.item.get(), 2)
					.build(pvd, getID(DelightFood.RAW_MEEF_SLICE.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.COOKED_MEEF.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							DelightFood.COOKED_MEEF_SLICE.item.get(), 2)
					.build(pvd, getID(DelightFood.COOKED_MEEF_SLICE.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.RAW_VENISON.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							DelightFood.RAW_VENISON_RIB.item.get(), 2)
					.build(pvd, getID(DelightFood.RAW_VENISON_RIB.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.COOKED_VENISON.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							DelightFood.COOKED_VENISON_RIB.item.get(), 2)
					.build(pvd, getID(DelightFood.COOKED_VENISON_RIB.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.HYDRA_CHOP.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							DelightFood.HYDRA_PIECE.item.get(), 2)
					.build(pvd, getID(DelightFood.HYDRA_PIECE.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TagGen.INSECT),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							DelightFood.RAW_INSECT.item.get(), 2)
					.build(pvd, getID(DelightFood.RAW_INSECT.item.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TDBlocks.MUSHGLOOM_COLONY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							TFBlocks.MUSHGLOOM.get(), 5)
					.build(pvd, getID(TDBlocks.MUSHGLOOM_COLONY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.UR_GHAST_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							DelightFood.EXPERIMENT_113.item.get(), 9)
					.addResult(TFItems.EXPERIMENT_115.get(), 4)
					.addResultWithChance(DelightFood.EXPERIMENT_110.item.get(), 0.1f)
					.build(pvd, getID(TFBlocks.UR_GHAST_TROPHY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.HYDRA_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							TFItems.HYDRA_CHOP.get(), 4)
					.build(pvd, getID(TFBlocks.HYDRA_TROPHY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.NAGA_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							TFItems.NAGA_SCALE.get(), 9)
					.build(pvd, getID(TFBlocks.NAGA_TROPHY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.KNIGHT_PHANTOM_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							TFItems.PHANTOM_HELMET.get(), 1)
					.build(pvd, getID(TFBlocks.KNIGHT_PHANTOM_TROPHY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.LICH_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							Items.SKELETON_SKULL, 1)
					.addResultWithChance(TFItems.ZOMBIE_SCEPTER.get(), 0.2f)
					.addResultWithChance(TFItems.LIFEDRAIN_SCEPTER.get(), 0.2f)
					.addResultWithChance(TFItems.TWILIGHT_SCEPTER.get(), 0.2f)
					.build(pvd, getID(TFBlocks.LICH_TROPHY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.MINOSHROOM_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							TFItems.RAW_MEEF.get(), 9)
					.addResultWithChance(Items.RED_MUSHROOM, 0.5f, 8)
					.build(pvd, getID(TFBlocks.MINOSHROOM_TROPHY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.ALPHA_YETI_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							TFItems.ALPHA_YETI_FUR.get(), 9)
					.addResultWithChance(TFItems.ICE_BOMB.get(), 0.5f, 4)
					.build(pvd, getID(TFBlocks.ALPHA_YETI_TROPHY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.PHANTOM_HELMET.get()),
							Ingredient.of(Tags.Items.TOOLS_PICKAXES),
							TFItems.ARMOR_SHARD_CLUSTER.get(), 3)
					.build(pvd, getID(TFItems.PHANTOM_HELMET.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFItems.PHANTOM_CHESTPLATE.get()),
							Ingredient.of(Tags.Items.TOOLS_PICKAXES),
							TFItems.ARMOR_SHARD_CLUSTER.get(), 5)
					.build(pvd, getID(TFItems.PHANTOM_CHESTPLATE.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.SNOW_QUEEN_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							TFItems.ICE_BOMB.get(), 9)
					.addResultWithChance(TFItems.ICE_SWORD.get(), 0.2f)
					.addResultWithChance(TFItems.GLASS_SWORD.get(), 0.1f)
					.addResultWithChance(TFItems.ICE_BOW.get(), 0.2f)
					.build(pvd, getID(TFBlocks.SNOW_QUEEN_TROPHY.getId()));

			CuttingBoardRecipeBuilder.cuttingRecipe(
							Ingredient.of(TFBlocks.QUEST_RAM_TROPHY.get()),
							Ingredient.of(ForgeTags.TOOLS_KNIVES),
							Items.MUTTON, 9)
					.build(pvd, getID(TFBlocks.QUEST_RAM_TROPHY.getId()));

		}

		// freezing
		{
			unlock(pvd, new SimpleFrozenRecipeBuilder(Ingredient.of(Items.BLUE_ICE), TFItems.ICE_BOMB.get())::unlockedBy,
					Items.BLUE_ICE).save(pvd, new ResourceLocation(getID(TFItems.ICE_BOMB.getId())));

			unlock(pvd, new SimpleFrozenRecipeBuilder(Ingredient.of(Items.MAGMA_BLOCK), Items.OBSIDIAN)::unlockedBy,
					Items.MAGMA_BLOCK).save(pvd, new ResourceLocation(TwilightDelight.MODID, "obsidian"));

			unlock(pvd, new SimpleFrozenRecipeBuilder(Ingredient.of(Items.ICE), Items.PACKED_ICE)::unlockedBy,
					Items.ICE).save(pvd, new ResourceLocation(TwilightDelight.MODID, "packed_ice"));

			unlock(pvd, new SimpleFrozenRecipeBuilder(Ingredient.of(Items.DIAMOND_SWORD), TFItems.ICE_SWORD.get())::unlockedBy,
					Items.DIAMOND_SWORD).save(pvd, new ResourceLocation(getID(TFItems.ICE_SWORD.getId())));

			unlock(pvd, new SimpleFrozenRecipeBuilder(Ingredient.of(Items.BOW), TFItems.ICE_BOW.get())::unlockedBy,
					Items.BOW).save(pvd, new ResourceLocation(getID(TFItems.ICE_BOW.getId())));
		}

		// neapolitan
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			path = "neapolitan/";
			neapolitan(pvd, NeapolitanFood.AURORA_ICE_CREAM.item,
					NeapolitanFood.AURORA_MILKSHAKE.item,
					NeapolitanCakes.AURORA,
					TFBlocks.AURORA_BLOCK.get().asItem());
			neapolitan(pvd, NeapolitanFood.GLACIER_ICE_CREAM.item,
					NeapolitanFood.GLACIER_MILKSHAKE.item,
					NeapolitanCakes.GLACIER,
					TFItems.ICE_BOMB.get());
			neapolitan(pvd, NeapolitanFood.PHYTOCHEMICAL_ICE_CREAM.item,
					NeapolitanFood.PHYTOCHEMICAL_MILKSHAKE.item,
					NeapolitanCakes.PHYTOCHEMICAL,
					TFItems.STEELEAF_INGOT.get());
			neapolitan(pvd, NeapolitanFood.TORCHBERRY_ICE_CREAM.item,
					NeapolitanFood.TORCHBERRY_MILKSHAKE.item,
					NeapolitanCakes.TORCHBERRY,
					TFItems.TORCHBERRIES.get());

			unlock(pvd, new ShapelessRecipeBuilder(NeapolitanFood.TWILIGHT_ICE_CREAM.item.get(), 3)::unlockedBy, TFItems.TORCHBERRIES.get())
					.requires(NeapolitanFood.TORCHBERRY_ICE_CREAM.item.get())
					.requires(NeapolitanItems.CHOCOLATE_ICE_CREAM.get())
					.requires(NeapolitanItems.STRAWBERRY_ICE_CREAM.get())
					.requires(Items.BOWL, 3).save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(NeapolitanFood.RAINBOW_ICE_CREAM.item.get(), 3)::unlockedBy, TFBlocks.AURORA_BLOCK.get().asItem())
					.requires(NeapolitanFood.AURORA_ICE_CREAM.item.get())
					.requires(NeapolitanItems.BANANA_ICE_CREAM.get())
					.requires(NeapolitanItems.ADZUKI_ICE_CREAM.get())
					.requires(Items.BOWL, 3).save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(NeapolitanFood.REFRESHING_ICE_CREAM.item.get(), 3)::unlockedBy, TFItems.ICE_BOMB.get())
					.requires(NeapolitanFood.GLACIER_ICE_CREAM.item.get())
					.requires(NeapolitanItems.MINT_ICE_CREAM.get())
					.requires(NeapolitanFood.PHYTOCHEMICAL_ICE_CREAM.item.get())
					.requires(Items.BOWL, 3).save(pvd);

		}
	}

	private static void neapolitan(RegistrateRecipeProvider pvd, ItemEntry<?> ice_cream, ItemEntry<?> milkshake, NeapolitanCakes cake, Item ingredient) {
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

		unlock(pvd, new ShapedRecipeBuilder(cake.block.get().asItem(), 1)::unlockedBy, ingredient)
				.pattern("MXM").pattern("SES").pattern("WXW")
				.define('M', milk).define('S', Items.SUGAR)
				.define('W', Items.WHEAT).define('E', Items.EGG)
				.define('X', ingredient)
				.save(ConditionalRecipeWrapper.mod(pvd, Neapolitan.MOD_ID), getID(cake.block.getId()));

		CuttingBoardRecipeBuilder.cuttingRecipe(
						Ingredient.of(cake.block.get()),
						Ingredient.of(ForgeTags.TOOLS_KNIVES),
						cake.item.get(), 7)
				.build(pvd, getID(cake.item.getId()));

		unlock(pvd, new ShapelessRecipeBuilder(cake.block.get(), 1)::unlockedBy, cake.item.get())
				.requires(cake.item.get(), 7)
				.save(ConditionalRecipeWrapper.mod(pvd, Neapolitan.MOD_ID), getID(cake.block.getId()) + "_assemble");
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
