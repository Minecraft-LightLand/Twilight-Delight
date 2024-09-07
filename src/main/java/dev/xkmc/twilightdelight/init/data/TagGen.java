package dev.xkmc.twilightdelight.init.data;

import com.teamabnormals.neapolitan.core.Neapolitan;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.init.registrate.neapolitan.NeapolitanCakes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.tag.ModTags;

public class TagGen {

	public static final TagKey<Item> INSECT = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "insect"));//TODO
	public static final TagKey<Item> MILK = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "milk"));
	public static final TagKey<Item> HYDRA_MEAT = ItemTags.create(TwilightDelight.loc("hydra_meat"));
	public static final TagKey<Item> MEEF_COOKED = ItemTags.create(TwilightDelight.loc("meef_cooked"));
	public static final TagKey<Item> MEEF_RAW = ItemTags.create(TwilightDelight.loc("meef_raw"));
	public static final TagKey<Item> VENSION_COOKED = ItemTags.create(TwilightDelight.loc("vension_cooked"));
	public static final TagKey<Item> VENSION_RAW = ItemTags.create(TwilightDelight.loc("vension_raw"));

	public static void genItemTag(RegistrateItemTagsProvider pvd) {
		pvd.addTag(INSECT)
				.add(TFBlocks.FIREFLY.get().asItem(),
						TFBlocks.CICADA.get().asItem(),
						TFBlocks.MOONWORM.get().asItem());
		pvd.addTag(MILK).add(Items.MILK_BUCKET);
		pvd.addTag(HYDRA_MEAT).add(TFItems.HYDRA_CHOP.get(), DelightFood.HYDRA_PIECE.item.get());
		pvd.addTag(MEEF_COOKED).add(TFItems.COOKED_MEEF.get(), DelightFood.COOKED_MEEF_SLICE.item.get());
		pvd.addTag(MEEF_RAW).add(TFItems.RAW_MEEF.get(), DelightFood.RAW_MEEF_SLICE.item.get());
		pvd.addTag(VENSION_COOKED).add(TFItems.COOKED_VENISON.get(), DelightFood.COOKED_VENISON_RIB.item.get());
		pvd.addTag(VENSION_RAW).add(TFItems.RAW_VENISON.get(), DelightFood.RAW_VENISON_RIB.item.get());
	}

	public static void genBlockTag(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
		pvd.addTag(ModTags.HEAT_SOURCES).add(TFBlocks.FIERY_BLOCK.get());
		var candle = pvd.addTag(BlockTags.CANDLE_CAKES);
		if (ModList.get().isLoaded(Neapolitan.MOD_ID)) {
			for (var e : NeapolitanCakes.values()) {
				candle.addOptional(e.candle.getId());
				for (var c : e.colored_candles)
					candle.addOptional(c.getId());
			}
		}
	}

}
