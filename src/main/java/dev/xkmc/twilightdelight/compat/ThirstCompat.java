package dev.xkmc.twilightdelight.compat;

import dev.ghen.thirst.api.ThirstHelper;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;

public class ThirstCompat {

	public static void init() {
		ThirstHelper.addDrink(DelightFood.THORN_ROSE_TEA.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.TORCHBERRY_JUICE.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.PHYTOCHEMICAL_JUICE.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.GLACIER_ICE_TEA.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.TWILIGHT_SPRING.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.TEAR_DRINK.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.THOUSAND_PLANT_STEW.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.GLOWSTEW.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.BORER_TEAR_SOUP.item.get(), 8, 13);
		ThirstHelper.addDrink(DelightFood.MUSHGLOOM_SAUCE.item.get(), 4, 6);
	}

}
