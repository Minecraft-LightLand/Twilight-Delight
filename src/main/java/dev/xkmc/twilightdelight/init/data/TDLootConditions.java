package dev.xkmc.twilightdelight.init.data;

import com.mojang.serialization.Codec;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Table-level {@code neoforge:conditions} for compat loot tables.
 * <p>
 * Loot tables are parsed and validated eagerly on every server start and every {@code /reload},
 * referenced or not, so a table shipped for content that only exists when an optional mod is
 * present makes the whole table fail to register with an unknown registry key error.
 * <p>
 * A pool-level {@code LootItemCondition} cannot help here: the table body (including the
 * unresolvable item) is decoded before any condition is consulted. Only the table-level
 * {@code ICondition} form is tested first, so gated tables get a {@code neoforge:mod_loaded}
 * condition written into them. See {@code mixin/LootTableProviderMixin} for the write path.
 */
public class TDLootConditions {

	/**
	 * MUST be createConditionalCodecWithConditions, NOT createConditionalCodec.
	 * The plain variant's encoder re-wraps with List.of(), so the conditions are silently
	 * dropped on write and the output looks unchanged — a very quiet failure.
	 */
	public static final Codec<Optional<WithConditions<LootTable>>> CODEC =
			ConditionalOps.createConditionalCodecWithConditions(LootTable.DIRECT_CODEC);

	private static final Map<ResourceLocation, List<ICondition>> CONDITIONS = new LinkedHashMap<>();

	/**
	 * Gate the block loot tables of compat blocks on the mod that provides them.
	 *
	 * @param modid      the optional mod id
	 * @param blockNames block names as registered, e.g. {@code aurora_cake}, {@code black_aurora_candle_cake}
	 */
	public static void requireMods(String modid, String... blockNames) {
		List<ICondition> conditions = List.of(new ModLoadedCondition(modid));
		for (var name : blockNames)
			CONDITIONS.put(TwilightDelight.loc("blocks/" + name), conditions);
	}

	@Nullable
	public static List<ICondition> get(ResourceLocation id) {
		return CONDITIONS.get(id);
	}

}