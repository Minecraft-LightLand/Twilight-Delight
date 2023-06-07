package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class TemporalSadness extends MobEffect {

	private static final String NAME_ATK = "temporalSadness_atk";
	private static final String NAME_SPE = "temporalSadness_speed";

	private static final UUID ID_ATK = UUID.nameUUIDFromBytes(NAME_ATK.getBytes(StandardCharsets.UTF_8));
	private static final UUID ID_SPE = UUID.nameUUIDFromBytes(NAME_SPE.getBytes(StandardCharsets.UTF_8));

	public TemporalSadness() {
		super(MobEffectCategory.HARMFUL, -1);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, ID_ATK.toString(), -10, AttributeModifier.Operation.ADDITION);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID_SPE.toString(), -0.5, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		entity.setSprinting(false);
		if (entity.level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.FALLING_WATER, entity.getX(), entity.getY(), entity.getZ(), 5, 1, 1, 1, 1);
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
