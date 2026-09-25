package dev.xkmc.twilightdelight.content.item.food;

import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;

/**
 * A drinkable item with multiple sips. Each sip applies a small meal and
 * consumes one sip stored in NBT; the model changes with remaining sips
 * and the last sip returns a glass bottle.
 */
public class ReusableDrinkItem extends Item {

	public static final int MAX_SIPS = 4;
	public static final String SIPS_TAG = "Sips";

	private final int nutrition;
	private final float saturation;
	private final List<EffectSupplier> effects;
	private final FoodProperties food;

	public ReusableDrinkItem(Properties props, int nutrition, float saturation, List<EffectSupplier> effects) {
		super(props.stacksTo(1));
		this.nutrition = nutrition;
		this.saturation = saturation;
		this.effects = effects;
		FoodProperties.Builder builder = new FoodProperties.Builder()
				.nutrition(nutrition).saturationMod(saturation);
		for (var e : effects) {
			builder = builder.effect(e::get, e.chance());
		}
		this.food = builder.build();
	}

	public static int getSips(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag == null || !tag.contains(SIPS_TAG, Tag.TAG_INT)) return MAX_SIPS;
		return Mth.clamp(tag.getInt(SIPS_TAG), 0, MAX_SIPS);
	}

	public static void setSips(ItemStack stack, int sips) {
		stack.getOrCreateTag().putInt(SIPS_TAG, Mth.clamp(sips, 0, MAX_SIPS));
	}

	public static float getSipsProperty(ItemStack stack) {
		return (MAX_SIPS - getSips(stack)) / (float) MAX_SIPS;
	}

	@Override
	public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
		return food;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity eater) {
		if (eater instanceof Player player) {
			player.getFoodData().eat(nutrition, saturation);
			for (var e : effects) {
				if (level.getRandom().nextFloat() < e.chance()) {
					player.addEffect(e.get());
				}
			}
		}
		int sips = getSips(stack) - 1;
		if (sips <= 0) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		setSips(stack, sips);
		return stack;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		if (Configuration.ENABLE_FOOD_EFFECT_TOOLTIP.get())
			TDFoodItem.getFoodEffects(stack, list);
	}

}
