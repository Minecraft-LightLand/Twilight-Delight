package dev.xkmc.twilightdelight.init.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 * Drops a random amount of an item within [min, max], untouched by looting or
 * the tool used. Meant for loot tables that should contain a handful of an
 * item regardless of how they were obtained.
 */
public class RandomCountModifier extends LootModifier {

	public static final Codec<RandomCountModifier> CODEC = RecordCodecBuilder.create(
			i -> codecStart(i).and(
					BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item)
			).and(
					Codec.INT.fieldOf("min").forGetter(e -> e.min)
			).and(
					Codec.INT.fieldOf("max").forGetter(e -> e.max)
			).apply(i, RandomCountModifier::new));

	public static final RegistryEntry<Codec<RandomCountModifier>> RANDOM_COUNT =
			TwilightDelight.REGISTRATE.simple("random_count",
					ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, () -> CODEC);

	private final Item item;
	private final int min;
	private final int max;

	protected RandomCountModifier(LootItemCondition[] conditions, Item item, int min, int max) {
		super(conditions);
		this.item = item;
		this.min = min;
		this.max = max;
	}

	public RandomCountModifier(Item item, int min, int max, LootItemCondition... conditions) {
		super(conditions);
		this.item = item;
		this.min = min;
		this.max = max;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> list, LootContext context) {
		list.add(new ItemStack(item, min + context.getRandom().nextInt(max - min + 1)));
		return list;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return RANDOM_COUNT.get();
	}

	public static void register() {

	}

}
