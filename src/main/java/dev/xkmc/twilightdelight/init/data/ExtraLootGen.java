package dev.xkmc.twilightdelight.init.data;

import com.tterrag.registrate.providers.loot.RegistrateLootTableProvider;
import dev.xkmc.l2core.serial.loot.LootHelper;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.registry.ModItems;

public class ExtraLootGen {

	public static final ResourceKey<LootTable> SCRAP_115 = ResourceKey.create(Registries.LOOT_TABLE, TwilightDelight.loc("scrap_115"));

	public static void genLoot(RegistrateLootTableProvider pvd) {
		LootHelper helper = new LootHelper(pvd.getProvider());
		pvd.addLootAction(LootContextParamSets.BLOCK, e -> e.accept(SCRAP_115,
				LootTable.lootTable().withPool(LootPool.lootPool()
						.add(AlternativesEntry.alternatives(
								LootItem.lootTableItem(TFItems.EXPERIMENT_115.get())
										.when(helper.silk()),
								LootItem.lootTableItem(ModItems.CAKE_SLICE.get()))))));

	}

}
