package dev.xkmc.twilightdelight.content.item.food;

import dev.xkmc.twilightdelight.init.registrate.TDDataComponents;
import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
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
import twilightforest.init.TFItems;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;

/**
 * A drinkable item with multiple sips. Each sip applies a small meal and
 * consumes one sip stored in a data component; the model changes with
 * remaining sips and the last sip returns a glass bottle.
 */
public class ReusableDrinkItem extends Item {

	public static final int MAX_SIPS = 4;

	private final int nutrition;
	private final float saturation;
	private final List<EffectSupplier> effects;

	public ReusableDrinkItem(Properties props, int nutrition, float saturation, List<EffectSupplier> effects) {
		super(withFood(props, nutrition, saturation, effects).stacksTo(1));
		this.nutrition = nutrition;
		this.saturation = saturation;
		this.effects = effects;
	}

	public static int getSips(ItemStack stack) {
		return Mth.clamp(stack.getOrDefault(TDDataComponents.SIPS.get(), MAX_SIPS), 0, MAX_SIPS);
	}

	public static void setSips(ItemStack stack, int sips) {
		stack.set(TDDataComponents.SIPS.get(), Mth.clamp(sips, 0, MAX_SIPS));
	}

	public static float getSipsProperty(ItemStack stack) {
		return (MAX_SIPS - getSips(stack)) / (float) MAX_SIPS;
	}

	private static Properties withFood(Properties props, int nutrition, float saturation, List<EffectSupplier> effects) {
		FoodProperties.Builder builder = new FoodProperties.Builder()
				.nutrition(nutrition).saturationModifier(saturation);
		for (var e : effects) {
			builder = builder.effect(e::get, e.chance());
		}
		return props.food(builder.build());
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity eater) {
		return 32;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity eater) {
		if (!level.isClientSide() && eater instanceof Player player) {
			player.getFoodData().eat(nutrition, saturation);
			for (var e : effects) {
				if (level.getRandom().nextFloat() < e.chance()) {
					player.addEffect(e.get());
				}
			}
		}
		int sips = getSips(stack) - 1;
		if (sips <= 0) {
			return new ItemStack(TFItems.GREATER_FLASK.get());
		}
		setSips(stack, sips);
		return stack;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		if (Configuration.ENABLE_FOOD_EFFECT_TOOLTIP.get())
			TDFoodItem.getFoodEffects(stack, list);
	}

}
