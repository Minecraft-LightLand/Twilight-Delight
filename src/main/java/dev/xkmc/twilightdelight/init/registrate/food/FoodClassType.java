package dev.xkmc.twilightdelight.init.registrate.food;

import com.teamabnormals.neapolitan.core.Neapolitan;
import dev.xkmc.twilightdelight.compat.neapolitan.TDIceCreamItem;
import dev.xkmc.twilightdelight.compat.neapolitan.TDMilkshakeItem;
import dev.xkmc.twilightdelight.content.item.food.InBowlItem;
import dev.xkmc.twilightdelight.content.item.food.RoseTeaItem;
import dev.xkmc.twilightdelight.content.item.food.TDDrinkableItem;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.function.Function;
import java.util.function.Supplier;

public enum FoodClassType {
	REGULAR(FarmersDelight.MODID, () -> TDFoodItem::new),
	BOWL(FarmersDelight.MODID, () -> p -> new InBowlItem(p.stacksTo(16))),
	DRINK(FarmersDelight.MODID, () -> p -> new TDDrinkableItem(p.stacksTo(16))),
	ROSE_TEA(FarmersDelight.MODID, () -> p -> new RoseTeaItem(p.stacksTo(16))),
	MILKSHAKE(Neapolitan.MOD_ID, () -> p -> new TDMilkshakeItem(p.stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))),
	ICE_CREAM(Neapolitan.MOD_ID, () -> p -> new TDIceCreamItem(p.stacksTo(1).craftRemainder(Items.BOWL)));

	public final String modid;
	public final Supplier<Function<Item.Properties, Item>> factory;

	FoodClassType(String modid, Supplier<Function<Item.Properties, Item>> factory) {
		this.modid = modid;
		this.factory = factory;
	}
}
