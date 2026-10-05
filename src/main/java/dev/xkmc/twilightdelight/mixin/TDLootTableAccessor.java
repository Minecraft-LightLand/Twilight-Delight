package dev.xkmc.twilightdelight.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

/**
 * {@code LootTable.randomSequence} is private with no getter, so an accessor is needed to recover
 * WHICH table is being written. {@code LootTableProvider.sequenceIdForLootTable} sets that field to
 * the table's own id, which is exactly what makes the lookup in {@link LootTableProviderMixin} possible.
 */
@Mixin(LootTable.class)
public interface TDLootTableAccessor {

	@Accessor("randomSequence")
	Optional<ResourceLocation> twilightdelight$getRandomSequence();

}