package dev.xkmc.twilightdelight.content.entity;

import dev.xkmc.twilightdelight.init.registrate.TDEntities;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import twilightforest.init.TFMobEffects;

/**
 * A fragile snowflake thrown like a snowball, except that it does not fall. On impact it
 * shatters, damaging and frosting every living entity within two blocks of the impact point.
 */
public class FragileSnowflakeEntity extends ThrowableItemProjectile {

	private static final float DAMAGE = 3.0F;
	private static final double RADIUS = 2.0D;
	private static final int FROSTY_TIME = 100;
	private static final int FROSTY_AMPLIFIER = 1;
	private static final byte SHATTER_EVENT = 3;

	public FragileSnowflakeEntity(EntityType<? extends FragileSnowflakeEntity> type, Level level) {
		super(type, level);
	}

	public FragileSnowflakeEntity(Level level, LivingEntity owner) {
		super(TDEntities.FRAGILE_SNOWFLAKE.get(), owner, level);
	}

	@Override
	protected Item getDefaultItem() {
		return TDItems.FRAGILE_SNOWFLAKE.get();
	}

	@Override
	protected float getGravity() {
		return 0.0F;
	}

	@Override
	protected void onHit(HitResult hit) {
		super.onHit(hit);
		if (level().isClientSide) {
			return;
		}
		Vec3 center = hit.getLocation();
		AABB area = new AABB(center, center).inflate(RADIUS);
		for (LivingEntity target : level().getEntitiesOfClass(LivingEntity.class, area)) {
			if (target == getOwner()) {
				continue;
			}
			// the shatter is meant to land regardless of when the target was last hurt
			target.invulnerableTime = 0;
			target.hurt(damageSources().thrown(this, getEffectSource()), DAMAGE);
			target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(),
					FROSTY_TIME, FROSTY_AMPLIFIER, false, true, true));
		}
		level().broadcastEntityEvent(this, SHATTER_EVENT);
		discard();
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == SHATTER_EVENT) {
			for (int i = 0; i < 48; i++) {
				double dx = (random.nextDouble() - 0.5D) * RADIUS * 2.0D;
				double dy = (random.nextDouble() - 0.5D) * RADIUS * 2.0D;
				double dz = (random.nextDouble() - 0.5D) * RADIUS * 2.0D;
				level().addParticle(ParticleTypes.SNOWFLAKE,
						getX() + dx, getY() + dy, getZ() + dz,
						dx * 0.1D, dy * 0.1D, dz * 0.1D);
			}
		} else {
			super.handleEntityEvent(id);
		}
	}

}
