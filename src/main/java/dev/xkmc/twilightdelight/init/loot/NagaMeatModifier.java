package dev.xkmc.twilightdelight.init.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.data.TagRef;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 * Drops a random amount of an item within [min, max]. Extra looting levels
 * grant extra rolls, taking the best roll, so higher looting shifts the
 * drop toward max.
 */
public class NagaMeatModifier extends LootModifier {

	public static final Codec<NagaMeatModifier> CODEC = RecordCodecBuilder.create(
			i -> codecStart(i).and(
					BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item)
			).and(
					Codec.INT.fieldOf("min").forGetter(e -> e.min)
			).and(
					Codec.INT.fieldOf("max").forGetter(e -> e.max)
			).apply(i, NagaMeatModifier::new));

	public static final RegistryEntry<Codec<NagaMeatModifier>> NAGA_MEAT =
			TwilightDelight.REGISTRATE.simple("naga_meat",
					ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> CODEC);

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
		int best = min;
		for (int i = 0; i < context.getLootingModifier() + 1; i++) {
			best = Math.max(best, min + context.getRandom().nextInt(max - min + 1));
		}
		if (killedByKnife(context)) {
			best = Math.round(best * 1.5f);
		}
		list.add(new ItemStack(item, best));
		return list;
	}

	private static boolean killedByKnife(LootContext context) {
		if (context.getParamOrNull(LootContextParams.KILLER_ENTITY) instanceof LivingEntity killer) {
			return killer.getMainHandItem().is(TagRef.TOOLS_KNIVES);
		}
		return false;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return NAGA_MEAT.get();
	}

	public static void register() {

	}

}
