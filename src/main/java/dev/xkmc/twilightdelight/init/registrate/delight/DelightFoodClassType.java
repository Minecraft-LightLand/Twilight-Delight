package dev.xkmc.twilightdelight.init.registrate.delight;

import dev.xkmc.twilightdelight.content.item.food.InBowlItem;
import dev.xkmc.twilightdelight.content.item.food.RoseTeaItem;
import dev.xkmc.twilightdelight.content.item.food.TDDrinkableItem;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;
import java.util.function.Supplier;

public enum DelightFoodClassType {
	REGULAR(TDFoodItem::new, () -> Items.AIR),
	FIRE_PROOF(p -> new TDFoodItem(p.fireResistant()), () -> Items.AIR),
	STICK(p -> new TDFoodItem(p.craftRemainder(Items.STICK)), () -> Items.STICK),
	SAUCE(p -> new TDFoodItem(p.stacksTo(64)), () -> Items.BOWL),
	BOWL(p -> new TDFoodItem(p.stacksTo(16)), () -> Items.BOWL),
	DRINK(p -> new TDDrinkableItem(p.stacksTo(16)), () -> Items.GLASS_BOTTLE),
	ROSE_TEA(p -> new RoseTeaItem(p.stacksTo(16)), () -> Items.GLASS_BOTTLE);

	private final Function<Item.Properties, Item> factory;
	private final Supplier<Item> container;

	DelightFoodClassType(Function<Item.Properties, Item> factory, Supplier<Item> container) {
		this.factory = factory;
		this.container = container;
	}

	public void container(FoodProperties.Builder builder) {
		builder.usingConvertsTo(container.get());
	}

	public Item create(Item.Properties props) {
		var cont = container.get();
		return factory.apply(cont == Items.AIR ? props : props.craftRemainder(cont));
	}
}
