package dev.xkmc.twilightdelight.init;

import dev.xkmc.twilightdelight.content.item.food.ReusableDrinkItem;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = TwilightDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwilightDelightClient {

	@SubscribeEvent
	public static void registerItemProperties(FMLClientSetupEvent event) {
		ItemProperties.register(TDItems.CHAOS_INK_DRINK.get(),
				new ResourceLocation(TwilightDelight.MODID, "sips"),
				(stack, level, entity, seed) -> ReusableDrinkItem.getSipsProperty(stack));
	}

}
