package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.builders.NoConfigBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.twilightdelight.content.effect.FireRange;
import dev.xkmc.twilightdelight.content.effect.FrozenRange;
import dev.xkmc.twilightdelight.content.effect.PoisonRange;
import dev.xkmc.twilightdelight.content.effect.TemporalSadness;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Goulixiaoji
 */
public class TDEffects {

	public static final RegistryEntry<MobEffect> FIRE_RANGE;
	public static final RegistryEntry<MobEffect> POISON_RANGE;
	public static final RegistryEntry<MobEffect> FROZEN_RANGE;
	public static final RegistryEntry<MobEffect> TEMPORAL_SADNESS;

	static {
		FIRE_RANGE = genEffect("fire_range", FireRange::new);
		POISON_RANGE = genEffect("poison_range", PoisonRange::new);
		FROZEN_RANGE = genEffect("frozen_range", FrozenRange::new);
		TEMPORAL_SADNESS = genEffect("temporal_sadness", TemporalSadness::new);
	}

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup) {
		return TwilightDelight.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(TwilightDelight.REGISTRATE,
						TwilightDelight.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId).register();
	}

	public static void register() {

	}

}
