package dev.xkmc.twilightdelight.mixin;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@org.spongepowered.asm.mixin.Mixin(net.minecraft.world.item.Item.class)
public interface ItemAccessor {

	@Mutable
	@Accessor
	void setCraftingRemainingItem(Item craftingRemainingItem);
}
