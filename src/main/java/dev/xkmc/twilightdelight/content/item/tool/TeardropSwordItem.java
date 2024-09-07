package dev.xkmc.twilightdelight.content.item.tool;

import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.delight.DelightFood;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import twilightforest.item.FierySwordItem;

public class TeardropSwordItem extends FierySwordItem {

	public static final Tier TIER = new SimpleTier(
			BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 1536, 9, 5, 30,
			() -> Ingredient.of(new ItemStack(DelightFood.EXPERIMENT_113.item.get())));

	public TeardropSwordItem(Item.Properties p) {
		super(TIER, p.rarity(Rarity.UNCOMMON).fireResistant());
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity attacker) {
		boolean success = super.hurtEnemy(itemstack, target, attacker);
		if (success) {
			if (Mth.nextInt(attacker.getRandom(), 1, 3) == 1) {
				target.addEffect(new MobEffectInstance(TDEffects.TEMPORAL_SADNESS, 100, 0));
			}
		}
		return success;
	}

}
