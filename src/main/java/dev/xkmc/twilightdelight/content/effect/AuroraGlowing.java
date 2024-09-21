package dev.xkmc.twilightdelight.content.effect;

import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2core.util.Proxy;
import dev.xkmc.twilightdelight.init.data.TDModConfig;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

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

	public static int getColor(Entity entity) {
		float pTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
		Level level = entity.level();
		Player player = Proxy.getPlayer();
		if (player == null) return -1;
		Vec3 pos = entity.getPosition(pTick).subtract(player.getPosition(pTick));
		double dis = Math.atan2(pos.x, pos.z) / Math.PI * 180 / 6;
		float tick = level.getGameTime() + pTick + (float) dis;
		int period = TDModConfig.CLIENT.auroraPeriod.get();
		return getColor(tick / period % 1, 1, 1);
	}

	public static boolean shouldRender(Entity self) {
		if (!(self instanceof ItemEntity) &&
				!(self instanceof Projectile) &&
				!(self instanceof LivingEntity) &&
				!(self instanceof PartEntity<?>))
			return false;
		Player player = Proxy.getPlayer();
		if (player != null && player.hasEffect(TDEffects.AURORA_GLOWING))
			return true;
		if (self instanceof LivingEntity le && L2LibReg.EFFECT.type().isProper(le)) {
			return L2LibReg.EFFECT.type().getExisting(le).map(x -> x.map.get(TDEffects.AURORA_GLOWING)).isPresent();
		}
		return false;
	}

	public AuroraGlowing() {
		super(MobEffectCategory.NEUTRAL, 9740385);
	}

}
