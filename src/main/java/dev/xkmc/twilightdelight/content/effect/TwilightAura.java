package dev.xkmc.twilightdelight.content.effect;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFRecipes;
import twilightforest.item.recipe.TransformPowderRecipe;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Once per second, randomly transforms one non-twilight mob in range into
 * its twilight counterpart, following Twilight Forest transformation powder
 * recipes. Transformed mobs are marked so they are never transformed again.
 */
public class TwilightAura extends RangeRenderEffect {

	public static final String TRANSFORMED_TAG = TwilightDelight.MODID + ".twilight_transformed";

	public TwilightAura() {
		super(MobEffectCategory.BENEFICIAL, 0x9932CC);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level().isClientSide()) return;
		if (entity.tickCount % 20 != 0) return;
		List<Mob> candidates = getEntitiesInRange(entity, Mob.class).stream()
				.filter(e -> e != entity && canTransform(e)).toList();
		if (candidates.isEmpty()) return;
		applyEffect(candidates.get(entity.level().getRandom().nextInt(candidates.size())), amplifier);
	}

	@Override
	protected void applyEffect(LivingEntity target, int amplifier) {
		if (target instanceof Mob mob && canTransform(mob)) {
			transform(mob);
		}
	}

	private static boolean canTransform(Mob mob) {
		if (mob.getPersistentData().getBoolean(TRANSFORMED_TAG)) return false;
		var id = ForgeRegistries.ENTITY_TYPES.getKey(mob.getType());
		if (id == null || id.getNamespace().equals(TwilightForestMod.ID)) return false;
		EntityType<?> target = getTransformTarget(mob);
		if (target == null) return false;
		var targetId = ForgeRegistries.ENTITY_TYPES.getKey(target);
		return targetId != null && targetId.getNamespace().equals(TwilightForestMod.ID);
	}

	@Nullable
	private static EntityType<?> getTransformTarget(Mob mob) {
		var manager = mob.level().getRecipeManager();
		for (TransformPowderRecipe recipe : manager.getAllRecipesFor(TFRecipes.TRANSFORM_POWDER_RECIPE.get())) {
			if (recipe.input() == mob.getType()) return recipe.result();
			if (recipe.isReversible() && recipe.result() == mob.getType()) return recipe.input();
		}
		return null;
	}

	private static void transform(Mob old) {
		Level level = old.level();
		if (!(level instanceof ServerLevel server)) return;
		EntityType<?> type = getTransformTarget(old);
		if (type == null || !(type.create(server) instanceof Mob converted)) return;
		converted.copyPosition(old);
		ForgeEventFactory.onFinalizeSpawn(converted, server,
				server.getCurrentDifficultyAt(converted.blockPosition()),
				MobSpawnType.CONVERSION, null, null);
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
