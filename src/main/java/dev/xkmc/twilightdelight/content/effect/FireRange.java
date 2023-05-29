package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FireRange extends RangeSearchEffect {

	public FireRange() {
		super(MobEffectCategory.BENEFICIAL, -39424);
	}

	@Override
	protected void applyEffect(LivingEntity target, int amplifier) {
		target.setSecondsOnFire(amplifier + 5);
	}

}