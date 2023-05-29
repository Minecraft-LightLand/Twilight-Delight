package dev.xkmc.twilightdelight.init.registrate.food;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Supplier;

public record EffectSupplier(Supplier<MobEffect> eff, int time, int amp, float chance) {

	public MobEffectInstance get() {
		return new MobEffectInstance(eff.get(), time, amp);
	}

}
