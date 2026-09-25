package dev.xkmc.twilightdelight.init.registrate.delight;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.content.block.TwilightCakeBlock;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.data.TagRef;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.List;
import java.util.Locale;

public enum DelightCake {

	TWILIGHT(List.of(
			new EffectSupplier(MobEffects.NIGHT_VISION, 600, 0, 1),
			new EffectSupplier(ModEffects.NOURISHMENT, 1200, 0, 1),
			new EffectSupplier(TDEffects.TWILIGHT_AURA, 400, 0, 1)));

	public static void register() {

	}

	public final BlockEntry<TwilightCakeBlock> block;
	public final ItemEntry<TDFoodItem> slice;

	DelightCake(List<EffectSupplier> effects) {
		String name = name().toLowerCase(Locale.ROOT);
		FoodProperties food = TDItems.simpleFood(DelightFoodType.NONE, 2, 0.3f, effects);
		slice = TwilightDelight.REGISTRATE.item(name + "_cake_slice", p -> new TDFoodItem(p.food(food)))
				.tag(TagRef.SWEETS, TagRef.SNACKS, TagRef.SUGARS).defaultModel().defaultLang().register();
		block = TwilightDelight.REGISTRATE.block(name + "_cake",
						p -> new TwilightCakeBlock(food, BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)))
				.blockstate((ctx, pvd) -> genCakeModels(ctx, pvd, name))
				.loot((pvd, block) -> pvd.dropOther(block, slice.get()))
				.item().properties(p -> p.stacksTo(1)).model((ctx, pvd) -> pvd.generated(ctx)).build()
				.defaultLang().register();
	}

	private static void genCakeModels(DataGenContext<Block, TwilightCakeBlock> ctx, RegistrateBlockstateProvider pvd, String name) {
		ModelFile[] slice = new ModelFile[7];
		slice[0] = new ModelFile.UncheckedModelFile(pvd.modLoc("block/" + name + "_cake"));
		for (int i = 1; i <= 6; i++) {
			slice[i] = new ModelFile.UncheckedModelFile(pvd.modLoc("block/" + name + "_cake_slice" + i));
		}
		pvd.getVariantBuilder(ctx.getEntry()).forAllStates(e ->
				ConfiguredModel.builder().modelFile(slice[e.getValue(BlockStateProperties.BITES)]).build());
	}

}
