package dev.xkmc.twilightdelight.init.registrate.food;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;

public enum NeapolitanFood {
	AURORA_ICE_CREAM(FoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 1200, 2, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 200, 1, 1)),
	AURORA_MILKSHAKE(FoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 600, 2, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 300, 0, 1)),

	TORCHBERRY_ICE_CREAM(FoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 6000, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 200, 2, 1)),
	TORCHBERRY_MILKSHAKE(FoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 3600, 0, 1)),

	PHYTOCHEMICAL_ICE_CREAM(FoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.POISON_RANGE, 6000, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 200, 2, 1)),
	PHYTOCHEMICAL_MILKSHAKE(FoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.POISON_RANGE, 3600, 0, 1)),

	GLACIER_ICE_CREAM(FoodType.ICE_CREAM, 6, 0.4f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 6000, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 200, 2, 1)),
	GLACIER_MILKSHAKE(FoodType.MILKSHAKE, 3, 0.6f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 3600, 0, 1)),

	;

	public final ItemEntry<Item> item;

	NeapolitanFood(FoodType r, int nutrition, float saturation, EffectSupplier... effects) {
		item = TDItems.simpleFood(r, name(), nutrition, saturation, effects);
	}

	public static void register() {
	}

}
