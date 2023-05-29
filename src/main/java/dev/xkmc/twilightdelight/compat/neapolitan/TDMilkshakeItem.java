package dev.xkmc.twilightdelight.compat.neapolitan;

import com.teamabnormals.neapolitan.common.item.MilkshakeItem;
import dev.xkmc.twilightdelight.content.item.food.TDFoodItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TDMilkshakeItem extends MilkshakeItem {

	public TDMilkshakeItem(Properties pProperties) {
		super(MobEffectCategory.BENEFICIAL, pProperties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag isAdvanced) {
		TDFoodItem.getFoodEffects(stack, list);
	}

}
