package dev.xkmc.twilightdelight.init.registrate.delight;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public enum DelightFoodType implements IFoodType {
	NONE(Rarity.COMMON, false, false, DelightFoodClassType.REGULAR),
	FAST(Rarity.COMMON, true, false, DelightFoodClassType.REGULAR),
	DRUMSTICK(Rarity.UNCOMMON, true, false, DelightFoodClassType.REGULAR),
	HYDRA_PIECE(Rarity.UNCOMMON, true, false, DelightFoodClassType.FIRE_PROOF),
	STICK(Rarity.COMMON, true, false, DelightFoodClassType.STICK),
	SAUCE(Rarity.COMMON, false, false, DelightFoodClassType.SAUCE),
	BOWL(Rarity.COMMON, false, false, DelightFoodClassType.BOWL),
	DRINK(Rarity.COMMON, false, true, DelightFoodClassType.DRINK),
	ROSE(Rarity.COMMON, false, true, DelightFoodClassType.ROSE_TEA);

	public final Rarity rarity;
	public final boolean  fast, drink;
	private final DelightFoodClassType type;

	DelightFoodType(Rarity rarity, boolean fast, boolean drink, DelightFoodClassType type) {
		this.rarity = rarity;
		this.fast = fast;
		this.drink = drink;
		this.type = type;
	}

	public FoodProperties.Builder process(FoodProperties.Builder e) {
		if (fast) e = e.fast();
		if (drink) e = e.alwaysEdible();
		return e;
	}

	public void model(DataGenContext<Item, Item> ctx, RegistrateItemModelProvider pvd) {
		if (drink) {
			pvd.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile(
							ResourceLocation.parse("farmersdelight:item/mug")))
					.texture("layer0", pvd.itemTexture(ctx));
		} else if (this == DRUMSTICK || this == STICK) {
			pvd.handheld(ctx);
		} else {
			pvd.generated(ctx);
		}
	}

	public Item create(Item.Properties props) {
		return type.create(props);
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public void container(FoodProperties.Builder builder) {
		type.container(builder);
	}

}
