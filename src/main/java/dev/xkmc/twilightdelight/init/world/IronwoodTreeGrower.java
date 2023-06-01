package dev.xkmc.twilightdelight.init.world;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class IronwoodTreeGrower extends AbstractTreeGrower {
	public IronwoodTreeGrower() {
	}

	public Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource rand, boolean largeHive) {
		return TreeConfig.CF_IRONWOOD;
	}
}
