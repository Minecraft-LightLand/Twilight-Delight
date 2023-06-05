package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.twilightdelight.content.block.FierySnakesBlock;
import dev.xkmc.twilightdelight.content.block.LilyChickenBlock;
import dev.xkmc.twilightdelight.content.block.MazeStoveBlock;
import dev.xkmc.twilightdelight.content.block.MeefWellingtonBlock;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.world.IronwoodTreeGrower;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
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
import twilightforest.block.DarkLeavesBlock;
import twilightforest.block.TFLogBlock;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Function;

public class TDBlocks {

	public static final TDTab TAB = new TDTab();

	static {
		TwilightDelight.REGISTRATE.creativeModeTab(() -> TAB);
	}

	public static final BlockEntry<MazeStoveBlock> MAZE_STOVE = TwilightDelight.REGISTRATE.block(
					"maze_stove", p -> new MazeStoveBlock(
							BlockBehaviour.Properties.copy(Blocks.BRICKS)
									.lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0)))
			.blockstate((ctx, pvd) -> {
				ModelFile on = pvd.models().cube(
						ctx.getName(),
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

	public static final BlockEntry<FierySnakesBlock> FIERY_SNAKES = TwilightDelight.REGISTRATE.block(
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

	public static final BlockEntry<LilyChickenBlock> LILY_CHICKEN = TwilightDelight.REGISTRATE.block(
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

	public static final BlockEntry<MeefWellingtonBlock> MEEF_WELLINGTON = TwilightDelight.REGISTRATE.block(
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

	public static final BlockEntry<MushroomColonyBlock> MUSHGLOOM_COLONY = TwilightDelight.REGISTRATE.block(
					"mushgloom_colony", p -> new MushroomColonyBlock(BlockBehaviour.Properties
							.of(Material.PLANT).instabreak().sound(SoundType.FUNGUS)
							.noCollission().noOcclusion().lightLevel((state) -> 3).randomTicks(),
							() -> TFBlocks.MUSHGLOOM.get().asItem()))
			.blockstate((ctx, pvd) -> pvd.getVariantBuilder(ctx.get()).forAllStates((state) -> {
				String stageName = ctx.getName() + "_stage" + state.getValue(MushroomColonyBlock.COLONY_AGE);
				return ConfiguredModel.builder().modelFile(pvd.models()
						.cross(stageName, pvd.modLoc("block/" + stageName))
						.renderType("cutout")).build();
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
			.model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("block/" + ctx.getName() + "_stage3"))).build()
			.register();

	public static final BlockEntry<SaplingBlock> IRON_SAPLING = TwilightDelight.REGISTRATE.block(
					"ironwood_sapling", p -> new SaplingBlock(new IronwoodTreeGrower(),
							BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noCollission().randomTicks()))
			.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models()
					.cross(ctx.getName(), pvd.modLoc("block/" + ctx.getName()))
					.renderType("cutout")))
			.tag(BlockTags.SAPLINGS)
			.item().model((ctx, pvd) -> pvd.generated(ctx, pvd.modLoc("block/" + ctx.getName())))
			.tag(ItemTags.SAPLINGS).build()
			.register();

	public static final BlockEntry<TFLogBlock> IRON_LOGS = TwilightDelight.REGISTRATE.block(
					"ironwood_log", p -> new TFLogBlock(
							BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).sound(SoundType.WOOD)
									.strength(10, 10).requiresCorrectToolForDrops()))
			.blockstate((ctx, pvd) -> pvd.logBlock(ctx.get()))
			.tag(BlockTags.LOGS, BlockTags.LOGS_THAT_BURN, BlockTags.MINEABLE_WITH_AXE, BlockTags.NEEDS_DIAMOND_TOOL)
			.item().tag(ItemTags.LOGS, ItemTags.LOGS_THAT_BURN).build()
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

	public static final BlockEntry<DarkLeavesBlock> IRON_LEAVES = TwilightDelight.REGISTRATE.block(
					"ironwood_leaves", p -> new DarkLeavesBlock(BlockBehaviour.Properties
							.of(Material.LEAVES).sound(SoundType.AZALEA_LEAVES)
							.isSuffocating((state, getter, pos) -> false)
							.isViewBlocking((state, getter, pos) -> false)
							.strength(10, 10).requiresCorrectToolForDrops()))
			.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models().withExistingParent(ctx.getName(), "block/leaves")
					.texture("all", pvd.modLoc("block/" + ctx.getName()))))
			.tag(BlockTags.LEAVES, BlockTags.MINEABLE_WITH_HOE, BlockTags.NEEDS_DIAMOND_TOOL)
			.item().tag(ItemTags.LEAVES).build()
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

	public static void register() {

	}

	private static <T extends FeastBlock> LootItemBlockStatePropertyCondition.Builder getServe(T block) {
		return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).
				setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(block.getServingsProperty(), block.getMaxServings()));
	}

}
