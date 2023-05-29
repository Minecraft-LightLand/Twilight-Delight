package dev.xkmc.twilightdelight.init.registrate.food;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.model.generators.ModelFile;

public enum FoodType {
	NONE(Rarity.COMMON, false, false, false, FoodClassType.REGULAR),
	MEAT(Rarity.COMMON, true, false, false, FoodClassType.REGULAR),
	COOKIE(Rarity.COMMON, false, true, false, FoodClassType.REGULAR),
	UNCOMMON_MEAT(Rarity.UNCOMMON, true, false, false, FoodClassType.REGULAR),
	BOWL(Rarity.COMMON, false, false, false, FoodClassType.BOWL),
	BOWL_MEAT(Rarity.COMMON, true, false, false, FoodClassType.BOWL),
	DRINK(Rarity.COMMON, false, false, true, FoodClassType.DRINK),
	ROSE(Rarity.COMMON, false, false, true, FoodClassType.ROSE_TEA),
	MILKSHAKE(Rarity.COMMON, false, false, false, FoodClassType.MILKSHAKE),
	ICE_CREAM(Rarity.COMMON, false, false, false, FoodClassType.ICE_CREAM);

	public final Rarity rarity;
	public final boolean meat, fast, drink;
	private final FoodClassType type;

	FoodType(Rarity rarity, boolean meat, boolean fast, boolean drink, FoodClassType type) {
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
		if (drink) {
			pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile(
							new ResourceLocation("farmersdelight:item/mug")))
					.texture("layer0", pvd.itemTexture(ctx));
		} else {
			pvd.generated(ctx);
		}
	}

	public Item create(Item.Properties props) {
		return type.factory.get().apply(props);
	}
}
