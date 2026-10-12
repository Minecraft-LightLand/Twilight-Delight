package dev.xkmc.twilightdelight.content.item.tool;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;
import vectorwing.farmersdelight.common.item.KnifeItem;

/**
 * A tomahawk carved out of a steak, bone handle and all. It swings like a knife for 10 damage,
 * but the blade only wears down when the meat on it is eaten: once the last bite is gone,
 * all that is left of it is the bone the steak was served on.
 */
public class TomahawkSmeaxeItem extends KnifeItem {

	/** The stat line is flat, so the tier carries no attack damage bonus of its own. */
	public static final Tier TIER = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, 250, 1.6F, 0F, 10,
			() -> Ingredient.EMPTY);

	public TomahawkSmeaxeItem(int durability, Properties properties) {
		super(TIER, properties.durability(durability)
				.attributes(DiggerItem.createAttributes(TIER, 10, -2.4f)));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		// fighting never wears the blade down, only eating it does
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
		super.finishUsingItem(stack, level, consumer);
		// creative mode leaves the stack alone, so there is nothing to wear down there
		if (!stack.isEmpty()) return stack;
		stack.setCount(1);
		stack.setDamageValue(stack.getDamageValue() + 1);
		if (stack.getDamageValue() < stack.getMaxDamage()) return stack;
		return new ItemStack(Items.BONE);
	}

}