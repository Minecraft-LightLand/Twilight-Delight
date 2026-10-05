package dev.xkmc.twilightdelight.mixin;

import com.mojang.serialization.Codec;
import dev.xkmc.twilightdelight.init.data.TDLootConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Writes table-level {@code neoforge:conditions} for the loot tables registered in
 * {@link dev.xkmc.twilightdelight.init.data.TDLootConditions}. Registrate's loot API only exposes a
 * {@code LootTable.Builder} with nowhere to attach such a condition, and
 * {@code LootTableProvider.run} saves through {@code LootTable.DIRECT_CODEC}, so the codec swap has
 * to happen here.
 */
@Mixin(LootTableProvider.class)
public abstract class LootTableProviderMixin {

	// Method target is lambda$run$4, NOT run(...). The saveStable call sits inside the stream lambda,
	// which javac compiles out to a synthetic method. Confirm the real name with:
	//   javap -p -c build/neoForm/neoFormJoined1.21.1-*/steps/recompile/classes/net/minecraft/data/loot/LootTableProvider.class
	@Redirect(method = "lambda$run$4", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/data/DataProvider;saveStable(Lnet/minecraft/data/CachedOutput;Lnet/minecraft/core/HolderLookup$Provider;Lcom/mojang/serialization/Codec;Ljava/lang/Object;Ljava/nio/file/Path;)Ljava/util/concurrent/CompletableFuture;",
			remap = false), remap = false)
	private <T> CompletableFuture<?> twilightdelight$writeLootTable(CachedOutput output, HolderLookup.Provider registries,
																	Codec<T> codec, T value, Path path) {
		if (value instanceof LootTable table) {
			var id = ((TDLootTableAccessor) (Object) table).twilightdelight$getRandomSequence();
			if (id.isPresent()) {
				var conditions = TDLootConditions.get(id.get());
				if (conditions != null)
					return DataProvider.saveStable(output, registries, TDLootConditions.CODEC,
							Optional.of(new WithConditions<>(conditions, table)), path);
			}
		}
		return DataProvider.saveStable(output, registries, codec, value, path);
	}

}