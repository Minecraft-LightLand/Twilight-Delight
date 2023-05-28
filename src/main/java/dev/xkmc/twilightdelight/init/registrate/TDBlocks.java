package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.twilightdelight.content.block.FierySnakesBlock;
import dev.xkmc.twilightdelight.content.block.LilyChickenBlock;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

public class TDBlocks {

	public static BlockEntry<FierySnakesBlock> FIERY_SNAKES = TwilightDelight.REGISTRATE.block(
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

	public static BlockEntry<LilyChickenBlock> LILY_CHICKEN = TwilightDelight.REGISTRATE.block(
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

	public static BlockEntry<StoveBlock> MAZE_STOVE = TwilightDelight.REGISTRATE.block(
					"maze_stove", p -> new StoveBlock(
							BlockBehaviour.Properties.copy(Blocks.BRICKS)
									.lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0)))
			.blockstate((ctx, pvd) -> {
			}).tag(ModTags.HEAT_SOURCES, BlockTags.MINEABLE_WITH_PICKAXE).simpleItem().register();

	public static void register() {

	}

	private static <T extends FeastBlock> LootItemBlockStatePropertyCondition.Builder getServe(T block) {
		return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).
				setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(block.getServingsProperty(), block.getMaxServings()));
	}

}
