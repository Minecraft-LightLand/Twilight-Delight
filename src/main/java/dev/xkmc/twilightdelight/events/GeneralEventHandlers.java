package dev.xkmc.twilightdelight.events;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.data.ExtraLootGen;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.block.Experiment115Block;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFMobEffects;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mod.EventBusSubscriber(modid = TwilightDelight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GeneralEventHandlers {

	private static ObjectArrayList<ItemStack> getLoot(ServerLevel sl, ResourceLocation id, BlockState state, PlayerInteractEvent.RightClickBlock event) {
		var ctx = new LootParams.Builder(sl)
				.withParameter(LootContextParams.BLOCK_STATE, state)
				.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(event.getPos()))
				.withParameter(LootContextParams.TOOL, event.getItemStack())
				.withParameter(LootContextParams.THIS_ENTITY, event.getEntity())
				.create(LootContextParamSets.BLOCK);
		return sl.getServer().getLootData().getLootTable(id)
				.getRandomItems(ctx);
	}

	@SubscribeEvent
	public static void onItemUse(PlayerInteractEvent.RightClickBlock event) {
		BlockState state = event.getLevel().getBlockState(event.getPos());
		if (state.is(TFBlocks.EXPERIMENT_115.get())) {
			if (event.getItemStack().is(Items.REDSTONE)) {
				if (!state.getValue(Experiment115Block.REGENERATE)) {
					if (state.getValue(Experiment115Block.BITES_TAKEN) > 0) {
						event.setCanceled(true);
						event.setCancellationResult(InteractionResult.FAIL);
					}
				}
			}
		}
		if (event.getItemStack().is(ModTags.KNIVES)) {
			if (state.is(TFBlocks.EXPERIMENT_115.get())) {
				if (event.getLevel() instanceof ServerLevel sl) {
					int i = state.getValue(Experiment115Block.BITES_TAKEN);
					if (i < 7) {
						event.getLevel().setBlock(event.getPos(), state.setValue(Experiment115Block.BITES_TAKEN, i + 1), 3);
					} else {
						event.getLevel().removeBlock(event.getPos(), false);
					}
					var loot = getLoot(sl, ExtraLootGen.SCRAP_115, state, event);
					for (var e : loot) {
						event.getEntity().getInventory().placeItemBackInInventory(e);
					}
					event.getItemStack().hurtAndBreak(1, event.getEntity(),
							e -> e.broadcastBreakEvent(event.getHand()));
				}
				event.getLevel().playSound(event.getEntity(), event.getPos(),
						SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
				event.setCanceled(true);
				event.setCancellationResult(InteractionResult.SUCCESS);
			}
		}
	}

	@SubscribeEvent
	public static void onPotionTest(MobEffectEvent.Applicable event) {
		if (event.getEffectInstance().getEffect() == MobEffects.POISON && event.getEntity().hasEffect(TDEffects.POISON_RANGE.get())) {
			event.setResult(Event.Result.DENY);
		}
		if (event.getEffectInstance().getEffect() == TFMobEffects.FROSTY.get() && event.getEntity().hasEffect(TDEffects.FROZEN_RANGE.get())) {
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public static void onKnightmetalToolDamage(LivingHurtEvent event) {
		LivingEntity target = event.getEntity();
		if (!target.level().isClientSide()) {
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
						((ServerLevel) target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
					}
				}
			}
		}

	}

}
