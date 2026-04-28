package dev.xkmc.twilightdelight.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;

@Mixin(AbstractStoveBlockEntity.class)
public interface AbstractStoveBlockEntityAccessor {

	@Accessor
	int[] getCookingTime();

}
