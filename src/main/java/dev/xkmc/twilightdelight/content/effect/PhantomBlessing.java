package dev.xkmc.twilightdelight.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * The Knight Phantom's blessing. It carries no gameplay effect of its own, it only
 * marks the player as blessed so the food tooltip and the HUD can show it.
 */
public class PhantomBlessing extends MobEffect {

	public PhantomBlessing() {
		super(MobEffectCategory.BENEFICIAL, 0x8A79C6);
	}

}
