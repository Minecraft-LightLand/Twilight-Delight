package dev.xkmc.twilightdelight.init.world;

import com.google.common.collect.ImmutableList;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import twilightforest.init.TFFeatures;
import twilightforest.world.components.feature.trees.treeplacers.BranchesConfig;
import twilightforest.world.components.feature.trees.treeplacers.BranchingTrunkPlacer;
import twilightforest.world.components.feature.trees.treeplacers.LeafSpheroidFoliagePlacer;
import twilightforest.world.registration.TreeDecorators;

public class TreeConfig {

	public static final TreeConfiguration TC_IRONWOOD = new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(TDBlocks.IRON_LOGS.get()),
			new BranchingTrunkPlacer(9, 1, 1, 3,
					new BranchesConfig(4, 0,
							8.0D, 2.0D,
							0.23D, 0.23D),
					false),
			BlockStateProvider.simple(TDBlocks.IRON_LEAVES.get()),
			new LeafSpheroidFoliagePlacer(4.5F, 2.25F, ConstantInt.of(0),
					1, 0, 0.45F, 36),
			new TwoLayersFeatureSize(4, 1, 1))
			.decorators(ImmutableList.of(TreeDecorators.LIVING_ROOTS)).ignoreVines().build();

	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CF_IRONWOOD =
			register("tree/ironwood_tree", TFFeatures.DARK_CANOPY_TREE.get(), TC_IRONWOOD);

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC config) {
		return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE,
				TwilightDelight.MODID + ":" + name, new ConfiguredFeature<>(feature, config));
	}

	public static void register() {

	}

}
