package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.twilightdelight.content.item.tool.*;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
import dev.xkmc.twilightdelight.init.registrate.delight.IFoodType;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import org.apache.commons.lang3.StringUtils;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class TDItems {

	public static final ItemEntry<FieryKnifeItem> FIERY_KNIFE;
	public static final ItemEntry<IronwoodKnifeItem> IRONWOOD_KNIFE;
	public static final ItemEntry<SteeleafKnifeItem> STEELEAF_KNIFE;
	public static final ItemEntry<KnightmetalKnifeItem> KNIGHTMETAL_KNIFE;
	public static final ItemEntry<TeardropSwordItem> TEARDROP_SWORD;

	// KNIVES
	static {
		IRONWOOD_KNIFE = handheld("ironwood_knife", IronwoodKnifeItem::new)
				.tab(TDBlocks.TAB.key(), (x, m) -> x.get().fillItemCategory(m))
				.register();
		STEELEAF_KNIFE = handheld("steeleaf_knife", SteeleafKnifeItem::new)
				.tab(TDBlocks.TAB.key(), (x, m) -> x.get().fillItemCategory(m))
				.register();
		KNIGHTMETAL_KNIFE = handheld("knightmetal_knife", KnightmetalKnifeItem::new)
				.lang("Knightly Knife").register();

		FIERY_KNIFE = handheld("fiery_knife", FieryKnifeItem::new)
				.model((ctx, pvd) -> pvd.handheld(ctx).customLoader(ItemLayerModelBuilder::begin).emissive(15, 15, 0))
				.tag(ModTags.KNIVES, CommonTags.TOOLS_KNIFE).register();
		TEARDROP_SWORD = handheld("teardrop_sword", TeardropSwordItem::new)
				.model((ctx, pvd) -> pvd.handheld(ctx).customLoader(ItemLayerModelBuilder::begin).emissive(15, 15, 0))
				.tag(ItemTags.SWORDS).register();
	}

	public static ItemEntry<Item> simpleFood(IFoodType r, String name, int nutrition, float saturation, EffectSupplier... effects) {
		return food(name.toLowerCase(Locale.ROOT),
				p -> r.create(p.rarity(r.getRarity())),
				() -> simpleFood(r, nutrition, saturation, effects))
				.model(r::model).register();
	}

	public static FoodProperties simpleFood(IFoodType r, int nutrition, float saturation, EffectSupplier... effects) {
		FoodProperties.Builder builder = new FoodProperties.Builder();
		builder = builder.nutrition(nutrition).saturationModifier(saturation);
		builder = r.process(builder);
		for (var eff : effects) {
			builder = builder.effect(eff::get, eff.chance());
		}
		r.container(builder);
		return builder.build();
	}

	private static <T extends Item> ItemBuilder<T, L2Registrate> food(String id, NonNullFunction<Item.Properties, T> factory,
																	  Supplier<FoodProperties> food) {
		return TwilightDelight.REGISTRATE.item(id, p -> factory.apply(p.food(food.get()))).lang(toEnglishName(id));
	}

	private static final Set<String> SMALL_WORDS = Set.of("of", "the", "with");

	public static String toEnglishName(String internalName) {
		return Arrays.stream(internalName.toLowerCase(Locale.ROOT).split("_"))
				.map(e -> SMALL_WORDS.contains(e) ? e : StringUtils.capitalize(e))
				.collect(Collectors.joining(" "));
	}

	private static <T extends Item> ItemBuilder<T, L2Registrate> handheld(String id, NonNullFunction<Item.Properties, T> factory) {
		return TwilightDelight.REGISTRATE.item(id, factory).model((ctx, pvd) -> pvd.handheld(ctx)).tag(ModTags.KNIVES, CommonTags.TOOLS_KNIFE);
	}

	public static void register() {

	}

}
