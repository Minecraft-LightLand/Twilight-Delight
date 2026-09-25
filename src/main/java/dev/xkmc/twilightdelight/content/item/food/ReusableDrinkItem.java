package dev.xkmc.twilightdelight.content.item.food;

import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;

/**
 * A drinkable item with multiple sips. Each sip applies a small meal and
 * damages the item; the model changes with damage and the last sip
 * returns a glass bottle.
 */
public class ReusableDrinkItem extends Item {

	private final int nutrition;
	private final float saturation;
	private final List<EffectSupplier> effects;

	public ReusableDrinkItem(Properties props, int nutrition, float saturation, List<EffectSupplier> effects) {
		super(withFood(props, nutrition, saturation, effects).stacksTo(1).durability(4));
		this.nutrition = nutrition;
		this.saturation = saturation;
		this.effects = effects;
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
		if (level instanceof ServerLevel server && eater instanceof Player player) {
			ServerPlayer sp = player instanceof ServerPlayer serverPlayer ? serverPlayer : null;
			stack.hurtAndBreak(1, server, sp, item -> {
			});
			if (stack.isEmpty()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}
		}
		return stack;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		if (Configuration.ENABLE_FOOD_EFFECT_TOOLTIP.get())
			TDFoodItem.getFoodEffects(stack, list);
	}

}
