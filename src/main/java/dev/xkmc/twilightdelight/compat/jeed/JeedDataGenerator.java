package dev.xkmc.twilightdelight.compat.jeed;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JeedDataGenerator {

	private static JeedDataGenerator INSTANCE;

	public static void add(Item item, MobEffect... effects) {
		if (INSTANCE == null) {
			INSTANCE = new JeedDataGenerator();
		}
		for (MobEffect eff : effects) {
			INSTANCE.put(eff, item);
		}
	}

	public static void finalizeRecipes(RegistrateRecipeProvider pvd) {
		if (INSTANCE != null) {
			INSTANCE.generate(pvd);
		}
		INSTANCE = null;
	}

	private final LinkedHashMap<MobEffect, List<Item>> map = new LinkedHashMap<>();

	private void put(MobEffect eff, Item item) {
		map.computeIfAbsent(eff, k -> new ArrayList<>()).add(item);
	}

	private void generate(RegistrateRecipeProvider pvd) {
		map.forEach((k, v) -> {
			JeedEffectRecipeData data = new JeedEffectRecipeData(k,
					new ArrayList<>(v.stream().map(Ingredient::of).toList()));
			JeedEffectRecipeBuilded recipe = new JeedEffectRecipeBuilded(
					new ResourceLocation(TwilightDelight.MODID,
							"jeed/" + ForgeRegistries.MOB_EFFECTS.getKey(k).getPath()),
					data);
			pvd.accept(recipe);
		});
	}

}
