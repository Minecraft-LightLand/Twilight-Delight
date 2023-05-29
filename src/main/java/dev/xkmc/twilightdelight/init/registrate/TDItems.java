package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.twilightdelight.content.item.tool.*;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
import dev.xkmc.twilightdelight.init.registrate.delight.IFoodType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class TDItems {

	public static final ItemEntry<FieryKnifeItem> FIERY_KNIFE;
	public static final ItemEntry<IronwoodKnifeItem> IRONWOOD_KNIFE;
	public static final ItemEntry<SteeleafKnifeItem> STEELEAF_KNIFE;
	public static final ItemEntry<KnightmetalKnifeItem> KNIGHTMETAL_KNIFE;
	public static final ItemEntry<TeardropSwordItem> TEARDROP_SWORD;

	// KNIVES
	static {
		FIERY_KNIFE = handheld("fiery_knife", FieryKnifeItem::new).tag(ModTags.KNIVES).register();
		IRONWOOD_KNIFE = handheld("ironwood_knife", IronwoodKnifeItem::new).tag(ModTags.KNIVES).register();
		STEELEAF_KNIFE = handheld("steeleaf_knife", SteeleafKnifeItem::new).tag(ModTags.KNIVES).register();
		KNIGHTMETAL_KNIFE = handheld("knightmetal_knife", KnightmetalKnifeItem::new).tag(ModTags.KNIVES).register();
		TEARDROP_SWORD = handheld("teardrop_sword", TeardropSwordItem::new).tag(Tags.Items.TOOLS_SWORDS).register();
	}

	public static ItemEntry<Item> simpleFood(IFoodType r, String name, int nutrition, float saturation, EffectSupplier... effects) {
		return food(name.toLowerCase(Locale.ROOT),
				p -> r.create(p.rarity(r.getRarity())),
				() -> simpleFood(r, nutrition, saturation, effects))
				.model(r::model).register();
	}

	public static FoodProperties simpleFood(IFoodType r, int nutrition, float saturation, EffectSupplier... effects) {
		FoodProperties.Builder builder = new FoodProperties.Builder();
		builder = builder.nutrition(nutrition).saturationMod(saturation);
		builder = r.process(builder);
		for (var eff : effects) {
			builder = builder.effect(eff::get, eff.chance());
		}
		return builder.build();
	}

	private static <T extends Item> ItemBuilder<T, Registrate> food(String id, Function<Item.Properties, T> factory,
																	Supplier<FoodProperties> food) {
		return TwilightDelight.REGISTRATE.item(id, p -> factory.apply(p.food(food.get())));
	}

	private static <T extends Item> ItemBuilder<T, Registrate> handheld(String id, NonNullFunction<Item.Properties, T> factory) {
		return TwilightDelight.REGISTRATE.item(id, factory).model((ctx, pvd) -> pvd.handheld(ctx));
	}

	public static void register() {

	}

}
