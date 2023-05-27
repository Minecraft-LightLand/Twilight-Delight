package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import twilightforest.init.TFMobEffects;

public class FrozenRange extends RangeSearchEffect {

	public FrozenRange() {
		super(MobEffectCategory.BENEFICIAL, -16724788);
	}

	@Override
	protected void applyEffect(LivingEntity target, int amplifier) {
		target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 21, 4));
	}

}
