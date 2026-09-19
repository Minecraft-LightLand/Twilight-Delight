package dev.xkmc.twilightdelight.compat;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

/**
 * Custom condition that passes when the given item is NOT in the item registry.
 * Per-item granularity (instead of per-mod) so compat follows content:
 * if the other mod renames, removes, or gates the item behind config,
 * our recipes track the item itself.
 * Registered as {@code twilightdelight:item_absent}.
 */
public record ItemAbsentCondition(ResourceLocation item) implements ICondition {

	public static final MapCodec<ItemAbsentCondition> CODEC = RecordCodecBuilder.mapCodec(
			inst -> inst.group(
					ResourceLocation.CODEC.fieldOf("item").forGetter(ItemAbsentCondition::item)
			).apply(inst, ItemAbsentCondition::new));

	@Override
	public boolean test(IContext context) {
		return !BuiltInRegistries.ITEM.containsKey(item);
	}

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}

	@Override
	public String toString() {
		return "item_absent(\"" + item + "\")";
	}

}
