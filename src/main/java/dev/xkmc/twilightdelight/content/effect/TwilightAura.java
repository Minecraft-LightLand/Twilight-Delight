package dev.xkmc.twilightdelight.content.effect;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFDataMaps;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Once per second, randomly transforms one non-twilight mob in range into
 * its twilight counterpart, following Twilight Forest transformation powder
 * data. Transformed mobs are marked so they are never transformed again.
 */
public class TwilightAura extends RangeRenderEffect {

	public static final String TRANSFORMED_TAG = TwilightDelight.MODID + ".twilight_transformed";

	public TwilightAura() {
		super(MobEffectCategory.BENEFICIAL, 0x9932CC);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level().isClientSide()) return true;
		if (entity.tickCount % 20 != 0) return true;
		List<Mob> candidates = getEntitiesInRange(entity, Mob.class).stream()
				.filter(e -> e != entity && canTransform(e)).toList();
		if (candidates.isEmpty()) return true;
		applyEffect(candidates.get(entity.level().getRandom().nextInt(candidates.size())), amplifier);
		return true;
	}

	@Override
	protected void applyEffect(LivingEntity target, int amplifier) {
		if (target instanceof Mob mob && canTransform(mob)) {
			transform(mob);
		}
	}

	private static boolean canTransform(Mob mob) {
		if (mob.getPersistentData().getBoolean(TRANSFORMED_TAG)) return false;
		var id = BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType());
		if (id == null || id.getNamespace().equals(TwilightForestMod.ID)) return false;
		EntityType<?> target = getTransformTarget(mob);
		if (target == null) return false;
		var targetId = BuiltInRegistries.ENTITY_TYPE.getKey(target);
		return targetId != null && targetId.getNamespace().equals(TwilightForestMod.ID);
	}

	@Nullable
	private static EntityType<?> getTransformTarget(Mob mob) {
		var data = mob.getType().builtInRegistryHolder().getData(TFDataMaps.TRANSFORMATION_POWDER);
		return data == null ? null : data.result();
	}

	private static void transform(Mob old) {
		Level level = old.level();
		if (!(level instanceof ServerLevel server)) return;
		EntityType<?> type = getTransformTarget(old);
		if (type == null || !(type.create(server) instanceof Mob converted)) return;
		converted.copyPosition(old);
		EventHooks.finalizeMobSpawn(converted, server,
				server.getCurrentDifficultyAt(converted.blockPosition()),
				MobSpawnType.CONVERSION, null);
		CompoundTag tag = new CompoundTag();
		old.saveWithoutId(tag);
		converted.load(tag);
		converted.getPersistentData().putBoolean(TRANSFORMED_TAG, true);
		server.addFreshEntity(converted);
		old.discard();
		server.sendParticles(ParticleTypes.PORTAL,
				converted.getX(), converted.getY(0.5), converted.getZ(),
				24, 0.5, 0.5, 0.5, 0.1);
	}

	@Override
	protected ParticleOptions getParticle() {
		return ParticleTypes.PORTAL;
	}

}
