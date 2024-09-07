package dev.xkmc.twilightdelight.init.registrate.delight;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Supplier;

public record EffectSupplier(Holder<MobEffect> eff, int time, int amp, float chance) {

	public MobEffectInstance get() {
		return new MobEffectInstance(eff, time, amp);
	}

}
