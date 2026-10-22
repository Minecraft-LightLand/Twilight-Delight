package dev.xkmc.twilightdelight.content.effect;

import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The Knight Phantom's blessing. The armor of the blessed one takes over a part of
 * the damage: every piece that really grants armor points takes over
 * {@link #SHARE_PER_PIECE} of the damage per level. The pieces share the damage
 * evenly and together they never take over more than {@link #MAX_SHARE} of it. The
 * pieces that took the damage wear out by the same amount.
 */
public class PhantomBlessing extends MobEffect {

	/** The share of the damage a single armor piece takes over, per level. */
	private static final float SHARE_PER_PIECE = 0.1F;
	/** The share of the damage the armor pieces take over at most. */
	private static final float MAX_SHARE = 0.8F;
	private static final List<EquipmentSlot> ARMOR_SLOTS =
			List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

	public PhantomBlessing() {
		super(MobEffectCategory.BENEFICIAL, 0x8A79C6);
	}

	/**
	 * Lets the armor of the target take over a part of the damage.
	 *
	 * @return the damage the target actually takes
	 */
	public static float shareDamage(LivingEntity target, DamageSource source, float damage) {
		if (damage <= 0 || source.is(DamageTypeTags.BYPASSES_ARMOR)) return damage;
		var effect = target.getEffect(TDEffects.PHANTOM_BLESSING);
		if (effect == null) return damage;
		var pieces = new ArrayList<ArmorPiece>();
		for (var slot : ARMOR_SLOTS) {
			var stack = target.getItemBySlot(slot);
			if (!stack.isEmpty() && hasArmorPoints(stack, slot)) {
				pieces.add(new ArmorPiece(slot, stack));
			}
		}
		if (pieces.isEmpty()) return damage;
		int level = effect.getAmplifier() + 1;
		// the pieces share the damage evenly, together they never take more than MAX_SHARE of it
		float share = Math.min(SHARE_PER_PIECE * level, MAX_SHARE / pieces.size());
		float shared = damage * share;
		for (var piece : pieces) {
			piece.stack().hurtAndBreak(Math.max(1, (int) Math.ceil(shared)), target, piece.slot());
		}
		return damage - shared * pieces.size();
	}

	/** The armor points the piece in the slot really grants. */
	private static boolean hasArmorPoints(ItemStack stack, EquipmentSlot slot) {
		List<AttributeModifier> list = new ArrayList<>();
		stack.forEachModifier(slot, (attribute, modifier) -> {
			if (attribute.is(Attributes.ARMOR)) {
				list.add(modifier);
			}
		});
		for (var e : list) {
			if (e.amount() > 0)
				return true;
		}
		return false;
	}

	private record ArmorPiece(EquipmentSlot slot, ItemStack stack) {
	}

}
