package dev.xkmc.twilightdelight.init.registrate.delight;

import dev.xkmc.twilightdelight.content.item.food.InBowlItem;
import dev.xkmc.twilightdelight.content.item.food.RoseTeaItem;
import dev.xkmc.twilightdelight.content.item.food.TDDrinkableItem;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public enum DelightFoodClassType {
	REGULAR(TDFoodItem::new),
	FIRE_PROOF(p-> new TDFoodItem(p.fireResistant())),
	STICK(p -> new TDFoodItem(p.craftRemainder(Items.STICK))),
	BOWL(p -> new InBowlItem(p.stacksTo(16).craftRemainder(Items.BOWL))),
	DRINK(p -> new TDDrinkableItem(p.stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))),
	ROSE_TEA(p -> new RoseTeaItem(p.stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));

	public final Function<Item.Properties, Item> factory;

	DelightFoodClassType(Function<Item.Properties, Item> factory) {
		this.factory = factory;
	}
}
