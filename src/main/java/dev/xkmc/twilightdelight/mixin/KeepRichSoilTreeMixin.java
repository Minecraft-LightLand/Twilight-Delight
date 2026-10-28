package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public class KeepRichSoilTreeMixin {

	/**
	 * Trees replace the dirt below them with podzol when growing.
	 * This keeps Twilight Rich Soil from being turned into podzol that way.
	 */
	@Inject(at = @At("HEAD"), method = "setDirtAt", cancellable = true)
	private static void twilightdelight$setDirtAt$keepRichSoil(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, BlockPos pos, TreeConfiguration config, CallbackInfo ci) {
		if (level.isStateAtPosition(pos, state -> state.is(TDBlocks.TWILIGHT_RICH_SOIL.get()))) {
			ci.cancel();
		}
	}

}
