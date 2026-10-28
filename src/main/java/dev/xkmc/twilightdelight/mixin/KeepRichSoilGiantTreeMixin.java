package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class KeepRichSoilGiantTreeMixin {

	/**
	 * Giant spruce growth uses {@link Feature#isGrassOrDirt} to decide what to replace with podzol,
	 * so Twilight Rich Soil must not count as grass or dirt there.
	 */
	@Inject(at = @At("HEAD"), method = "isGrassOrDirt", cancellable = true)
	private static void twilightdelight$isGrassOrDirt$keepRichSoil(LevelSimulatedReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (level.isStateAtPosition(pos, state -> state.is(TDBlocks.TWILIGHT_RICH_SOIL.get()))) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

}
