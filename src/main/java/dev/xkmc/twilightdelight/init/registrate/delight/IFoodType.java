package dev.xkmc.twilightdelight.init.registrate.delight;

import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemModelProvider;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public interface IFoodType {

	FoodProperties.Builder process(FoodProperties.Builder e);

	void model(DataGenContext<Item, Item> ctx, RegistrateItemModelProvider pvd);

	Item create(Item.Properties props);

	Rarity getRarity();
}
