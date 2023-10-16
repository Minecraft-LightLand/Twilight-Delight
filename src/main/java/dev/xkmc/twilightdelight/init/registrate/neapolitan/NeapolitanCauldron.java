package dev.xkmc.twilightdelight.init.registrate.neapolitan;

import com.teamabnormals.blueprint.core.api.BlueprintCauldronInteraction;
import com.teamabnormals.neapolitan.common.block.MilkshakeCauldronBlock;
import com.teamabnormals.neapolitan.core.NeapolitanConfig;
import com.teamabnormals.neapolitan.core.other.NeapolitanCauldronInteractions;
import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateBlockstateProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.registrate.TDEffects;
import dev.xkmc.twilightdelight.init.registrate.TDItems;
import dev.xkmc.twilightdelight.init.registrate.delight.EffectSupplier;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.Locale;

public enum NeapolitanCauldron {
	AURORA(MaterialColor.TERRACOTTA_LIGHT_BLUE,
			new EffectSupplier(TDEffects.AURORA_GLOWING, 0, 0, 1),
			new EffectSupplier(() -> MobEffects.MOVEMENT_SPEED, 0, 2, 1),
			new EffectSupplier(() -> MobEffects.JUMP, 0, 1, 1)),
	TORCHBERRY(MaterialColor.COLOR_YELLOW,
			new EffectSupplier(TDEffects.FIRE_RANGE, 0, 0, 1)),
	PHYTOCHEMICAL(MaterialColor.COLOR_LIGHT_GREEN,
			new EffectSupplier(TDEffects.POISON_RANGE, 0, 0, 1)),
	GLACIER(MaterialColor.TERRACOTTA_WHITE,
			new EffectSupplier(TDEffects.FROZEN_RANGE, 0, 0, 1)),
	;

	public final BlueprintCauldronInteraction interaction;

	public final ItemEntry<Item> iceCream, milkshake;
	public final BlockEntry<Block> iceCreamBlock;
	public final BlockEntry<MilkshakeCauldronBlock> cauldron;

	NeapolitanCauldron(MaterialColor color, EffectSupplier... effs) {
		String name = name().toLowerCase(Locale.ROOT);
		interaction = BlueprintCauldronInteraction.register(new ResourceLocation(TwilightDelight.MODID, name + "_milkshake"),
				CauldronInteraction.newInteractionMap());
		iceCream = TDItems.simpleFood(NeapolitanFoodType.ICE_CREAM, name + "_ice_cream", 6, 0.4f, parse(effs, 1800));
		milkshake = TDItems.simpleFood(NeapolitanFoodType.MILKSHAKE, name + "_milkshake", 3, 0.6f, parse(effs, 600));
		iceCreamBlock = TwilightDelight.REGISTRATE.block(name + "_ice_cream_block", p -> new Block(
						BlockBehaviour.Properties.of(Material.SNOW, color).strength(0.2F).sound(SoundType.SNOW)))
				.simpleItem().register();
		cauldron = TwilightDelight.REGISTRATE.block(name + "_milkshake_cauldron", p -> new MilkshakeCauldronBlock(interaction.map()))
				.blockstate(this::buildCauldron)
				.loot((pvd, block) -> pvd.dropOther(block, Items.CAULDRON)).register();
	}

	private void buildCauldron(DataGenContext<Block, MilkshakeCauldronBlock> ctx, RegistrateBlockstateProvider pvd) {
		String name = name().toLowerCase(Locale.ROOT);
		pvd.getVariantBuilder(ctx.get()).forAllStates(e -> {
			int level = e.getValue(MilkshakeCauldronBlock.LEVEL);
			String suffix = level == 3 ? "_full" : "_level" + level;
			return ConfiguredModel.builder().modelFile(pvd.models()
					.withExistingParent(ctx.getName() + suffix, "block/template_cauldron" + suffix)
					.texture("content", pvd.modLoc("block/" + name + "_milkshake"))
					.texture("inside", pvd.mcLoc("block/cauldron_inner"))
					.texture("particle", pvd.mcLoc("block/cauldron_side"))
					.texture("top", pvd.mcLoc("block/cauldron_top"))
					.texture("bottom", pvd.mcLoc("block/cauldron_bottom"))
					.texture("side", pvd.mcLoc("block/cauldron_side"))).build();
		});
	}

	public void registerInteraction() {
		NeapolitanCauldronInteractions.addMilkshakeInteractions(milkshake.get(), cauldron.get(), iceCream.get(), interaction.map());
	}

	private static EffectSupplier[] parse(EffectSupplier[] effs, int time) {
		EffectSupplier[] ans = new EffectSupplier[effs.length];
		for (int i = 0; i < effs.length; i++) {
			ans[i] = new EffectSupplier(effs[i].eff(), time, effs[i].amp(), effs[i].chance());
		}
		return ans;
	}


	public static void registerInteractions() {
		if (NeapolitanConfig.COMMON.milkshakeCauldrons.get()) {
			for (var e : values()) {
				e.registerInteraction();
			}
		}
	}

	public static void register() {

	}

}
