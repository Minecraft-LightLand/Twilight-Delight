package dev.xkmc.twilightdelight.init.data;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.mixin.AddItemModifierAccessor;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.loot.modifier.AddItemModifier;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ModTags;

public class GLMGen extends GlobalLootModifierProvider {

	public GLMGen(DataGenerator gen) {
		super(gen, TwilightDelight.MODID);
	}

	@Override
	protected void start() {
		scavenging(of(TFItems.EXPERIMENT_115), of(TFEntities.CARMINITE_GHASTGUARD), 0.5f, 0.1f, false);
		scavenging(of(TFItems.EXPERIMENT_115), of(TFEntities.CARMINITE_GHASTLING), 0.01f, 0.1f, false);
		scavenging(of(TFItems.EXPERIMENT_115), vanilla(EntityType.GHAST, "ghast"), 0.3f, 0.1f, false);
		scavenging(of(ModItems.HAM), of(TFEntities.BOAR), 0.67f, 0.1f, true);
	}

	private void scavenging(EntryHolder<? extends Item> item, EntryHolder<? extends EntityType<?>> type, float base, float loot, boolean nofire) {
		add("scavenging_" + item.id().getPath() + "_from_" + type.id().getPath(), nofire ?
				create(item.type(), 1,
						killedByKnife(),
						killTarget(type.type()),
						withChance(base, loot),
						noFire()) :
				create(item.type(), 1,
						killedByKnife(),
						killTarget(type.type()),
						withChance(base, loot)));
	}

	private static LootItemCondition killTarget(EntityType<?> type) {
		return LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.THIS,
				EntityPredicate.Builder.entity().entityType(
						EntityTypePredicate.of(type))).build();
	}

	private static LootItemCondition killedByKnife() {
		return LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.KILLER,
				EntityPredicate.Builder.entity().equipment(
						EntityEquipmentPredicate.Builder.equipment().mainhand(
										ItemPredicate.Builder.item().of(ModTags.KNIVES).build())
								.build()).build()).build();
	}

	private static LootItemCondition withChance(float base, float loot) {
		return LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(base, loot).build();
	}

	private static LootItemCondition noFire() {
		return LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
				EntityPredicate.Builder.entity().flags(
						EntityFlagsPredicate.Builder.flags().setOnFire(false)
								.build()).build()).build();
	}

	private static AddItemModifier create(Item item, int count, LootItemCondition... conditions) {
		var ans = AddItemModifierAccessor.create(conditions, item, count);
		assert ans != null;
		return ans;
	}

	private static <T> EntryHolder<T> vanilla(T obj, String id) {
		return new EntryHolder<>(obj, new ResourceLocation(id));
	}

	private static <T> EntryHolder<T> of(RegistryObject<T> obj) {
		return new EntryHolder<>(obj.get(), obj.getId());
	}

	private record EntryHolder<T>(T type, ResourceLocation id) {

	}

}