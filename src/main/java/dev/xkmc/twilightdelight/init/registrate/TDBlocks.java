package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.twilightdelight.content.block.FierySnakesBlock;
import dev.xkmc.twilightdelight.content.block.LilyChickenBlock;
import dev.xkmc.twilightdelight.content.block.MazeStoveBlock;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.Tags;
import twilightforest.init.TFBlocks;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
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
			}).tag(ModTags.HEAT_SOURCES, BlockTags.MINEABLE_WITH_PICKAXE).simpleItem().register();

	public static final BlockEntry<FierySnakesBlock> FIERY_SNAKES = TwilightDelight.REGISTRATE.block(
					"fiery_snakes_block", p -> new FierySnakesBlock())
			.blockstate((ctx, pvd) -> {
			}).item().model((ctx, pvd) -> pvd.generated(ctx)).build()
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
			.blockstate((ctx, pvd) -> {
			}).item().model((ctx, pvd) -> pvd.generated(ctx)).build()
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

	public static final BlockEntry<MushroomColonyBlock> MUSHGLOOM_COLONY = TwilightDelight.REGISTRATE.block(
					"mushgloom_colony", p -> new MushroomColonyBlock(BlockBehaviour.Properties.copy(
							TFBlocks.MUSHGLOOM.get()), () -> TFBlocks.MUSHGLOOM.get().asItem()))
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

	public static void register() {

	}

	private static <T extends FeastBlock> LootItemBlockStatePropertyCondition.Builder getServe(T block) {
		return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).
				setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(block.getServingsProperty(), block.getMaxServings()));
	}

}
