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
import java.util.function.Supplier;

public enum NeapolitanFoodType implements IFoodType {
	MILKSHAKE(Rarity.COMMON, p -> new TDMilkshakeItem(p.stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)), () -> Items.GLASS_BOTTLE),
	ICE_CREAM(Rarity.COMMON, p -> new TDIceCreamItem(p.stacksTo(16).craftRemainder(Items.BOWL)), () -> Items.BOWL);

	public final Rarity rarity;
	private final Function<Item.Properties, Item> type;
	private final Supplier<Item> container;

	NeapolitanFoodType(Rarity rarity, Function<Item.Properties, Item> type, Supplier<Item> container) {
		this.rarity = rarity;
		this.type = type;
		this.container = container;
	}

	public FoodProperties.Builder process(FoodProperties.Builder builder) {
		return builder;
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

	@Override
	public void container(FoodProperties.Builder builder) {
		builder.usingConvertsTo(container.get());
	}

}
