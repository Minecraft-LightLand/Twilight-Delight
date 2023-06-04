package dev.xkmc.twilightdelight.mixin;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

	public ItemEntityMixin(EntityType<?> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Inject(at = @At("HEAD"), method = "tick")
	public void twilightdelight$tick$frozen(CallbackInfo ci) {
		if (getType() == EntityType.ITEM && level.isClientSide()) {
			if (getTicksFrozen() > 0 && level.random.nextBoolean()) {
				double x = Mth.randomBetween(level.getRandom(), -1, 1) / 12;
				double z = Mth.randomBetween(level.getRandom(), -1, 1) / 12;
				level.addParticle(ParticleTypes.SNOWFLAKE, getX(), getY() + getBbHeight() / 2, getZ(), x, 0.05f, z);
			}
		}
	}

}
