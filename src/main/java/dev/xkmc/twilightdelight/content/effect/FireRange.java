package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FireRange extends RangeRenderEffect {

	public FireRange() {
		super(MobEffectCategory.BENEFICIAL, -39424);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		entity.clearFire();
		return super.applyEffectTick(entity, amplifier);
	}

	@Override
	protected void applyEffect(LivingEntity target, int amplifier) {
		target.setRemainingFireTicks((amplifier + 2) * 20);
	}

	@Override
	protected ParticleOptions getParticle() {
		return ParticleTypes.FLAME;
	}
}
