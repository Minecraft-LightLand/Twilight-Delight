package dev.xkmc.twilightdelight.content.item.food;

import dev.xkmc.twilightdelight.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TDFoodItem extends Item {

	private static Component getTooltip(MobEffectInstance eff) {
		MutableComponent ans = Component.translatable(eff.getDescriptionId());
		MobEffect mobeffect = eff.getEffect();
		if (eff.getAmplifier() > 0) {
			ans = Component.translatable("potion.withAmplifier", ans,
					Component.translatable("potion.potency." + eff.getAmplifier()));
		}

		if (eff.getDuration() > 20) {
			ans = Component.translatable("potion.withDuration", ans,
					MobEffectUtil.formatDuration(eff, 1));
		}

		return ans.withStyle(mobeffect.getCategory().getTooltipFormatting());
	}

	public static void getFoodEffects(ItemStack stack, List<Component> list) {
		var food = stack.getFoodProperties(null);
		if (food == null) return;
		getFoodEffects(food, list);
	}

	public static void getFoodEffects(FoodProperties food, List<Component> list) {
		for (var eff : food.getEffects()) {
			int chance = Math.round(eff.getSecond() * 100);
			Component ans = getTooltip(eff.getFirst());
			if (chance == 100) {
				list.add(ans);
			} else {
				list.add(LangData.CHANCE_EFFECT.get(ans, chance));
			}
		}
	}


	public TDFoodItem(Properties props) {
		super(props);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		getFoodEffects(stack, list);
	}

}
