package dev.xkmc.twilightdelight.content.effect;

import dev.xkmc.l2library.base.effects.EffectSyncEvents;
import dev.xkmc.twilightdelight.init.data.TDModConfig;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AuroraGlowing extends MobEffect {

	private static int getColor(float hue, float saturation, float brightness) {
		int r = 0, g = 0, b = 0;
		if (saturation == 0) {
			r = g = b = (int) (brightness * 255.0f + 0.5f);
		} else {
			float h = (hue - (float) Math.floor(hue)) * 6.0f;
			float f = h - (float) java.lang.Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - (saturation * (1.0f - f)));
			switch ((int) h) {
				case 0 -> {
					r = (int) (brightness * 255.0f + 0.5f);
					g = (int) (t * 255.0f + 0.5f);
					b = (int) (p * 255.0f + 0.5f);
				}
				case 1 -> {
					r = (int) (q * 255.0f + 0.5f);
					g = (int) (brightness * 255.0f + 0.5f);
					b = (int) (p * 255.0f + 0.5f);
				}
				case 2 -> {
					r = (int) (p * 255.0f + 0.5f);
					g = (int) (brightness * 255.0f + 0.5f);
					b = (int) (t * 255.0f + 0.5f);
				}
				case 3 -> {
					r = (int) (p * 255.0f + 0.5f);
					g = (int) (q * 255.0f + 0.5f);
					b = (int) (brightness * 255.0f + 0.5f);
				}
				case 4 -> {
					r = (int) (t * 255.0f + 0.5f);
					g = (int) (p * 255.0f + 0.5f);
					b = (int) (brightness * 255.0f + 0.5f);
				}
				case 5 -> {
					r = (int) (brightness * 255.0f + 0.5f);
					g = (int) (p * 255.0f + 0.5f);
					b = (int) (q * 255.0f + 0.5f);
				}
			}
		}
		return 0xff000000 | (r << 16) | (g << 8) | (b);
	}

	@OnlyIn(Dist.CLIENT)
	public static int getColor(Level level) {
		float tick = level.getGameTime() + Minecraft.getInstance().getPartialTick();
		int period = TDModConfig.CLIENT.auroraPeriod.get();
		return getColor(tick / period % 1, 1, 1);
	}

	@OnlyIn(Dist.CLIENT)
	public static boolean shouldRender(Entity self) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.hasEffect(TDEffects.AURORA_GLOWING.get()))
			return true;
		if (EffectSyncEvents.EFFECT_MAP.containsKey(self.getUUID())) {
			var map = EffectSyncEvents.EFFECT_MAP.get(self.getUUID());
			return map.containsKey(TDEffects.AURORA_GLOWING.get());
		}
		return false;
	}

	public AuroraGlowing() {
		super(MobEffectCategory.NEUTRAL, 9740385);
	}

}
