package dev.xkmc.twilightdelight.init.data;

import dev.xkmc.l2library.repack.registrate.providers.loot.RegistrateLootTableProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.registry.ModItems;

public class ExtraLootGen {

	public static final ResourceLocation SCRAP_115 = new ResourceLocation(TwilightDelight.MODID, "scrap_115");

	public static void genLoot(RegistrateLootTableProvider pvd) {
		pvd.addLootAction(LootContextParamSets.BLOCK, e -> e.accept(SCRAP_115,
				LootTable.lootTable().withPool(LootPool.lootPool()
						.add(AlternativesEntry.alternatives(
								LootItem.lootTableItem(TFItems.EXPERIMENT_115.get())
										.when(MatchTool.toolMatches(ItemPredicate.Builder.item()
												.hasEnchantment(new EnchantmentPredicate(
														Enchantments.SILK_TOUCH,
														MinMaxBounds.Ints.atLeast(1))))),
								LootItem.lootTableItem(ModItems.CAKE_SLICE.get()))))));
	}

}
