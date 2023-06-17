package dev.xkmc.twilightdelight.init.registrate.neapolitan;

import com.teamabnormals.neapolitan.core.registry.NeapolitanMobEffects;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;

public enum NeapolitanFood {
	AURORA_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.AURORA_GLOWING, 1800, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 1800, 2, 1),
			new EffectSupplier(() -> MobEffects.JUMP, 1800, 1, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, 2, 1)),
	AURORA_MILKSHAKE(NeapolitanFoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.AURORA_GLOWING, 600, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 600, 2, 1),
			new EffectSupplier(() -> MobEffects.JUMP, 600, 1, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, 2, 1)),

	TORCHBERRY_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 1800, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, 2, 1)),
	TORCHBERRY_MILKSHAKE(NeapolitanFoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 600, 0, 1)),

	PHYTOCHEMICAL_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.POISON_RANGE, 1800, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, 2, 1)),
	PHYTOCHEMICAL_MILKSHAKE(NeapolitanFoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.POISON_RANGE, 600, 0, 1)),

	GLACIER_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 1800, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, 2, 1)),
	GLACIER_MILKSHAKE(NeapolitanFoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 600, 0, 1)),

	TWILIGHT_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 600, 0, 1),
			new EffectSupplier(NeapolitanMobEffects.SUGAR_RUSH, 900, 2, 1),
			new EffectSupplier(() -> MobEffects.HEAL, 1, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, 2, 1)),

	RAINBOW_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.AURORA_GLOWING, 600, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 600, 2, 1),
			new EffectSupplier(() -> MobEffects.JUMP, 600, 1, 1),
			new EffectSupplier(NeapolitanMobEffects.HARMONY, 600, 0, 1),
			new EffectSupplier(NeapolitanMobEffects.AGILITY, 600, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, 2, 1)),

	REFRESHING_ICE_CREAM(NeapolitanFoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 600, 0, 1),
			new EffectSupplier(TDEffects.POISON_RANGE, 600, 0, 1),
			new EffectSupplier(NeapolitanMobEffects.BERSERKING, 600, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 100, 2, 1)),
	;

	public final ItemEntry<Item> item;

	NeapolitanFood(NeapolitanFoodType r, int nutrition, float saturation, EffectSupplier... effects) {
		item = TDItems.simpleFood(r, name(), nutrition, saturation, effects);
	}

	public static void register() {
	}

}
