package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class RangeSearchEffect extends MobEffect {

	public static final int RANGE = 6;

	public static <T extends Entity> List<T> getEntitiesInRange(LivingEntity center, Class<T> cls) {
		Vec3 pos = center.position();
		return center.getLevel().getEntitiesOfClass(cls, center.getBoundingBox().inflate(RANGE), e -> true)
				.stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(pos)))
				.collect(Collectors.toList());
	}

	protected RangeSearchEffect(MobEffectCategory pCategory, int pColor) {
		super(pCategory, pColor);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.tickCount % 10 == 0) {
			searchEntities(entity, amplifier);
		}
	}

	protected void searchEntities(LivingEntity entity, int amplifier) {
		for (LivingEntity e : getEntitiesInRange(entity, LivingEntity.class)) {
			if (e == entity || !(e instanceof Enemy)) {
				continue;
			}
			applyEffect(e, amplifier);
		}
	}

	@Override
	public boolean isDurationEffectTick(int tick, int amplifier) {
		return true;
	}

	protected abstract void applyEffect(LivingEntity target, int amplifier);

}
