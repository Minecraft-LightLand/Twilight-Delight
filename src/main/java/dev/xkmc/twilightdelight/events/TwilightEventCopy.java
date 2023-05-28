package dev.xkmc.twilightdelight.events;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TwilightDelight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TwilightEventCopy {

	@SubscribeEvent
	public static void onKnightmetalToolDamage(LivingHurtEvent event) {
		LivingEntity target = event.getEntity();
		if (!target.getLevel().isClientSide()) {
			Entity var3 = event.getSource().getDirectEntity();
			if (var3 instanceof LivingEntity living) {
				ItemStack weapon = living.getMainHandItem();
				if (!weapon.isEmpty()) {
					if (target.getArmorValue() > 0 && (weapon.is(TDItems.KNIGHTMETAL_KNIFE.get()))) {
						if (target.getArmorCoverPercentage() > 0.0F) {
							int moreBonus = (int) (2.0F * target.getArmorCoverPercentage());
							event.setAmount(event.getAmount() + (float) moreBonus);
						} else {
							event.setAmount(event.getAmount() + 2.0F);
						}
						((ServerLevel) target.getLevel()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
					}
				}
			}
		}

	}

}
