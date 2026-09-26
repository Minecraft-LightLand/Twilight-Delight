package dev.xkmc.twilightdelight.init.registrate;

import com.mojang.serialization.Codec;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TDDataComponents {

	public static final DeferredRegister<DataComponentType<?>> REG =
			DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, TwilightDelight.MODID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SIPS =
			REG.register("sips", () -> DataComponentType.<Integer>builder()
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.VAR_INT)
					.build());

	public static void register(IEventBus bus) {
		REG.register(bus);
	}

}
