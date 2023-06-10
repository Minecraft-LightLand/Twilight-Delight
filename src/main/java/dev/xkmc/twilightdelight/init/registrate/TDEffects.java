package dev.xkmc.twilightdelight.init.registrate;

import dev.xkmc.l2library.repack.registrate.builders.NoConfigBuilder;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.twilightdelight.content.effect.*;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public class TDEffects {

	public static final RegistryEntry<MobEffect> FIRE_RANGE;
	public static final RegistryEntry<MobEffect> POISON_RANGE;
	public static final RegistryEntry<MobEffect> FROZEN_RANGE;
	public static final RegistryEntry<MobEffect> TEMPORAL_SADNESS;
	public static final RegistryEntry<MobEffect> AURORA_GLOWING;

	static {
		FIRE_RANGE = genEffect("fire_range", FireRange::new);
		POISON_RANGE = genEffect("poison_range", PoisonRange::new);
		FROZEN_RANGE = genEffect("frozen_range", FrozenRange::new);
		TEMPORAL_SADNESS = genEffect("temporal_sadness", TemporalSadness::new);
		AURORA_GLOWING = genEffect("aurora_glowing", AuroraGlowing::new);
	}

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup) {
		return TwilightDelight.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(TwilightDelight.REGISTRATE,
						TwilightDelight.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId).register();
	}

	public static void register() {

	}

}
