package dev.xkmc.twilightdelight.init.data;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import dev.xkmc.twilightdelight.mixin.AddItemModifierAccessor;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.loot.modifier.AddItemModifier;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class GLMGen extends GlobalLootModifierProvider {

	public GLMGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, TwilightDelight.MODID);
	}

	@Override
	protected void start() {
		scavenging(of(TFItems.EXPERIMENT_115), of(TFEntities.CARMINITE_GHASTGUARD), 0.5f, 0.1f, false);
		scavenging(of(TFItems.EXPERIMENT_115), of(TFEntities.CARMINITE_GHASTLING), 0.01f, 0.1f, false);
		scavenging(of(TFItems.EXPERIMENT_115), vanilla(EntityType.GHAST, "ghast"), 0.3f, 0.1f, false);
		scavenging(of(ModItems.HAM.get(), FarmersDelight.MODID, "ham"), of(TFEntities.BOAR), 0.67f, 0.1f, true);
		lootDropMeat(TFItems.DIAMOND_MINOTAUR_AXE.get(), of(DelightFood.RAW_TOMAHAWK_SMEAK.item), of(TFEntities.MINOTAUR), 0.3f, false);
		lootDropMeat(TFItems.DIAMOND_MINOTAUR_AXE.get(), of(DelightFood.COOKED_TOMAHAWK_SMEAK.item), of(TFEntities.MINOTAUR), 0.3f, true);
		lootDropMeat(TFItems.DIAMOND_MINOTAUR_AXE.get(), of(DelightFood.RAW_TOMAHAWK_SMEAK.item), of(TFEntities.MINOSHROOM), 1f, false);
		lootDropMeat(TFItems.DIAMOND_MINOTAUR_AXE.get(), of(DelightFood.COOKED_TOMAHAWK_SMEAK.item), of(TFEntities.MINOSHROOM), 1f, true);
	}

	private void scavenging(EntryHolder<? extends Item> item, EntryHolder<? extends EntityType<?>> type, float base, float loot, boolean nofire) {
		lootDrop(item, type, nofire ?
				create(item.type(), 1,
						killedByKnife(),
						killTarget(type.type()),
						withChance(base, loot),
						fire(false)) :
				create(item.type(), 1,
						killedByKnife(),
						killTarget(type.type()),
						withChance(base, loot)));
	}

	private void lootDropMeat(Item tool, EntryHolder<? extends Item> item, EntryHolder<? extends EntityType<?>> type, float base, boolean fire) {
		lootDrop(item, type, create(item.type, 1,
				killedByItem(tool),
				killTarget(type.type),
				withChance(base, 0),
				fire(fire)));
	}

	private void lootDrop(EntryHolder<? extends Item> item, EntryHolder<? extends EntityType<?>> type, AddItemModifier loot) {
		add("scavenging_" + item.id().getPath() + "_from_" + type.id().getPath(), loot);
	}

	private static LootItemCondition killTarget(EntityType<?> type) {
		return LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.THIS,
				EntityPredicate.Builder.entity().entityType(
						EntityTypePredicate.of(type))).build();
	}

	private static LootItemCondition killedByKnife() {
		return LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.ATTACKER,
				EntityPredicate.Builder.entity().equipment(
						EntityEquipmentPredicate.Builder.equipment().mainhand(
										ItemPredicate.Builder.item().of(ModTags.KNIVES))
								.build()).build()).build();
	}

	private static LootItemCondition killedByItem(Item item) {
		return LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.ATTACKER,
				EntityPredicate.Builder.entity().equipment(
						EntityEquipmentPredicate.Builder.equipment().mainhand(
										ItemPredicate.Builder.item().of(item))
								.build()).build()).build();
	}

	private LootItemCondition withChance(float base, float loot) {
		return LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, base, loot).build();
	}

	private static LootItemCondition fire(boolean fire) {
		return LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
				EntityPredicate.Builder.entity().flags(
						EntityFlagsPredicate.Builder.flags().setOnFire(fire)).build()).build();
	}

	private static AddItemModifier create(Item item, int count, LootItemCondition... conditions) {
		var ans = AddItemModifierAccessor.create(conditions, item, count);
		assert ans != null;
		return ans;
	}

	private static <T> EntryHolder<T> vanilla(T obj, String id) {
		return new EntryHolder<>(obj, ResourceLocation.withDefaultNamespace(id));
	}

	private static <T> EntryHolder<T> of(DeferredHolder<T, ?> obj) {
		return new EntryHolder<>(obj.get(), obj.getId());
	}

	private static <T> EntryHolder<T> of(T obj, String modid, String id) {
		return new EntryHolder<>(obj, ResourceLocation.fromNamespaceAndPath(modid, id));
	}

	private record EntryHolder<T>(T type, ResourceLocation id) {

	}

}
