package dev.xkmc.twilightdelight.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class TDModConfig {

	public static class Client {

		public ForgeConfigSpec.IntValue auroraPeriod;

		Client(ForgeConfigSpec.Builder builder) {
			auroraPeriod = builder.comment("Period for aurora color change")
					.defineInRange("auroraPeriod", 30, 0, 10000);
		}

	}

	public static class Common {

		public ForgeConfigSpec.IntValue effectRange;
		public ForgeConfigSpec.IntValue auroraRange;
		public ForgeConfigSpec.BooleanValue genAllModFoodValues;

		Common(ForgeConfigSpec.Builder builder) {
			effectRange = builder.comment("Range for hostile effects, such as fire/frost/poison")
					.defineInRange("effectRange", 6, 0, 128);
			auroraRange = builder.comment("Range for aurora range")
					.defineInRange("auroraRange", 24, 0, 128);
			genAllModFoodValues = builder.comment("Generate food effect tooltip for all modded food")
					.define("genAllModFoodValues", true);
		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
	}


}
