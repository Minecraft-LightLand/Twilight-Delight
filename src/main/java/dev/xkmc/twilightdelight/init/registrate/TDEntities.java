package dev.xkmc.twilightdelight.init.registrate;

import com.tterrag.registrate.util.entry.EntityEntry;
import dev.xkmc.twilightdelight.content.entity.FragileSnowflakeEntity;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

public class TDEntities {

	public static final EntityEntry<FragileSnowflakeEntity> FRAGILE_SNOWFLAKE;

	static {
		FRAGILE_SNOWFLAKE = TwilightDelight.REGISTRATE
				.entity("fragile_snowflake",
						(EntityType<FragileSnowflakeEntity> type, Level level) -> new FragileSnowflakeEntity(type, level),
						MobCategory.MISC)
				.properties(p -> p.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10))
				.renderer(() -> ThrownItemRenderer::new)
				.register();
	}

	public static void register() {

	}

}
