package dev.xkmc.twilightdelight.init.data;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemTagsProvider;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
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

	public static void genItemTag(RegistrateItemTagsProvider pvd) {
		pvd.tag(INSECT)
				.add(TFBlocks.FIREFLY.get().asItem(),
						TFBlocks.CICADA.get().asItem(),
						TFBlocks.MOONWORM.get().asItem());
		pvd.tag(MILK).add(Items.MILK_BUCKET);
		pvd.tag(HYDRA_MEAT).add(TFItems.HYDRA_CHOP.get(), DelightFood.HYDRA_PIECE.item.get());
		pvd.tag(MEEF_COOKED).add(TFItems.COOKED_MEEF.get(), DelightFood.COOKED_MEEF_SLICE.item.get());
		pvd.tag(MEEF_RAW).add(TFItems.RAW_MEEF.get(), DelightFood.RAW_MEEF_SLICE.item.get());
		pvd.tag(VENSION_COOKED).add(TFItems.COOKED_VENISON.get(), DelightFood.COOKED_VENISON_RIB.item.get());
		pvd.tag(VENSION_RAW).add(TFItems.RAW_VENISON.get(), DelightFood.RAW_VENISON_RIB.item.get());
	}

	public static void genBlockTag(RegistrateTagsProvider<Block> pvd) {
		pvd.tag(ModTags.HEAT_SOURCES).add(TFBlocks.FIERY_BLOCK.get());
	}

}
