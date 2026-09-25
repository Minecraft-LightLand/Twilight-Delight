package dev.xkmc.twilightdelight.init.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2core.init.reg.simple.CdcReg;
import dev.xkmc.l2core.init.reg.simple.CdcVal;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.data.TagRef;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 * Drops a random amount of an item within [min, max]. Extra looting levels
 * grant extra rolls, taking the best roll, so higher looting shifts the
 * drop toward max.
 */
public class NagaMeatModifier extends LootModifier {

	public static final MapCodec<NagaMeatModifier> CODEC = RecordCodecBuilder.mapCodec(
			i -> codecStart(i).and(
					BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item)
			).and(
					Codec.INT.fieldOf("min").forGetter(e -> e.min)
			).and(
					Codec.INT.fieldOf("max").forGetter(e -> e.max)
			).apply(i, NagaMeatModifier::new));

	private static final CdcReg<IGlobalLootModifier> CR =
			CdcReg.of(TwilightDelight.REG, NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

	public static final CdcVal<NagaMeatModifier> NAGA_MEAT = CR.reg("naga_meat", CODEC);

	private final Item item;
	private final int min;
	private final int max;

	protected NagaMeatModifier(LootItemCondition[] conditions, Item item, int min, int max) {
		super(conditions);
		this.item = item;
		this.min = min;
		this.max = max;
	}

	public NagaMeatModifier(Item item, int min, int max, LootItemCondition... conditions) {
		super(conditions);
		this.item = item;
		this.min = min;
		this.max = max;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> list, LootContext context) {
		int looting = 0;
		if (context.getParamOrNull(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity attacker) {
			var enchant = context.getLevel().registryAccess()
					.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING);
			looting = EnchantmentHelper.getEnchantmentLevel(enchant, attacker);
		}
		int best = min;
		for (int i = 0; i < looting + 1; i++) {
			best = Math.max(best, min + context.getRandom().nextInt(max - min + 1));
		}
		if (killedByKnife(context)) {
			best = Math.round(best * 1.5f);
		}
		list.add(new ItemStack(item, best));
		return list;
	}

	private static boolean killedByKnife(LootContext context) {
		if (context.getParamOrNull(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity attacker) {
			return attacker.getMainHandItem().is(TagRef.KNIFE);
		}
		return false;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return NAGA_MEAT.get();
	}

	public static void register() {

	}

}
