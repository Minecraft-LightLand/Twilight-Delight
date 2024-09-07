package dev.xkmc.twilightdelight.content.effect;

import dev.xkmc.twilightdelight.init.registrate.TDRecipes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import twilightforest.init.TFMobEffects;

public class FrozenRange extends RangeRenderEffect {

	public FrozenRange() {
		super(MobEffectCategory.BENEFICIAL, -16724788);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.hasEffect(TFMobEffects.FROSTY)) {
			entity.removeEffect(TFMobEffects.FROSTY);
		}
		return super.applyEffectTick(entity, amplifier);
	}

	@Override
	protected void searchEntities(LivingEntity entity, int amplifier) {
		super.searchEntities(entity, amplifier);
		for (ItemEntity e : getEntitiesInRange(entity, ItemEntity.class)) {
			var inv = new SingleRecipeInput(e.getItem());
			var opt = entity.level().getRecipeManager().getRecipeFor(TDRecipes.WORLD_RECIPE.get(), inv, entity.level());
			if (opt.isPresent()) {
				e.setTicksFrozen(e.getTicksFrozen() + 10);
				if (e.isFullyFrozen()) {
					e.setItem(opt.get().value().assemble(inv, entity.level().registryAccess()));
					e.setTicksFrozen(0);
				}
			}
		}
	}

	@Override
	protected void applyEffect(LivingEntity target, int amplifier) {
		target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY, 21, 4));
	}

	@Override
	protected ParticleOptions getParticle() {
		return ParticleTypes.SNOWFLAKE;
	}

}
