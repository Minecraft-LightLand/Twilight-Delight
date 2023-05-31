package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class PoisonRange extends RangeSearchEffect {

	public PoisonRange() {
		super(MobEffectCategory.BENEFICIAL, -16751104);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.hasEffect(MobEffects.POISON)) {
			entity.removeEffect(MobEffects.POISON);
		}
		super.applyEffectTick(entity, amplifier);
	}

	@Override
	protected void applyEffect(LivingEntity target, int amplifier) {
		target.addEffect(new MobEffectInstance(MobEffects.POISON, 26));
	}

}
