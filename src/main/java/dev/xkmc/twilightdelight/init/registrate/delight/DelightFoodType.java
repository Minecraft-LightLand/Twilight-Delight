package dev.xkmc.twilightdelight.init.registrate.delight;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.model.generators.ModelFile;

public enum DelightFoodType implements IFoodType {
	NONE(Rarity.COMMON, false, false, false, DelightFoodClassType.REGULAR),
	MEAT(Rarity.COMMON, true, false, false, DelightFoodClassType.REGULAR),
	MEAT_PIECE(Rarity.COMMON, true, true, false, DelightFoodClassType.REGULAR),
	COOKIE(Rarity.COMMON, false, true, false, DelightFoodClassType.REGULAR),
	HYDRA_PIECE(Rarity.UNCOMMON, true, true, false, DelightFoodClassType.FIRE_PROOF),
	STICK(Rarity.COMMON, false, true, false, DelightFoodClassType.STICK),
	SAUCE(Rarity.COMMON, false, false, false, DelightFoodClassType.SAUCE),
	BOWL(Rarity.COMMON, false, false, false, DelightFoodClassType.BOWL),
	BOWL_MEAT(Rarity.COMMON, true, false, false, DelightFoodClassType.BOWL),
	DRINK(Rarity.COMMON, false, false, true, DelightFoodClassType.DRINK),
	ROSE(Rarity.COMMON, false, false, true, DelightFoodClassType.ROSE_TEA);

	public final Rarity rarity;
	public final boolean meat, fast, drink;
	private final DelightFoodClassType type;

	DelightFoodType(Rarity rarity, boolean meat, boolean fast, boolean drink, DelightFoodClassType type) {
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
		return type.factory.apply(props);
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

}
