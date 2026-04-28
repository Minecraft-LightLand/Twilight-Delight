package dev.xkmc.twilightdelight.init.registrate.neapolitan;

import com.teamabnormals.neapolitan.core.registry.NeapolitanMobEffects;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.init.data.TagRef;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;

import java.util.List;

public enum NeapolitanFood {
	AURORA_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f, List.of(
			new EffectSupplier(TDEffects.AURORA_GLOWING, 1800, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 1800, 2, 1),
			new EffectSupplier(() -> MobEffects.JUMP, 1800, 1, 1)),
			TagRef.SWEETS, TagRef.SUGARS),
	AURORA_MILKSHAKE(NeapolitanFoodType.MILKSHAKE, 3, 0.6f, List.of(
			new EffectSupplier(TDEffects.AURORA_GLOWING, 600, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 600, 2, 1),
			new EffectSupplier(() -> MobEffects.JUMP, 600, 1, 1)),
			TagRef.SWEETS, TagRef.SUGARS),

	TORCHBERRY_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 1800, 0, 1),
			TagRef.SWEETS, TagRef.SUGARS),
	TORCHBERRY_MILKSHAKE(NeapolitanFoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 600, 0, 1),
			TagRef.SWEETS, TagRef.SUGARS),

	PHYTOCHEMICAL_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.POISON_RANGE, 1800, 0, 1),
			TagRef.SWEETS, TagRef.SUGARS),
	PHYTOCHEMICAL_MILKSHAKE(NeapolitanFoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.POISON_RANGE, 600, 0, 1),
			TagRef.SWEETS, TagRef.SUGARS),

	GLACIER_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 1800, 0, 1),
			TagRef.SWEETS, TagRef.SUGARS),
	GLACIER_MILKSHAKE(NeapolitanFoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 600, 0, 1),
			TagRef.SWEETS, TagRef.SUGARS),

	TWILIGHT_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f, List.of(
			new EffectSupplier(TDEffects.FIRE_RANGE, 600, 0, 1),
			new EffectSupplier(NeapolitanMobEffects.SUGAR_RUSH, 900, 2, 1),
			new EffectSupplier(() -> MobEffects.HEAL, 1, 0, 1)),
			TagRef.SWEETS, TagRef.SUGARS),

	RAINBOW_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f, List.of(
			new EffectSupplier(TDEffects.AURORA_GLOWING, 600, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 600, 2, 1),
			new EffectSupplier(() -> MobEffects.JUMP, 600, 1, 1),
			new EffectSupplier(NeapolitanMobEffects.HARMONY, 600, 0, 1),
			new EffectSupplier(NeapolitanMobEffects.AGILITY, 600, 0, 1)),
			TagRef.SWEETS, TagRef.SUGARS),

	REFRESHING_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f, List.of(
			new EffectSupplier(TDEffects.FROZEN_RANGE, 600, 0, 1),
			new EffectSupplier(TDEffects.POISON_RANGE, 600, 0, 1),
			new EffectSupplier(NeapolitanMobEffects.BERSERKING, 600, 0, 1)),
			TagRef.SWEETS, TagRef.SUGARS),
	;

	public final ItemEntry<Item> item;

	@SafeVarargs
	NeapolitanFood(NeapolitanFoodType r, int nutrition, float saturation, TagKey<Item>... tags) {
		this(r, nutrition, saturation, List.of(), tags);
	}

	@SafeVarargs
	NeapolitanFood(NeapolitanFoodType r, int nutrition, float saturation, EffectSupplier effects, TagKey<Item>... tags) {
		this(r, nutrition, saturation, List.of(effects), tags);
	}

	@SafeVarargs
	NeapolitanFood(NeapolitanFoodType r, int nutrition, float saturation, List<EffectSupplier> effects, TagKey<Item>... tags) {
		item = TDItems.simpleFood(r, name(), nutrition, saturation, effects, tags);
	}

	public static void register() {
	}

}
