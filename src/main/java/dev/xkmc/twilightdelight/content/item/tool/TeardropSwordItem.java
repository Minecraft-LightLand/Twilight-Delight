package dev.xkmc.twilightdelight.content.item.tool;

import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import twilightforest.TwilightForestMod;
import twilightforest.item.FierySwordItem;

public class TeardropSwordItem extends FierySwordItem {

	public static final Tier TIER = new ForgeTier(4, 1536, 9, 5, 30,
			BlockTags.create(TwilightForestMod.prefix("needs_fiery_tool")),
			() -> Ingredient.of(new ItemStack(TDItems.Food.EXPERIMENT_113.item.get())));

	public TeardropSwordItem(Item.Properties p) {
		super(TIER, p.rarity(Rarity.UNCOMMON).fireResistant());
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity attacker) {
		boolean success = super.hurtEnemy(itemstack, target, attacker);
		if (success) {
			if (Mth.nextInt(attacker.getRandom(), 1, 3) == 1) {
				target.addEffect(new MobEffectInstance(TDEffects.TEMPORAL_SADNESS.get(), 100, 0));
			}
		}
		return success;
	}

}
