package dev.xkmc.twilightdelight.events;

import dev.xkmc.twilightdelight.compat.neapolitan.TDCakeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vectorwing.farmersdelight.common.tag.ModTags;

public class NeapolitanEventListeners {

	@SubscribeEvent
	public static void onItemUse(PlayerInteractEvent.RightClickBlock event) {
		BlockState state = event.getLevel().getBlockState(event.getPos());
		if (event.getItemStack().is(ModTags.KNIVES)) {
			if (state.getBlock() instanceof TDCakeBlock cake) {
				Level level = event.getLevel();
				BlockPos pos = event.getPos();
				int bites = state.getValue(CakeBlock.BITES);
				if (bites < 6) {
					level.setBlockAndUpdate(pos, state.setValue(CakeBlock.BITES, bites + 1));
				} else {
					level.removeBlock(pos, false);
				}
				Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), cake.cake.item.asStack());
				level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
				event.setCancellationResult(InteractionResult.SUCCESS);
				event.setCanceled(true);
			}
		}
	}

}
