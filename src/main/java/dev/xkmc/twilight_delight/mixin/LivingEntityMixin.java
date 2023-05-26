package dev.xkmc.twilight_delight.mixin;

import dev.xkmc.twilight_delight.MagicReductionCalculation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(at = @At("HEAD"), method = "getDamageAfterMagicAbsorb", cancellable = true)
	public void magic_protection_overhaul$getDamageAfterMagicAbsorb$overhaulMagicAbsorb(
			DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(MagicReductionCalculation.calculateMagicAbsorb((LivingEntity) (Object) this, source, amount));
	}

}
