package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class RangeSearchEffect extends MobEffect {

	public static List<LivingEntity> getEntitiesInRange(double size, LivingEntity center) {
		Vec3 pos = center.position();
		return center.getLevel().getEntitiesOfClass(LivingEntity.class, center.getBoundingBox().inflate(size), e -> true)
				.stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(pos)))
				.collect(Collectors.toList());
	}

	protected RangeSearchEffect(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		entity.clearFire();
		if (entity.tickCount % 10 == 0) {
			for (LivingEntity e : getEntitiesInRange(6, entity)) {
				if (e == entity || !(e instanceof Enemy)) {
					continue;
				}
				applyEffect(e, amplifier);
			}
		}
	}

	@Override
	public boolean isDurationEffectTick(int tick, int amplifier) {
		return true;
	}

	protected abstract void applyEffect(LivingEntity target, int amplifier);

}
