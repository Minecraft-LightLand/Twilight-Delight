package dev.xkmc.twilightdelight.init.registrate.delight;

import dev.xkmc.twilightdelight.content.item.food.InBowlItem;
import dev.xkmc.twilightdelight.content.item.food.RoseTeaItem;
import dev.xkmc.twilightdelight.content.item.food.TDDrinkableItem;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public enum DelightFoodClassType {
	REGULAR(TDFoodItem::new),
	BOWL(p -> new InBowlItem(p.stacksTo(16))),
	DRINK(p -> new TDDrinkableItem(p.stacksTo(16))),
	ROSE_TEA(p -> new RoseTeaItem(p.stacksTo(16)));

	public final Function<Item.Properties, Item> factory;

	DelightFoodClassType(Function<Item.Properties, Item> factory) {
		this.factory = factory;
	}
}
