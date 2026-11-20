package dev.xkmc.twilightdelight.init.data;

import com.teamabnormals.neapolitan.core.Neapolitan;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
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

	public static final TagKey<Item> INSECT = ItemTags.create(new ResourceLocation("forge", "insect"));
	public static final TagKey<Item> MILK = ItemTags.create(new ResourceLocation("forge", "milk"));
	public static final TagKey<Item> HYDRA_MEAT = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "hydra_meat"));
	public static final TagKey<Item> MEEF_COOKED = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "meef_cooked"));
	public static final TagKey<Item> MEEF_RAW = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "meef_raw"));
	public static final TagKey<Item> VENSION_COOKED = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "vension_cooked"));
	public static final TagKey<Item> VENSION_RAW = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "vension_raw"));
	public static final TagKey<Item> NAGA_COOKED = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "naga_cooked"));
	public static final TagKey<Item> NAGA_RAW = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "naga_raw"));
	public static final TagKey<Item> YETI_COOKED = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "yeti_cooked"));
	public static final TagKey<Item> YETI_RAW = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "yeti_raw"));
	public static final TagKey<Item> FERNS = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "ferns"));
	public static final TagKey<Item> BERRIES = ItemTags.create(new ResourceLocation(TwilightDelight.MODID, "berries"));

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
		pvd.addTag(NAGA_COOKED).add(DelightFood.COOKED_NAGA_MEAT.item.get(), DelightFood.COOKED_NAGA_PIECE.item.get());
		pvd.addTag(NAGA_RAW).add(DelightFood.RAW_NAGA_MEAT.item.get(), DelightFood.RAW_NAGA_PIECE.item.get());
		pvd.addTag(YETI_COOKED).add(DelightFood.COOKED_YETI_MEAT.item.get(), DelightFood.COOKED_YETI_MEAT_SLICE.item.get());
		pvd.addTag(YETI_RAW).add(DelightFood.RAW_YETI_MEAT.item.get(), DelightFood.RAW_YETI_MEAT_SLICE.item.get());
		// ferns: the vanilla fern and the Twilight Forest fiddlehead are interchangeable
		pvd.addTag(FERNS).add(Items.FERN, Items.LARGE_FERN, TFBlocks.FIDDLEHEAD.get().asItem());
		// berries: every berry this side of the mod ships, so the berry slot in a recipe
		// takes what the player happens to have
		pvd.addTag(BERRIES).add(Items.SWEET_BERRIES, Items.GLOW_BERRIES, TFItems.TORCHBERRIES.get());
		// uncrafting a scepter hands back its repair ingredient, a golden apple for the fortification
		// scepter for example, so keep them off the Twilight Forest uncrafting table
		pvd.addTag(TagRef.BANNED_UNCRAFTABLES)
				.add(TFItems.ZOMBIE_SCEPTER.get(),
						TFItems.TWILIGHT_SCEPTER.get(),
						TFItems.LIFEDRAIN_SCEPTER.get(),
						TFItems.FORTIFICATION_SCEPTER.get());
	}

	public static void genBlockTag(RegistrateTagsProvider.IntrinsicImpl<Block> pvd) {
		pvd.addTag(TagRef.HEAT_SOURCES).add(TFBlocks.FIERY_BLOCK.get());
		pvd.addTag(ModTags.Blocks.MUSHROOM_COLONY_GROWABLE_ON).add(TDBlocks.TWILIGHT_RICH_SOIL.get());
		// plants that hang from the block above them, so rich soil under them still boosts them
		pvd.addTag(ModTags.Blocks.PLANTED_FROM_BELOW).add(TFBlocks.TORCHBERRY_PLANT.get(), TFBlocks.ROOT_STRAND.get());
		pvd.addTag(BlockTags.DIRT).add(TDBlocks.TWILIGHT_RICH_SOIL.get());
		pvd.addTag(BlockTags.MUSHROOM_GROW_BLOCK).add(TDBlocks.TWILIGHT_RICH_SOIL.get());
		pvd.addTag(BlockTags.BAMBOO_PLANTABLE_ON).add(TDBlocks.TWILIGHT_RICH_SOIL.get());
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
