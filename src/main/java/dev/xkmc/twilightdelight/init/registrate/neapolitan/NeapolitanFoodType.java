package dev.xkmc.twilightdelight.init.registrate.neapolitan;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import dev.xkmc.twilightdelight.compat.neapolitan.TDIceCreamItem;
import dev.xkmc.twilightdelight.compat.neapolitan.TDMilkshakeItem;
import dev.xkmc.twilightdelight.init.registrate.delight.IFoodType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

import java.util.function.Function;

public enum NeapolitanFoodType implements IFoodType {
	MILKSHAKE(Rarity.COMMON, false, false, false, p -> new TDMilkshakeItem(p.stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))),
	ICE_CREAM(Rarity.COMMON, false, false, false, p -> new TDIceCreamItem(p.stacksTo(1).craftRemainder(Items.BOWL)));

	public final Rarity rarity;
	public final boolean meat, fast, drink;
	private final Function<Item.Properties, Item> type;

	NeapolitanFoodType(Rarity rarity, boolean meat, boolean fast, boolean drink, Function<Item.Properties, Item> type) {
		this.rarity = rarity;
		this.meat = meat;
		this.fast = fast;
		this.drink = drink;
		this.type = type;
	}

	public FoodProperties.Builder process(FoodProperties.Builder e) {
		if (meat) e = e.meat();
		if (fast) e = e.fast();
		if (drink) e = e.alwaysEat();
		return e;
	}

	public void model(DataGenContext<Item, Item> ctx, RegistrateItemModelProvider pvd) {
		pvd.generated(ctx);
	}

	public Item create(Item.Properties props) {
		return type.apply(props);
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

}
