package dev.xkmc.twilightdelight.mixin;

import dev.xkmc.twilightdelight.content.effect.AuroraGlowing;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(at = @At("HEAD"), method = "getTeamColor", cancellable = true)
	public void twilightdelight$getTeamColor$renderShiny(CallbackInfoReturnable<Integer> cir) {
		Entity self = (Entity) (Object) this;
		if (self.level.isClientSide() && AuroraGlowing.shouldRender(self)) {
			cir.setReturnValue(AuroraGlowing.getColor(self));
		}
	}

	@Inject(at = @At("HEAD"), method = "isCurrentlyGlowing", cancellable = true)
	public void twilightdelight$isCurrentlyGlowing$renderShinny(CallbackInfoReturnable<Boolean> cir) {
		Entity self = (Entity) (Object) this;
		if (self.level.isClientSide()) {
			if (AuroraGlowing.shouldRender(self)) {
				cir.setReturnValue(true);
			}
		}
	}
}
