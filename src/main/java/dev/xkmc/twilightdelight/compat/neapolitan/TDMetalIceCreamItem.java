package dev.xkmc.twilightdelight.compat.neapolitan;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

/**
 * Ice cream churned from raw oreberries. It is eaten with the sound of placing a copper block
 * instead of the usual eating sound.
 */
public class TDMetalIceCreamItem extends TDIceCreamItem {

	public TDMetalIceCreamItem(Properties properties) {
		super(properties);
	}

	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.NETHERITE_BLOCK_PLACE;
	}

}
