package dev.xkmc.twilightdelight.init.registrate.delight;

import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.Locale;

public enum DelightPie {
	TORCHBERRY_PIE(new EffectSupplier(TDEffects.FIRE_RANGE, 300, 0, 1)),
	AURORA_PIE(
			new EffectSupplier(TDEffects.AURORA_GLOWING, 300, 0, 1),
			new EffectSupplier(MobEffects.MOVEMENT_SPEED, 300, 2, 1),
			new EffectSupplier(MobEffects.JUMP, 300, 1, 1));

	public static void register() {

	}

	public final BlockEntry<PieBlock> block;
	public final ItemEntry<TDFoodItem> slice;


	DelightPie(EffectSupplier... effects) {
		String name = name().toLowerCase(Locale.ROOT);
		FoodProperties food = TDItems.simpleFood(DelightFoodType.FAST, 3, 0.3f, effects);
		slice = TwilightDelight.REGISTRATE.item(name + "_slice", p -> new TDFoodItem(p.food(food)))
				.defaultModel().defaultLang().register();
		block = TwilightDelight.REGISTRATE.block(name,
						p -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), slice::get))
				.blockstate((ctx, pvd) -> {
					ModelFile[] models = new ModelFile[4];
					for (int i = 0; i < 4; i++) {
						models[i] = genCakeModel(pvd, i == 0 ? "" : "_slice" + i);
					}
					pvd.horizontalBlock(ctx.getEntry(), state -> models[state.getValue(PieBlock.BITES)]);
				}).loot((a, b) -> a.dropOther(b, slice)).item().model((ctx, pvd) -> pvd.generated(ctx)).build().defaultLang().register();
	}

	private BlockModelBuilder genCakeModel(RegistrateBlockstateProvider pvd, String model) {
		String base = name().toLowerCase(Locale.ROOT);
		return pvd.models().getBuilder(base + model).parent(new ModelFile.UncheckedModelFile(
						ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie" + model)))
				.texture("particle", pvd.modLoc("block/" + base + "_top"))
				.texture("top", pvd.modLoc("block/" + base + "_top"))
				.texture("bottom", pvd.modLoc("block/pie_bottom"))
				.texture("side", pvd.modLoc("block/pie_side"))
				.texture("inner", pvd.modLoc("block/" + base + "_inner"));
	}


}
