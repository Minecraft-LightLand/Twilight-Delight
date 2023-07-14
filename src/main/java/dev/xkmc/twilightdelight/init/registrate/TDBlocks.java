package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.twilightdelight.content.block.*;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.world.IronwoodTreeGrower;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;
import twilightforest.TwilightForestMod;
import twilightforest.block.DarkLeavesBlock;
import twilightforest.block.TFLogBlock;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Function;

public class TDBlocks {

	public static final RegistryEntry<CreativeModeTab> TAB =
			TwilightDelight.REGISTRATE.buildModCreativeTab("twilight_delight", "Twilight's Flavors & Delight",
					e -> e.icon(TDBlocks.MAZE_STOVE::asStack));

	public static final BlockEntry<MazeStoveBlock> MAZE_STOVE;
	public static final BlockEntry<FieryCookingPotBlock> FIERY_POT;

	public static final BlockEntry<FierySnakesBlock> FIERY_SNAKES;
	public static final BlockEntry<LilyChickenBlock> LILY_CHICKEN;
	public static final BlockEntry<MeefWellingtonBlock> MEEF_WELLINGTON;

	public static final BlockEntry<Block> TORCHBERRIES_CRATE;
	public static final BlockEntry<MushroomColonyBlock> MUSHGLOOM_COLONY;

	public static final BlockEntry<SaplingBlock> IRON_SAPLING;
	public static final BlockEntry<TFLogBlock> IRON_LOGS;
	public static final BlockEntry<DarkLeavesBlock> IRON_LEAVES;

