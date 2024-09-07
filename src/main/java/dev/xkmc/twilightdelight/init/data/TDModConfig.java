package dev.xkmc.twilightdelight.init.data;

import dev.xkmc.l2core.util.ConfigInit;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.neoforged.neoforge.common.ModConfigSpec;

public class TDModConfig {

	public static class Client extends ConfigInit {

		public ModConfigSpec.IntValue auroraPeriod;

		Client(Builder builder) {
			markPlain();
			auroraPeriod = builder.text("Period for aurora color change")
					.defineInRange("auroraPeriod", 30, 0, 10000);
		}

	}

	public static class Server extends ConfigInit {

		public ModConfigSpec.IntValue effectRange;
		public ModConfigSpec.IntValue auroraRange;

		Server(Builder builder) {
			markPlain();
			effectRange = builder.text("Range for hostile effects, such as fire/frost/poison")
					.defineInRange("effectRange", 6, 0, 128);
			auroraRange = builder.text("Range for aurora range")
					.defineInRange("auroraRange", 24, 0, 128);
		}

	}

	public static final Client CLIENT = TwilightDelight.REGISTRATE.registerClient(Client::new);
	public static final Server SERVER = TwilightDelight.REGISTRATE.registerSynced(Server::new);

	public static void init() {
	}


}
