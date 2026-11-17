package dev.xkmc.twilightdelight.content.item.food;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

/**
 * Bowl food made from oreberries. It is eaten with the sound of placing a metal block
 * instead of the usual eating sound.
 */
public class MetalFoodItem extends TDFoodItem {

	public MetalFoodItem(Properties props) {
		super(props);
	}

	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.NETHERITE_BLOCK_PLACE;
	}

}
