package dev.xkmc.twilightdelight.events;

import dev.xkmc.twilightdelight.content.effect.PhantomBlessing;
import dev.xkmc.twilightdelight.content.item.tool.AntlerBowItem;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.data.ExtraLootGen;
import dev.xkmc.twilightdelight.init.data.TagRef;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDBlocks;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.block.Experiment115Block;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFMobEffects;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mod.EventBusSubscriber(modid = TwilightDelight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GeneralEventHandlers {

	/**
	 * The chest of the Dark Tower boss, where the Knight Phantom keeps its loot.
	 */
	public static final ResourceLocation STRONGHOLD_BOSS_CHEST =
			new ResourceLocation("twilightforest", "chests/stronghold_boss");

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
		// bone meal on uberous soil turns it into twilight rich soil
		if (event.getItemStack().is(TDItems.WITCHCRAFT_BONE_MEAL.get()) && state.is(TFBlocks.UBEROUS_SOIL.get())) {
			Level level = event.getLevel();
			if (level instanceof ServerLevel sl) {
				sl.setBlockAndUpdate(event.getPos(), TDBlocks.TWILIGHT_RICH_SOIL.get().defaultBlockState());
				sl.playSound(null, event.getPos(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
				if (!event.getEntity().getAbilities().instabuild) {
					event.getItemStack().shrink(1);
				}
			}
			event.setCanceled(true);
			event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
		} else {
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
			if (event.getItemStack().is(TagRef.TOOLS_KNIVES)) {
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
	}

	/**
	 * The antler bow is light enough to draw twice as fast. Vanilla reads the draw straight off
	 * the use countdown, so burning one extra tick of it per tick doubles the rate: full power,
	 * and the fully pulled model, arrive in 10 ticks instead of 20. The event fires on both
	 * sides, so the pull animation finishes at the same moment the shot does.
	 */
	@SubscribeEvent
	public static void onItemUseTick(LivingEntityUseItemEvent.Tick event) {
		if (event.getItem().getItem() instanceof AntlerBowItem) {
			event.setDuration(event.getDuration() - 1);
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

	/**
	 * The armor of the blessed one takes over a part of the damage.
	 */
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onLivingHurt(LivingDamageEvent event) {
		LivingEntity target = event.getEntity();
		if (!target.level().isClientSide()) {
			event.setAmount(PhantomBlessing.shareDamage(target, event.getSource(), event.getAmount()));
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

	@SubscribeEvent
	public static void fieryToolSetFire(LivingAttackEvent event) {
		Entity var2 = event.getSource().getEntity();
		if (var2 instanceof LivingEntity living) {
			var item = living.getMainHandItem();
		if ((item.is(TDItems.FIERY_KNIFE.get()) || item.is(TDItems.TEARDROP_SWORD.get())) && !event.getEntity().fireImmune()) {
			event.getEntity().setSecondsOnFire(1);
		}
		if (item.is(TDItems.ICE_KNIFE.get())) {
			event.getEntity().clearFire();
		}
		}

	}

	/**
	 * The chest of the Dark Tower boss holds what is left of the Knight Phantom,
	 * a handful of phantom bones among the phantom armour.
	 */
	@SubscribeEvent
	public static void onLootTableLoad(LootTableLoadEvent event) {
		if (!STRONGHOLD_BOSS_CHEST.equals(event.getName())) return;
		event.getTable().addPool(LootPool.lootPool()
				.setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(TDItems.PHANTOM_BONE.get())
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 9.0F))))
				.build());
	}

	public static boolean skipLootTable(ResourceLocation name) {
		if (name.getNamespace().equals(TwilightDelight.MODID)) {
			if (name.getPath().startsWith("blocks/")) {
				if (!ForgeRegistries.BLOCKS.containsKey(name.withPath(e -> e.substring(7)))) {
					return true;
				}
			}
		}
		return false;
	}

}
