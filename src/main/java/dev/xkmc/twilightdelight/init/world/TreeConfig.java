package dev.xkmc.twilightdelight.init.world;

import com.google.common.collect.ImmutableList;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
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
			BlockStateProvider.simple(TDBlocks.IRON_LOGS.getDefaultState()),
			new BranchingTrunkPlacer(9, 1, 1, 3,
					new BranchesConfig(BlockStateProvider.simple(TDBlocks.IRON_LOGS.getDefaultState()),
							4, 0,
							8.0D, 2.0D,
							0.23D, 0.23D),
					false),
			BlockStateProvider.simple(TDBlocks.IRON_LEAVES.get()),
			new LeafSpheroidFoliagePlacer(4.5F, 2.25F, ConstantInt.of(0),
					1, 0, 0.45F, 36),
			new TwoLayersFeatureSize(4, 1, 1))
			.decorators(ImmutableList.of(TreeDecorators.LIVING_ROOTS)).ignoreVines().build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> CF_IRONWOOD = ResourceKey.create(Registries.CONFIGURED_FEATURE,
			TwilightDelight.loc("tree/ironwood_tree"));

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		context.register(CF_IRONWOOD, new ConfiguredFeature<>(TFFeatures.DARK_CANOPY_TREE.get(), TC_IRONWOOD));
	}


}