	static {
		// utensils
		{
			MAZE_STOVE = TwilightDelight.REGISTRATE.block(
							"maze_stove", p -> new MazeStoveBlock(
									BlockBehaviour.Properties.copy(Blocks.BRICKS)
											.lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0)))
					.blockstate((ctx, pvd) -> {
						ModelFile on = pvd.models().cube(
								ctx.getName() + "_on",
								pvd.modLoc("block/" + ctx.getName() + "_bottom"),
								pvd.modLoc("block/" + ctx.getName() + "_top_on"),
								pvd.modLoc("block/" + ctx.getName() + "_front_on"),
								pvd.modLoc("block/" + ctx.getName() + "_side"),
								pvd.modLoc("block/" + ctx.getName() + "_side"),
								pvd.modLoc("block/" + ctx.getName() + "_side")
						).texture("particle", pvd.modLoc("block/" + ctx.getName() + "_bottom"));

						ModelFile off = pvd.models().cube(
								ctx.getName(),
								pvd.modLoc("block/" + ctx.getName() + "_bottom"),
								pvd.modLoc("block/" + ctx.getName() + "_top"),
								pvd.modLoc("block/" + ctx.getName() + "_front"),
								pvd.modLoc("block/" + ctx.getName() + "_side"),
								pvd.modLoc("block/" + ctx.getName() + "_side"),
								pvd.modLoc("block/" + ctx.getName() + "_side")
						).texture("particle", pvd.modLoc("block/" + ctx.getName() + "_bottom"));
						pvd.horizontalBlock(ctx.get(), state -> state.getValue(StoveBlock.LIT) ? on : off);
					})
					.tag(ModTags.HEAT_SOURCES, BlockTags.MINEABLE_WITH_PICKAXE).simpleItem().register();
			FIERY_POT = TwilightDelight.REGISTRATE.block(
							"fiery_cooking_pot", p -> new FieryCookingPotBlock(
									BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
											.strength(0.5F, 6.0F)
											.sound(SoundType.LANTERN)))
					.blockstate((ctx, pvd) -> {
						String asset = "block/hot_cooking_pot";
						ModelFile base = pvd.models().getBuilder(ctx.getName())
								.parent(new ModelFile.UncheckedModelFile(pvd.modLoc(asset)))
								.texture("top", asset + "_top")
								.texture("bottom", asset + "_bottom")
								.texture("side", asset + "_side")
								.texture("parts", asset + "_parts");
						ModelFile handle = pvd.models().getBuilder(ctx.getName() + "_handle")
								.parent(new ModelFile.UncheckedModelFile(pvd.modLoc(asset + "_handle")))
								.texture("top", asset + "_top")
								.texture("bottom", asset + "_bottom")
								.texture("side", asset + "_side")
								.texture("parts", asset + "_parts")
								.texture("handle", asset + "_handle");
						ModelFile tray = pvd.models().getBuilder(ctx.getName() + "_tray")
								.parent(new ModelFile.UncheckedModelFile(pvd.modLoc(asset + "_tray")))
								.texture("top", asset + "_top")
								.texture("bottom", asset + "_bottom")
								.texture("side", asset + "_side")
								.texture("parts", asset + "_parts");
						pvd.horizontalBlock(ctx.getEntry(), state ->
								switch (state.getValue(CookingPotBlock.SUPPORT)) {
									case NONE -> base;
									case HANDLE -> handle;
									case TRAY -> tray;
								});
					}).simpleItem().defaultLoot().defaultLang().register();
		}
		// food
		{
			FIERY_SNAKES = TwilightDelight.REGISTRATE.block(
							"fiery_snakes_block", p -> new FierySnakesBlock())
					.blockstate((ctx, pvd) -> pvd.horizontalBlock(ctx.get(), state -> {
						int serve = state.getValue(FeastBlock.SERVINGS);
						String suffix = serve == 4 ? "" : serve == 0 ? "_leftover" : ("_stage" + (4 - serve));
						return new ModelFile.UncheckedModelFile(pvd.modLoc("block/" + ctx.getName() + suffix));
					}))
					.item().model((ctx, pvd) -> pvd.generated(ctx)).build()
					.loot((pvd, block) -> pvd.add(block, LootTable.lootTable()
							.withPool(LootPool.lootPool().add(LootItem.lootTableItem(block.asItem())
									.when(ExplosionCondition.survivesExplosion())
									.when(getServe(block))))
							.withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BOWL))
									.when(ExplosionCondition.survivesExplosion())
									.when(InvertedLootItemCondition.invert(getServe(block))))
					)).register();
			LILY_CHICKEN = TwilightDelight.REGISTRATE.block(
							"lily_chicken_block", p -> new LilyChickenBlock())
					.blockstate((ctx, pvd) -> pvd.horizontalBlock(ctx.get(), state -> {
						int serve = state.getValue(FeastBlock.SERVINGS);
						String suffix = serve == 4 ? "" : serve == 0 ? "_leftover" : ("_stage" + (4 - serve));
						return new ModelFile.UncheckedModelFile(pvd.modLoc("block/" + ctx.getName() + suffix));
					}))
					.item().model((ctx, pvd) -> pvd.generated(ctx)).build()
					.loot((pvd, block) -> pvd.add(block, LootTable.lootTable()
							.withPool(LootPool.lootPool().add(LootItem.lootTableItem(block.asItem())
									.when(ExplosionCondition.survivesExplosion())
									.when(getServe(block))))
							.withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BOWL))
									.when(ExplosionCondition.survivesExplosion())
									.when(InvertedLootItemCondition.invert(getServe(block))))
							.withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BONE_MEAL))
									.when(ExplosionCondition.survivesExplosion())
									.when(InvertedLootItemCondition.invert(getServe(block))))
					))
					.register();
			MEEF_WELLINGTON = TwilightDelight.REGISTRATE.block(
							"meef_wellington_block", p -> new MeefWellingtonBlock())
					.blockstate((ctx, pvd) -> pvd.horizontalBlock(ctx.get(), state -> {
						int serve = state.getValue(FeastBlock.SERVINGS);
						String suffix = serve == 4 ? "" : serve == 0 ? "_leftover" : ("_stage" + (4 - serve));
						return new ModelFile.UncheckedModelFile(pvd.modLoc("block/" + ctx.getName() + suffix));
					}))
					.item().model((ctx, pvd) -> pvd.generated(ctx)).build()
					.loot((pvd, block) -> pvd.add(block, LootTable.lootTable()
							.withPool(LootPool.lootPool().add(LootItem.lootTableItem(block.asItem())
									.when(ExplosionCondition.survivesExplosion())
									.when(getServe(block))))
							.withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.BOWL))
									.when(ExplosionCondition.survivesExplosion())
									.when(InvertedLootItemCondition.invert(getServe(block))))
					)).register();
		}
		// misc
		{
			TORCHBERRIES_CRATE = TwilightDelight.REGISTRATE.block(
							"torchberries_crate", p -> new Block(BlockBehaviour.Properties
									.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F)
									.sound(SoundType.WOOD).lightLevel(state -> 15)))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models().cubeBottomTop(
							ctx.getName(),
							pvd.modLoc("block/" + ctx.getName() + "_side"),
							pvd.modLoc("block/crate_bottom"),
							pvd.modLoc("block/" + ctx.getName() + "_top"))))
					.simpleItem().register();

			MUSHGLOOM_COLONY = TwilightDelight.REGISTRATE.block(
							"mushgloom_colony", p -> new MushroomColonyBlock(BlockBehaviour.Properties
									.copy(Blocks.BROWN_MUSHROOM).instabreak().sound(SoundType.FUNGUS)
									.noCollission().noOcclusion().lightLevel((state) -> 3).randomTicks(),
									() -> TFBlocks.MUSHGLOOM.get().asItem()))
					.blockstate((ctx, pvd) -> pvd.getVariantBuilder(ctx.get()).forAllStates((state) -> {
						String stageName = ctx.getName() + "_stage" + state.getValue(MushroomColonyBlock.COLONY_AGE);
						return ConfiguredModel.builder().modelFile(pvd.models()
								.getBuilder(stageName).parent(new ModelFile.UncheckedModelFile(
										new ResourceLocation(TwilightForestMod.ID, "block/mushgloom")))
								.texture("cross", pvd.modLoc("block/" + stageName))
								.texture("cross2", pvd.modLoc("block/" + stageName + "_head"))).build();
					}))
					.tag(ModTags.COMPOST_ACTIVATORS, ModTags.UNAFFECTED_BY_RICH_SOIL)
					.loot((pvd, block) -> {
						var item = TFBlocks.MUSHGLOOM.get().asItem();
						Function<Integer, LootItemCondition.Builder> prop = i ->
								LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(MushroomColonyBlock.COLONY_AGE, i));
						var s0 = LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)))
								.when(prop.apply(0));
						var s1 = LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(3)))
								.when(prop.apply(1));
						var s2 = LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)))
								.when(prop.apply(2));
						var s3 = LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(5)))
								.when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS)).invert())
								.when(prop.apply(3));
						var self = LootItem.lootTableItem(block.asItem())
								.when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS)))
								.when(prop.apply(3));
						pvd.add(block, LootTable.lootTable().withPool(LootPool.lootPool().add(
										AlternativesEntry.alternatives(s0, s1, s2, s3, self))
								.apply(ApplyExplosionDecay.explosionDecay())));
					})
					.item(MushroomColonyItem::new)
					.model((ctx, pvd) -> pvd.generated(ctx,
							pvd.modLoc("block/" + ctx.getName() + "_stage3"),
							pvd.modLoc("block/" + ctx.getName() + "_stage3_head"))).build()
					.register();
		}
		// tree
		{
			IRON_SAPLING = TwilightDelight.REGISTRATE.block(
							"ironwood_sapling", p -> new SaplingBlock(new IronwoodTreeGrower(),
									BlockBehaviour.Properties.of().mapColor(MapColor.PLANT)
											.pushReaction(PushReaction.DESTROY).instabreak()
											.sound(SoundType.GRASS).noCollission().randomTicks()
							))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models()
							.cross(ctx.getName(), pvd.modLoc("block/" + ctx.getName()))
							.renderType("cutout")))
					.tag(BlockTags.SAPLINGS)
					.item().model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("block/" + ctx.getName())))
					.tag(ItemTags.SAPLINGS).removeTab(TAB.getKey()).build()//TODO
					.register();
			IRON_LOGS = TwilightDelight.REGISTRATE.block(
							"ironwood_log", p -> new TFLogBlock(
									BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD)
											.strength(10, 10).requiresCorrectToolForDrops()))
					.blockstate((ctx, pvd) -> pvd.logBlock(ctx.get()))
					.tag(BlockTags.LOGS, BlockTags.LOGS_THAT_BURN, BlockTags.MINEABLE_WITH_AXE, BlockTags.NEEDS_DIAMOND_TOOL)
					.item().tag(ItemTags.LOGS, ItemTags.LOGS_THAT_BURN).removeTab(TAB.getKey()).build()//TODO
					.loot((pvd, block) -> pvd.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
							.add(AlternativesEntry.alternatives(
									LootItem.lootTableItem(block.asItem())
											.when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(
													new EnchantmentPredicate(Enchantments.SILK_TOUCH,
															MinMaxBounds.Ints.atLeast(1))))),
									LootItem.lootTableItem(TFItems.IRONWOOD_INGOT.get())
											.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
							)).when(ExplosionCondition.survivesExplosion())
					))).register();

			IRON_LEAVES = TwilightDelight.REGISTRATE.block(
							"ironwood_leaves", p -> new DarkLeavesBlock(
									BlockBehaviour.Properties.of().mapColor(MapColor.PLANT)
											.pushReaction(PushReaction.DESTROY)
											.sound(SoundType.AZALEA_LEAVES)
											.isSuffocating((state, getter, pos) -> false)
											.isViewBlocking((state, getter, pos) -> false)
											.strength(10, 10).requiresCorrectToolForDrops()))
					.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models().withExistingParent(ctx.getName(), "block/leaves")
							.texture("all", pvd.modLoc("block/" + ctx.getName()))))
					.tag(BlockTags.LEAVES, BlockTags.MINEABLE_WITH_HOE, BlockTags.NEEDS_DIAMOND_TOOL)
					.item().tag(ItemTags.LEAVES).removeTab(TAB.getKey()).build()//TODO
					.loot((pvd, block) -> pvd.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
							.add(AlternativesEntry.alternatives(
									LootItem.lootTableItem(block.asItem())
											.when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(
													new EnchantmentPredicate(Enchantments.SILK_TOUCH,
															MinMaxBounds.Ints.atLeast(1))))),
									LootItem.lootTableItem(TDBlocks.IRON_SAPLING.get())
											.when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE,
													0.001f, 0.002f, 0.003f, 0.004f)),
									LootItem.lootTableItem(TFItems.STEELEAF_INGOT.get())
											.when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE,
													0.04f, 0.05f, 0.06f, 0.07f))
							)).when(ExplosionCondition.survivesExplosion())
					)))
					.register();
		}
	}

	public static void register() {

	}

	private static <T extends FeastBlock> LootItemBlockStatePropertyCondition.Builder getServe(T block) {
		return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).
				setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(block.getServingsProperty(), block.getMaxServings()));
	}

}
