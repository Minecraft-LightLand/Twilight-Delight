package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.xkmc.twilightdelight.content.item.food.InBowlItem;
import dev.xkmc.twilightdelight.content.item.food.RoseTeaItem;
import dev.xkmc.twilightdelight.content.item.food.TDDrinkableItem;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import dev.xkmc.twilightdelight.content.item.tool.*;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class TDItems {

	public static final Tab GOLEMS = new Tab();

	static {
		TwilightDelight.REGISTRATE.creativeModeTab(() -> GOLEMS);
	}

	public static final ItemEntry<FieryKnifeItem> FIERY_KNIFE;
	public static final ItemEntry<IronwoodKnifeItem> IRONWOOD_KNIFE;
	public static final ItemEntry<SteeleafKnifeItem> STEELEAF_KNIFE;
	public static final ItemEntry<KnightmetalKnifeItem> KNIGHTMETAL_KNIFE;
	public static final ItemEntry<TeardropSwordItem> TEARDROP_SWORD;

	public record EffectSupplier(Supplier<MobEffect> eff, int time, int amp, float chance) {

		public MobEffectInstance get() {
			return new MobEffectInstance(eff.get(), time, amp);
		}

	}

	public enum FoodType {
		NONE(Rarity.COMMON, false, false, false, TDFoodItem::new),
		MEAT(Rarity.COMMON, true, false, false, TDFoodItem::new),
		COOKIE(Rarity.COMMON, false, true, false, TDFoodItem::new),
		UNCOMMON_MEAT(Rarity.UNCOMMON, true, false, false, TDFoodItem::new),
		BOWL(Rarity.COMMON, false, false, false, p -> new InBowlItem(p.stacksTo(16))),
		BOWL_MEAT(Rarity.COMMON, true, false, false, p -> new InBowlItem(p.stacksTo(16))),
		DRINK(Rarity.COMMON, false, false, true, p -> new TDDrinkableItem(p.stacksTo(16))),
		ROSE(Rarity.COMMON, false, false, true, p -> new RoseTeaItem(p.stacksTo(16)));

		public final Rarity rarity;
		public final boolean meat, fast, drink;
		public final Function<Item.Properties, Item> factory;

		FoodType(Rarity rarity, boolean meat, boolean fast, boolean drink, Function<Item.Properties, Item> factory) {
			this.rarity = rarity;
			this.meat = meat;
			this.fast = fast;
			this.drink = drink;
			this.factory = factory;
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
	}

	public enum Food {
		HYDRA_PIECE(FoodType.UNCOMMON_MEAT, 9, 2.0F,
				new EffectSupplier(() -> MobEffects.REGENERATION, 20, 0, 1)),
		RAW_VENISON_RIB(FoodType.MEAT, 2, 0.25f),
		COOKED_VENISON_RIB(FoodType.MEAT, 4, 0.875f),
		RAW_MEEF_SLICE(FoodType.MEAT, 1, 0.7F),
		COOKED_MEEF_SLICE(FoodType.MEAT, 3, 0.6F),
		RAW_INSECT(FoodType.MEAT, 2, 0.2F),
		COOKED_INSECT(FoodType.MEAT, 6, 0.6F),
		TORCHBERRY_COOKIE(FoodType.COOKIE, 2, 0.2F),
		CHOCOLATE_WAFER(FoodType.NONE, 9, 0.6F),
		EXPERIMENT_113(FoodType.MEAT, 6, 0.2F,
				new EffectSupplier(() -> MobEffects.WEAKNESS, 100, 0, 0.33f)),
		EXPERIMENT_110(FoodType.MEAT, 12, 0.3F,
				new EffectSupplier(() -> MobEffects.CONFUSION, 1200, 0, 1),
				new EffectSupplier(() -> MobEffects.HEALTH_BOOST, 2400, 4, 1),
				new EffectSupplier(() -> MobEffects.NIGHT_VISION, 2400, 0, 1)),
		BERRY_STICK(FoodType.NONE, 4, 0.2f),

		GLOWSTEW(FoodType.BOWL, 7, 0.675F,
				new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
				new EffectSupplier(ModEffects.COMFORT, 1200, 0, 1)),
		GLOW_VENISON_RIB_WITH_PASTA(FoodType.BOWL_MEAT, 12, 0.7f,
				new EffectSupplier(() -> MobEffects.GLOWING, 200, 0, 1),
				new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1)),
		FRIED_INSECT(FoodType.BOWL_MEAT, 10, 0.61f,
				new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1)),
		THOUSAND_PLANT_STEW(FoodType.BOWL, 10, 0.61f,
				new EffectSupplier(() -> MobEffects.HEALTH_BOOST, 600, 1, 1),
				new EffectSupplier(() -> MobEffects.CONFUSION, 300, 0, 0.1f)),
		GRILLED_GHAST(FoodType.BOWL_MEAT, 10, 0.72f,
				new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1)),
		PLATE_OF_LILY_CHICKEN(FoodType.BOWL_MEAT, 16, 0.875f,
				new EffectSupplier(() -> MobEffects.FIRE_RESISTANCE, 6000, 0, 1),
				new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1)),
		PLATE_OF_FIERY_SNAKES(FoodType.BOWL_MEAT, 20, 1.9f,
				new EffectSupplier(ModEffects.NOURISHMENT, 6000, 0, 1),
				new EffectSupplier(ModEffects.COMFORT, 6000, 0, 1),
				new EffectSupplier(() -> MobEffects.DAMAGE_BOOST, 6000, 1, 1),
				new EffectSupplier(() -> MobEffects.REGENERATION, 400, 1, 1)),
		AURORA_ICE_CREAM(FoodType.BOWL, 5, 0.2f,
				new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 1200, 2, 1),
				new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 600, 0, 1)),

		THORN_ROSE_TEA(FoodType.ROSE, 4, 0.25f,
				new EffectSupplier(() -> MobEffects.REGENERATION, 100, 1, 1)),
		TORCHBERRY_JUICE(FoodType.DRINK, 4, 0.25f,
				new EffectSupplier(TDEffects.FIRE_RANGE, 3600, 0, 1)),
		PHYTOCHEMICAL_JUICE(FoodType.DRINK, 4, 0.25f,
				new EffectSupplier(TDEffects.POISON_RANGE, 3600, 2, 1)),
		GLACIER_ICE_TEA(FoodType.DRINK, 4, 0.25f,
				new EffectSupplier(TDEffects.FROZEN_RANGE, 3600, 0, 1)),
		TWILIGHT_SPRING(FoodType.DRINK, 0, 0,
				new EffectSupplier(() -> MobEffects.DAMAGE_RESISTANCE, 600, 1, 1)),
		TEAR_DRINK(FoodType.DRINK, 1, 0,
				new EffectSupplier(() -> MobEffects.FIRE_RESISTANCE, 12000, 0, 1),
				new EffectSupplier(TDEffects.TEMPORAL_SADNESS, 1200, 0, 1)),
		;

		public final ItemEntry<Item> item;

		Food(FoodType r, int nutrition, float saturation, EffectSupplier... effects) {
			item = food(name().toLowerCase(Locale.ROOT), p -> r.factory.apply(p.rarity(r.rarity)), e -> {
				e = e.nutrition(nutrition).saturationMod(saturation);
				e = r.process(e);
				for (var eff : effects) {
					e = e.effect(eff::get, eff.chance);
				}
				return e.build();
			}).model(r::model).register();
		}

		public static void register() {
		}

	}

	// KNIVES
	static {
		Food.register();
		FIERY_KNIFE = handheld("fiery_knife", FieryKnifeItem::new).tag(ModTags.KNIVES).register();
		IRONWOOD_KNIFE = handheld("ironwood_knife", IronwoodKnifeItem::new).tag(ModTags.KNIVES).register();
		STEELEAF_KNIFE = handheld("steeleaf_knife", SteeleafKnifeItem::new).tag(ModTags.KNIVES).register();
		KNIGHTMETAL_KNIFE = handheld("knightmetal_knife", KnightmetalKnifeItem::new).tag(ModTags.KNIVES).register();
		TEARDROP_SWORD = handheld("teardrop_sword", TeardropSwordItem::new).tag(Tags.Items.TOOLS_SWORDS).register();
	}

	private static <T extends Item> ItemBuilder<T, Registrate> food(String id, Function<Item.Properties, T> factory,
																	Function<FoodProperties.Builder, FoodProperties> food) {
		return TwilightDelight.REGISTRATE.item(id, p -> factory.apply(p.food(food.apply(new FoodProperties.Builder()))));
	}

	private static <T extends Item> ItemBuilder<T, Registrate> handheld(String id, NonNullFunction<Item.Properties, T> factory) {
		return TwilightDelight.REGISTRATE.item(id, factory).model((ctx, pvd) -> pvd.handheld(ctx));
	}

	public static class Tab extends CreativeModeTab {

		public Tab() {
			super("tab." + TwilightDelight.MODID);
		}

		@Override
		public @NotNull ItemStack makeIcon() {
			return Food.EXPERIMENT_113.item.asStack();
		}
	}

	public static void register() {

	}

}
