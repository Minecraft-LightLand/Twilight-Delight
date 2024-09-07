package dev.xkmc.twilightdelight.compat;

import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ThirstCompat {

	public static void init() {
		NeoForge.EVENT_BUS.register(ThirstCompat.class);
	}

	@SubscribeEvent
	public static void onThirst(RegisterThirstValueEvent event) {
		event.addDrink(DelightFood.THORN_ROSE_TEA.item.get(), 8, 13);
		event.addDrink(DelightFood.TORCHBERRY_JUICE.item.get(), 8, 13);
		event.addDrink(DelightFood.PHYTOCHEMICAL_JUICE.item.get(), 8, 13);
		event.addDrink(DelightFood.GLACIER_ICE_TEA.item.get(), 8, 13);
		event.addDrink(DelightFood.TWILIGHT_SPRING.item.get(), 8, 13);
		event.addDrink(DelightFood.TEAR_DRINK.item.get(), 8, 13);
		event.addDrink(DelightFood.THOUSAND_PLANT_STEW.item.get(), 8, 13);
		event.addDrink(DelightFood.GLOWSTEW.item.get(), 8, 13);
		event.addDrink(DelightFood.BORER_TEAR_SOUP.item.get(), 8, 13);
		event.addDrink(DelightFood.MUSHGLOOM_SAUCE.item.get(), 4, 6);
	}

}
