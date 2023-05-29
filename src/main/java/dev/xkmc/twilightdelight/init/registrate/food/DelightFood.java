package dev.xkmc.twilightdelight.init.registrate.food;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.registry.ModEffects;

public enum DelightFood {
	HYDRA_PIECE(FoodType.UNCOMMON_MEAT, 9, 2.0F,
			new EffectSupplier(() -> MobEffects.REGENERATION, 20, 0, 1)),
	RAW_VENISON_RIB(FoodType.MEAT, 2, 0.25f),
	COOKED_VENISON_RIB(FoodType.MEAT, 4, 0.875f),
	RAW_MEEF_SLICE(FoodType.MEAT, 1, 0.7F),
	COOKED_MEEF_SLICE(FoodType.MEAT, 3, 0.6F),
	RAW_INSECT(FoodType.MEAT, 2, 0.2F),
	COOKED_INSECT(FoodType.MEAT, 6, 0.6F),
	TORCHBERRY_COOKIE(FoodType.COOKIE, 2, 0.2F),
	CHOCOLATE_WAFER(FoodType.NONE, 9, 0.6F),
	EXPERIMENT_113(FoodType.MEAT, 6, 0.2F,
			new EffectSupplier(() -> MobEffects.WEAKNESS, 100, 0, 0.33f)),
	EXPERIMENT_110(FoodType.MEAT, 12, 0.3F,
			new EffectSupplier(() -> MobEffects.CONFUSION, 1200, 0, 1),
			new EffectSupplier(() -> MobEffects.HEALTH_BOOST, 2400, 4, 1),
			new EffectSupplier(() -> MobEffects.NIGHT_VISION, 2400, 0, 1)),
	BERRY_STICK(FoodType.NONE, 4, 0.2f),

	GLOWSTEW(FoodType.BOWL, 7, 0.675F,
			new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
			new EffectSupplier(ModEffects.COMFORT, 1200, 0, 1)),
	GLOW_VENISON_RIB_WITH_PASTA(FoodType.BOWL_MEAT, 12, 0.7f,
			new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 3600, 0, 1)),
	FRIED_INSECT(FoodType.BOWL_MEAT, 10, 0.61f,
			new EffectSupplier(ModEffects.NOURISHMENT, 3600, 0, 1)),
	THOUSAND_PLANT_STEW(FoodType.BOWL, 10, 0.61f,
			new EffectSupplier(() -> MobEffects.HEALTH_BOOST, 600, 1, 1),
			new EffectSupplier(() -> MobEffects.CONFUSION, 300, 0, 0.1f)),
	GRILLED_GHAST(FoodType.BOWL_MEAT, 10, 0.72f,
			new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1)),
	PLATE_OF_LILY_CHICKEN(FoodType.BOWL_MEAT, 16, 0.875f,
			new EffectSupplier(() -> MobEffects.FIRE_RESISTANCE, 6000, 0, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1)),
	PLATE_OF_FIERY_SNAKES(FoodType.BOWL_MEAT, 20, 1.9f,
			new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1),
			new EffectSupplier(ModEffects.COMFORT, 6000, 0, 1),
			new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 6000, 1, 1),
			new EffectSupplier(() -> MobEffects.REGENERATION, 400, 1, 1)),

	THORN_ROSE_TEA(FoodType.ROSE, 4, 0.25f,
			new EffectSupplier(() -> MobEffects.REGENERATION, 100, 1, 1)),
	TORCHBERRY_JUICE(FoodType.DRINK, 4, 0.25f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 3600, 0, 1)),
	PHYTOCHEMICAL_JUICE(FoodType.DRINK, 4, 0.25f,
			new EffectSupplier(TDEffects.POISON_RANGE, 3600, 0, 1)),
	GLACIER_ICE_TEA(FoodType.DRINK, 4, 0.25f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 3600, 0, 1)),
	TWILIGHT_SPRING(FoodType.DRINK, 0, 0,
			new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 600, 1, 1)),
	TEAR_DRINK(FoodType.DRINK, 1, 0,
			new EffectSupplier(() -> MobEffects.FIRE_RESISTANCE, 12000, 0, 1),
			new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 1200, 0, 1)),
	;

	public final ItemEntry<Item> item;

	DelightFood(FoodType r, int nutrition, float saturation, EffectSupplier... effects) {
		item = TDItems.simpleFood(r, name(), nutrition, saturation, effects);
	}

	public static void register() {
	}

}
