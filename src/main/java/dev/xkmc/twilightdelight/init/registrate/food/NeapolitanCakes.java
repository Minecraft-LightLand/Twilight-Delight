package dev.xkmc.twilightdelight.init.registrate.food;

import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import com.teamabnormals.neapolitan.common.block.FlavoredCandleCakeBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.Locale;

public enum NeapolitanCakes {
	AURORA(MaterialColor.COLOR_CYAN,
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 600, 2, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SLOWDOWN, 40, 0, 1)),
	TORCHBERRY(MaterialColor.COLOR_YELLOW,
			new EffectSupplier(TDEffects.FIRE_RANGE, 3600, 0, 1)),
	PHYTOCHEMICAL(MaterialColor.COLOR_GREEN,
			new EffectSupplier(TDEffects.POISON_RANGE, 3600, 2, 1)),
	GLACIER(MaterialColor.COLOR_LIGHT_BLUE,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 3600, 0, 1)),
	;

	private final String base;

	public final BlockEntry<FlavoredCakeBlock> block;
	public final BlockEntry<FlavoredCandleCakeBlock> candle;
	public final BlockEntry<FlavoredCandleCakeBlock>[] colored_candles;

	@SuppressWarnings({"unchecked", "rawtype", "unsafe", "deprecation"})
	NeapolitanCakes(MaterialColor color, EffectSupplier... effects) {
		base = name().toLowerCase(Locale.ROOT);
		var food = TDItems.simpleFood(FoodType.NONE, 1, 0.1f, effects);
		var props = BlockBehaviour.Properties.of(Material.CAKE, color).strength(0.5F).sound(SoundType.WOOL);
		block = TwilightDelight.REGISTRATE.block(base + "_cake", p -> new FlavoredCakeBlock(food, props))
				.blockstate(this::genCakeModels)
				.simpleItem().register();
		this.candle = TwilightDelight.REGISTRATE.block(base + "_candle_cake",
						p -> new FlavoredCandleCakeBlock(block::get, Blocks.CANDLE, props))
				.blockstate((ctx, pvd) -> genCandleModels(ctx, pvd, "candle"))
				.tag(BlockTags.CANDLE_CAKES)
				.register();
		colored_candles = new BlockEntry[DyeColor.values().length];
		for (DyeColor dye : DyeColor.values()) {
			String color_name = dye.getName();
			Block candle = Registry.BLOCK.get(new ResourceLocation(color_name + "_candle"));
			this.colored_candles[dye.ordinal()] = TwilightDelight.REGISTRATE.block(color_name + "_" + base + "_candle_cake",
							p -> new FlavoredCandleCakeBlock(block::get, candle, props))
					.blockstate((ctx, pvd) -> genCandleModels(ctx, pvd, color_name + "_candle"))
					.tag(BlockTags.CANDLE_CAKES)
					.register();
		}
	}

	private void genCandleModels(DataGenContext<Block, FlavoredCandleCakeBlock> ctx, RegistrateBlockstateProvider pvd, String candle) {
		BlockModelBuilder nolit = genCandleCakeModel(ctx, pvd, candle, false);
		BlockModelBuilder lit = genCandleCakeModel(ctx, pvd, candle, true);
		pvd.getVariantBuilder(ctx.getEntry()).forAllStates(e ->
				ConfiguredModel.builder().modelFile(e.getValue(BlockStateProperties.LIT) ? lit : nolit).build());
	}

	private void genCakeModels(DataGenContext<Block, FlavoredCakeBlock> ctx, RegistrateBlockstateProvider pvd) {
		BlockModelBuilder[] slice = new BlockModelBuilder[7];
		slice[0] = genCakeModel(pvd, "cake");
		for (int i = 1; i <= 6; i++) {
			slice[i] = genCakeModel(pvd, "cake_slice" + i);
		}
		pvd.getVariantBuilder(ctx.getEntry()).forAllStates(e ->
				ConfiguredModel.builder().modelFile(slice[e.getValue(BlockStateProperties.BITES)]).build());
	}

	private BlockModelBuilder genCakeModel(RegistrateBlockstateProvider pvd, String model) {
		return pvd.models().withExistingParent(base + "_" + model, new ResourceLocation("block/" + model))
				.texture("particle", pvd.modLoc("block/" + base + "_cake_side"))
				.texture("bottom", pvd.modLoc("block/" + base + "_cake_bottom"))
				.texture("top", pvd.modLoc("block/" + base + "_cake_top"))
				.texture("side", pvd.modLoc("block/" + base + "_cake_side"))
				.texture("inside", pvd.modLoc("block/" + base + "_cake_inner"));
	}

	private BlockModelBuilder genCandleCakeModel(DataGenContext<Block, FlavoredCandleCakeBlock> ctx, RegistrateBlockstateProvider pvd, String candle, boolean lit) {
		String name = ctx.getName();
		if (lit) {
			name += "_lit";
			candle += "_lit";
		}
		return pvd.models().withExistingParent(name, new ResourceLocation("block/template_cake_with_candle"))
				.texture("particle", pvd.modLoc("block/" + base + "_cake_side"))
				.texture("bottom", pvd.modLoc("block/" + base + "_cake_bottom"))
				.texture("top", pvd.modLoc("block/" + base + "_cake_top"))
				.texture("side", pvd.modLoc("block/" + base + "_cake_side"))
				.texture("candle", pvd.mcLoc("block/" + candle));
	}

	public static void register() {
	}

}
