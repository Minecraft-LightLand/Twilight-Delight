package dev.xkmc.twilightdelight.init;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(TwilightDelight.MODID)
public class TwilightDelight {

	public static final String MODID = "twilightdelight";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(MODID);

	public TwilightDelight() {
		TDItems.register();
		TDEffects.register();
		ModConfig.init();
	}

	public static void gatherData(GatherDataEvent event){

	}

}
