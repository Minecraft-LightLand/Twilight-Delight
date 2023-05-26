package dev.xkmc.twilight_delight;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TwilightDelight.MODID)
public class TwilightDelight {
	// Define mod id in a common place for everything to reference
	public static final String MODID = "twilight_delight";
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();

	public TwilightDelight() {
		ModConfig.init();
	}

}
