package dev.xkmc.twilightdelight.content.effect;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TemporalSadness extends MobEffect {

	private static final ResourceLocation NAME_ATK = TwilightDelight.loc("temporal_sadness_atk");
	private static final ResourceLocation NAME_SPE = TwilightDelight.loc("temporal_sadness_speed");

	public TemporalSadness() {
		super(MobEffectCategory.HARMFUL, -1);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, NAME_ATK, -10, AttributeModifier.Operation.ADD_VALUE);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, NAME_SPE, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		entity.setSprinting(false);
		if (entity.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.FALLING_WATER, entity.getX(), entity.getY(), entity.getZ(), 5, 1, 1, 1, 1);
		}
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

}
