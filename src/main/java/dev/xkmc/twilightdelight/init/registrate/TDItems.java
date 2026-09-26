package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.twilightdelight.content.item.food.ReusableDrinkItem;
import dev.xkmc.twilightdelight.content.item.tool.*;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.data.TagRef;
import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
import dev.xkmc.twilightdelight.init.registrate.delight.IFoodType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
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
	public static final ItemEntry<IceKnifeItem> ICE_KNIFE;
	public static final ItemEntry<TeardropSwordItem> TEARDROP_SWORD;
	public static final ItemEntry<WroughtIronSwordItem> WROUGHT_IRON_SWORD;

	public static final ItemEntry<Item> WITCHCRAFT_BONE;
	public static final ItemEntry<Item> WITCHCRAFT_BONE_MEAL;
	public static final ItemEntry<ReusableDrinkItem> CHAOS_INK_DRINK;

	// WITCHCRAFT
	static {
		WITCHCRAFT_BONE = TwilightDelight.REGISTRATE.item("witchcraft_bone", Item::new)
				.defaultModel().lang("Witchcraft Bone").register();
		WITCHCRAFT_BONE_MEAL = TwilightDelight.REGISTRATE.item("witchcraft_bone_meal", Item::new)
				.defaultModel().lang("Witchcraft Bone Meal").register();
		CHAOS_INK_DRINK = TwilightDelight.REGISTRATE.item("chaos_ink_drink",
						p -> new ReusableDrinkItem(p, 2, 0.3f, List.of(
								new EffectSupplier(TDEffects.LICH_CHARGE, 3600, 0, 1))))
				.model((ctx, pvd) -> {
					for (int i = 1; i <= 3; i++) {
						pvd.getBuilder(ctx.getName() + "_stage" + i)
								.parent(new ModelFile.UncheckedModelFile(ResourceLocation.parse("minecraft:item/generated")))
								.texture("layer0", pvd.modLoc("item/" + ctx.getName() + "_stage" + i));
					}
					var base = pvd.getBuilder(ctx.getName())
							.parent(new ModelFile.UncheckedModelFile(ResourceLocation.parse("minecraft:item/generated")))
							.texture("layer0", pvd.modLoc("item/" + ctx.getName()));
					float[] thresholds = {0.25f, 0.5f, 0.75f};
					for (int i = 0; i < 3; i++) {
						base.override().predicate(TwilightDelight.loc("sips"), thresholds[i])
								.model(new ModelFile.UncheckedModelFile(
										pvd.modLoc("item/" + ctx.getName() + "_stage" + (i + 1)))).end();
					}
				})
				.lang("Chaos Ink Drink").register();
	}

	// KNIVES
	static {
		IRONWOOD_KNIFE = handheld("ironwood_knife", IronwoodKnifeItem::new)
				.tab(TDBlocks.TAB.key(), (x, m) -> x.get().fillItemCategory(m))
				.tag(TagRef.KNIFE, TagRef.MOD_KNIFE).register();
		STEELEAF_KNIFE = handheld("steeleaf_knife", SteeleafKnifeItem::new)
				.tab(TDBlocks.TAB.key(), (x, m) -> x.get().fillItemCategory(m))
				.tag(TagRef.KNIFE, TagRef.MOD_KNIFE).register();
		KNIGHTMETAL_KNIFE = handheld("knightmetal_knife", KnightmetalKnifeItem::new)
				.lang("Knightly Knife")
				.tag(TagRef.KNIFE, TagRef.MOD_KNIFE).register();
		FIERY_KNIFE = handheld("fiery_knife", FieryKnifeItem::new)
				.model((ctx, pvd) -> pvd.handheld(ctx).customLoader(ItemLayerModelBuilder::begin).emissive(15, 15, 0))
				.tag(TagRef.KNIFE, TagRef.MOD_KNIFE).register();
		ICE_KNIFE = TwilightDelight.REGISTRATE.item("ice_knife", IceKnifeItem::new)
				.model((ctx, pvd) -> pvd.getBuilder(ctx.getName())
						.parent(new ModelFile.UncheckedModelFile(ResourceLocation.parse("minecraft:item/handheld")))
						.texture("layer0", pvd.modLoc("item/" + ctx.getName() + "_solid"))
						.texture("layer1", pvd.modLoc("item/" + ctx.getName() + "_clear"))
						.customLoader(ItemLayerModelBuilder::begin))
				.tag(TagRef.KNIFE, TagRef.MOD_KNIFE).register();
		TEARDROP_SWORD = handheld("teardrop_sword", TeardropSwordItem::new)
				.model((ctx, pvd) -> pvd.handheld(ctx).customLoader(ItemLayerModelBuilder::begin).emissive(15, 15, 0))
				.tag(ItemTags.SWORDS).register();
		WROUGHT_IRON_SWORD = TwilightDelight.REGISTRATE.item("wrought_iron_sword", WroughtIronSwordItem::new)
				.model((ctx, pvd) -> {})
				.tag(ItemTags.SWORDS).register();
	}

	@SafeVarargs
	public static ItemEntry<Item> simpleFood(IFoodType r, String name, int nutrition, float saturation, List<EffectSupplier> effects, TagKey<Item>... tags) {
		return food(name.toLowerCase(Locale.ROOT),
				p -> r.create(p.rarity(r.getRarity())),
				() -> simpleFood(r, nutrition, saturation, effects))
				.model(r::model).tag(tags).register();
	}

	public static FoodProperties simpleFood(IFoodType r, int nutrition, float saturation, List<EffectSupplier> effects) {
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
		return TwilightDelight.REGISTRATE.item(id, factory).model((ctx, pvd) -> pvd.handheld(ctx));
	}

	public static void register() {

	}

}
