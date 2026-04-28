package dev.xkmc.twilightdelight.init.registrate.delight;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.init.data.TagRef;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.List;

public enum DelightFood {
	HYDRA_PIECE(DelightFoodType.HYDRA_PIECE, 9, 2.0F,
			new EffectSupplier(() -> MobEffects.REGENERATION, 20, 0, 1),
			TagRef.PROTEINS),
	RAW_VENISON_RIB(DelightFoodType.MEAT_PIECE, 2, 0.7f, TagRef.PROTEINS),
	COOKED_VENISON_RIB(DelightFoodType.MEAT_PIECE, 4, 0.8f, TagRef.PROTEINS),
	RAW_MEEF_SLICE(DelightFoodType.MEAT_PIECE, 1, 0.7F, TagRef.PROTEINS),
	COOKED_MEEF_SLICE(DelightFoodType.MEAT_PIECE, 3, 0.8F, TagRef.PROTEINS),
	RAW_TOMAHAWK_SMEAK(DelightFoodType.MEAT, 6, 0.7F, TagRef.PROTEINS),
	COOKED_TOMAHAWK_SMEAK(DelightFoodType.MEAT, 12, 0.8F, TagRef.PROTEINS),
	RAW_INSECT(DelightFoodType.MEAT, 2, 0.2F, TagRef.PROTEINS),
	COOKED_INSECT(DelightFoodType.MEAT, 6, 0.6F, TagRef.PROTEINS),
	TORCHBERRY_COOKIE(DelightFoodType.COOKIE, 2, 0.2F, List.of(
			new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
			new EffectSupplier(TDEffects.FIRE_RANGE, 200, 0, 0.3f)),
			TagRef.SWEETS, TagRef.SNACKS, TagRef.SUGARS),
	NAGA_CHIP(DelightFoodType.MEAT_PIECE, 2, 0.8F, List.of(
			new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 200, 2, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 200, 2, 1)),
			TagRef.SNACKS),
	CHOCOLATE_WAFER(DelightFoodType.NONE, 9, 0.6F,
			TagRef.SWEETS, TagRef.SNACKS, TagRef.SUGARS),
	EXPERIMENT_113(DelightFoodType.MEAT_STICK, 6, 1.2F,
			new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 100, 0, 0.33f),
			TagRef.PROTEINS),
	CHOCOLATE_113(DelightFoodType.MEAT_STICK, 6, 1.2F, List.of(
			new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 100, 0, 0.33f),
			new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 600, 4, 1)),
			TagRef.PROTEINS, TagRef.SUGARS),
	MILKY_113(DelightFoodType.MEAT_STICK, 6, 1.2F,
			new EffectSupplier(() -> MobEffects.HEAL, 1, 0, 1),
			TagRef.PROTEINS),
	GLOW_113(DelightFoodType.MEAT_STICK, 6, 1.2F, List.of(
			new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 100, 0, 0.33f),
			new EffectSupplier(() -> MobEffects.NIGHT_VISION, 6000, 0, 1),
			new EffectSupplier(() -> MobEffects.GLOWING, 6000, 0, 1)),
			TagRef.PROTEINS),
	HONEY_113(DelightFoodType.MEAT_STICK, 6, 1.2F, List.of(
			new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 100, 0, 0.33f),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 3600, 2, 1)),
			TagRef.PROTEINS, TagRef.SUGARS),
	EXPERIMENT_110(DelightFoodType.MEAT, 12, 0.3F, List.of(
			new EffectSupplier(() -> MobEffects.HEALTH_BOOST, 2400, 4, 1),
			new EffectSupplier(() -> MobEffects.NIGHT_VISION, 2400, 0, 1),
			new EffectSupplier(() -> MobEffects.CONFUSION, 2400, 0, 1),
			new EffectSupplier(() -> MobEffects.POISON, 2400, 0, 1),
			new EffectSupplier(() -> MobEffects.BLINDNESS, 2400, 0, 0.5f),
			new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 200, 0, 0.5f)),
			TagRef.PROTEINS),
	MEEF_WRAP(DelightFoodType.MEAT, 10, 0.8f, List.of(
			new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 1200, 0, 1),
			new EffectSupplier(() -> MobEffects.REGENERATION, 1200, 0, 1)),
			TagRef.PROTEINS),
	GHAST_BURGER(DelightFoodType.MEAT, 11, 0.8f,
			new EffectSupplier(() -> MobEffects.REGENERATION, 1200, 1, 1),
			TagRef.PROTEINS),
	HYDRA_BURGER(DelightFoodType.MEAT, 18, 0.8f, List.of(
			new EffectSupplier(() -> MobEffects.REGENERATION, 6000, 1, 1),
			new EffectSupplier(() -> MobEffects.FIRE_RESISTANCE, 6000, 0, 1)),
			TagRef.PROTEINS),
	BERRY_STICK(DelightFoodType.STICK, 6, 0.3f, List.of(
			new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
			new EffectSupplier(TDEffects.FIRE_RANGE, 200, 0, 1)),
			TagRef.FRUITS),

	GLOWSTEW(DelightFoodType.BOWL, 7, 0.7F, List.of(
			new EffectSupplier(() -> MobEffects.GLOWING, 3600, 0, 1),
			new EffectSupplier(() -> MobEffects.NIGHT_VISION, 3600, 0, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 1200, 0, 1)),
			TagRef.FRUITS),
	MUSHGLOOM_SAUCE(DelightFoodType.SAUCE, 5, 0.4f,
			new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
			TagRef.VEGETABLES),
	GLOW_VENISON_RIB_WITH_PASTA(DelightFoodType.BOWL_MEAT, 12, 0.8f, List.of(
			new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
			new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 1800, 1, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 3600, 0, 1)),
			TagRef.PROTEINS, TagRef.GRAINS, TagRef.VEGETABLES),
	MUSHGLOOM_MEEF_PASTA(DelightFoodType.BOWL_MEAT, 12, 0.8f, List.of(
			new EffectSupplier(() -> MobEffects.GLOWING, 100, 0, 1),
			new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 1200, 1, 1),
			new EffectSupplier(() -> MobEffects.REGENERATION, 1200, 1, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 3600, 0, 1)),
			TagRef.PROTEINS, TagRef.GRAINS, TagRef.VEGETABLES),
	LIVEROOT_PORK_FRIED_RICE(DelightFoodType.BOWL_MEAT, 12, 0.8f, List.of(
			new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 3600, 0, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 3600, 0, 1)),
			TagRef.PROTEINS, TagRef.GRAINS, TagRef.VEGETABLES),
	LIVEROOT_VENISON_NOODLE_SOUP(DelightFoodType.BOWL_MEAT, 10, 0.8f, List.of(
			new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 1200, 1, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 3600, 0, 1)),
			TagRef.PROTEINS, TagRef.GRAINS, TagRef.VEGETABLES),
	TORCHBERRY_VENISON_SANDWICH(DelightFoodType.MEAT, 10, 0.8f, List.of(
			new EffectSupplier(TDEffects.FIRE_RANGE, 3600, 0, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 3600, 0, 1)),
			TagRef.PROTEINS, TagRef.GRAINS, TagRef.FRUITS),
	FRIED_INSECT(DelightFoodType.BOWL_MEAT, 10, 0.8f,
			new EffectSupplier(ModEffects.NOURISHMENT, 3600, 0, 1),
			TagRef.PROTEINS),
	THOUSAND_PLANT_STEW(DelightFoodType.BOWL, 10, 0.6f, List.of(
			new EffectSupplier(() -> MobEffects.HEALTH_BOOST, 600, 1, 1),
			new EffectSupplier(() -> MobEffects.CONFUSION, 300, 0, 0.1f),
			new EffectSupplier(ModEffects.NOURISHMENT, 1200, 0, 1)),
			TagRef.VEGETABLES),
	GRILLED_GHAST(DelightFoodType.BOWL_MEAT, 10, 0.8f, List.of(
			new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1),
			new EffectSupplier(() -> MobEffects.REGENERATION, 6000, 1, 1),
			new EffectSupplier(() -> MobEffects.FIRE_RESISTANCE, 6000, 0, 1)),
			TagRef.PROTEINS),
	GRILLED_TOMAHAWK_SMEAK(DelightFoodType.BOWL_MEAT, 14, 0.8f, List.of(
			new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1),
			new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 6000, 1, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 6000, 1, 1)),
			TagRef.PROTEINS),
	BORER_TEAR_SOUP(DelightFoodType.BOWL, 6, 0.6f,
			new EffectSupplier(() -> MobEffects.SATURATION, 10, 0, 1),
			TagRef.VEGETABLES),
	GHAST_BRAIN_SALAD(DelightFoodType.BOWL, 6, 0.6f, List.of(
			new EffectSupplier(() -> MobEffects.HEALTH_BOOST, 2400, 4, 1),
			new EffectSupplier(() -> MobEffects.NIGHT_VISION, 2400, 0, 1),
			new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 2400, 4, 1),
			new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 2400, 2, 1),
			new EffectSupplier(() -> MobEffects.CONFUSION, 2400, 0, 1),
			new EffectSupplier(() -> MobEffects.POISON, 2400, 0, 1),
			new EffectSupplier(() -> MobEffects.BLINDNESS, 2400, 0, 0.5f),
			new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 200, 0, 0.5f)),
			TagRef.PROTEINS, TagRef.VEGETABLES),

	PLATE_OF_LILY_CHICKEN(DelightFoodType.BOWL_MEAT, 16, 0.9f, List.of(
			new EffectSupplier(() -> MobEffects.FIRE_RESISTANCE, 6000, 0, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1)),
			TagRef.PROTEINS, TagRef.VEGETABLES),
	PLATE_OF_FIERY_SNAKES(DelightFoodType.BOWL_MEAT, 20, 1.9f, List.of(
			new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1),
			new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 6000, 2, 1),
			new EffectSupplier(() -> MobEffects.REGENERATION, 6000, 1, 1))),
	PLATE_OF_MEEF_WELLINGTON(DelightFoodType.BOWL_MEAT, 14, 0.8f, List.of(
			new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 3600, 1, 1),
			new EffectSupplier(() -> MobEffects.REGENERATION, 3600, 1, 1),
			new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1)),
			TagRef.PROTEINS, TagRef.GRAINS, TagRef.VEGETABLES),

	THORN_ROSE_TEA(DelightFoodType.ROSE, 4, 0.25f,
			new EffectSupplier(() -> MobEffects.REGENERATION, 300, 1, 1)),
	TORCHBERRY_JUICE(DelightFoodType.DRINK, 4, 0.25f,
			new EffectSupplier(TDEffects.FIRE_RANGE, 1200, 0, 1)),
	PHYTOCHEMICAL_JUICE(DelightFoodType.DRINK, 4, 0.25f,
			new EffectSupplier(TDEffects.POISON_RANGE, 1200, 0, 1)),
	GLACIER_ICE_TEA(DelightFoodType.DRINK, 4, 0.25f,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 1200, 0, 1)),
	TWILIGHT_SPRING(DelightFoodType.DRINK, 0, 0,
			new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 1200, 1, 1)),
	TEAR_DRINK(DelightFoodType.DRINK, 1, 0, List.of(
			new EffectSupplier(() -> MobEffects.FIRE_RESISTANCE, 12000, 0, 1),
			new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 12000, 1, 1),
			new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 1200, 0, 1))),
	;

	public final ItemEntry<Item> item;

	DelightFood(DelightFoodType r, int nutrition, float saturation, TagKey<Item>... tags) {
		this(r, nutrition, saturation, List.of(), tags);
	}

	DelightFood(DelightFoodType r, int nutrition, float saturation, EffectSupplier effects, TagKey<Item>... tags) {
		this(r, nutrition, saturation, List.of(effects), tags);
	}

	DelightFood(DelightFoodType r, int nutrition, float saturation, List<EffectSupplier> effects, TagKey<Item>... tags) {
		item = TDItems.simpleFood(r, name(), nutrition, saturation, effects, tags);
	}

	public static void register() {
	}

}
