package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.twilightdelight.content.effect.*;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.world.effect.MobEffect;

public class TDEffects {

	public static final SimpleEntry<MobEffect> FIRE_RANGE;
	public static final SimpleEntry<MobEffect> POISON_RANGE;
	public static final SimpleEntry<MobEffect> FROZEN_RANGE;
	public static final SimpleEntry<MobEffect> TEMPORAL_SADNESS;
	public static final SimpleEntry<MobEffect> AURORA_GLOWING;
	public static final SimpleEntry<MobEffect> TWILIGHT_AURA;
	public static final SimpleEntry<MobEffect> LICH_CHARGE;

	static {
		FIRE_RANGE = genEffect("fire_range", FireRange::new, "Ignite enemies around you, and make you cannot be ignited.");
		POISON_RANGE = genEffect("poison_range", PoisonRange::new, "Poison enemies around you, and make you immune to Poison.");
		FROZEN_RANGE = genEffect("frozen_range", FrozenRange::new, "Freeze enemies around you, and make you immune to Frosty. Can also freeze items.");
		TEMPORAL_SADNESS = genEffect("temporal_sadness", TemporalSadness::new, "Reduce movement speed and attack damage.");
		AURORA_GLOWING = genEffect("aurora_glowing", AuroraGlowing::new, "Make yourself glow with rainbow color. All entities appear rainbow glowing to you");
		TWILIGHT_AURA = genEffect("twilight_aura", TwilightAura::new, "Randomly transform nearby ordinary mobs into twilight creatures.");
		LICH_CHARGE = genEffect("lich_charge", LichCharge::new, "Restores durability of lich scepters in hotbar and offhand.");
	}

	public static SimpleEntry<MobEffect> genEffect(String name, NonNullSupplier<MobEffect> sup, String desc) {
		return new SimpleEntry<>(TwilightDelight.REGISTRATE.effect(name, sup, desc).lang(MobEffect::getDescriptionId).register());
	}

	public static void register() {

	}

}
