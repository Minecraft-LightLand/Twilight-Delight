package dev.xkmc.twilightdelight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import dev.xkmc.twilightdelight.init.data.TDLootConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mixin(LootTableProvider.class)
public abstract class LootTableProviderMixin {

	@WrapOperation(method = "lambda$run$4(Lnet/minecraft/data/CachedOutput;Lnet/minecraft/core/HolderLookup$Provider;Ljava/util/Map$Entry;)Ljava/util/concurrent/CompletableFuture;",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/data/DataProvider;saveStable(Lnet/minecraft/data/CachedOutput;Lnet/minecraft/core/HolderLookup$Provider;Lcom/mojang/serialization/Codec;Ljava/lang/Object;Ljava/nio/file/Path;)Ljava/util/concurrent/CompletableFuture;",
					remap = false),
			remap = false, require = 0)
	private <T> CompletableFuture<?> twilightdelight$writeLootTable(CachedOutput output, HolderLookup.Provider registries,
																	Codec<T> codec, T value, Path path, Operation<CompletableFuture<?>> original) {
		if (value instanceof LootTable table) {
			var id = ((TDLootTableAccessor) (Object) table).twilightdelight$getRandomSequence();
			if (id.isPresent()) {
				var conditions = TDLootConditions.get(id.get());
				if (conditions != null)
					return original.call(output, registries, TDLootConditions.CODEC,
							Optional.of(new WithConditions<>(conditions, table)), path);
			}
		}
		return original.call(output, registries, codec, value, path);
	}

}