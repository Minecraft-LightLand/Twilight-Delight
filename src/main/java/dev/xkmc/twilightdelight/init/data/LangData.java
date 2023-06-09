package dev.xkmc.twilightdelight.init.data;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import dev.xkmc.twilightdelight.compat.PatchouliLang;
import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;
import java.util.Locale;

public enum LangData {
	CHANCE_EFFECT("tooltip.chance", "%1$s with %2$s%% chance", 2, ChatFormatting.GRAY),
	FROZEN_TITLE("jei.frozen", "Freezing", 0, null);

	private final String key, def;
	private final int arg;
	private final ChatFormatting format;


	LangData(String key, String def, int arg, @Nullable ChatFormatting format) {
		this.key = TwilightDelight.MODID + "." + key;
		this.def = def;
		this.arg = arg;
		this.format = format;
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public MutableComponent get(Object... args) {
		if (args.length != arg)
			throw new IllegalArgumentException("for " + name() + ": expect " + arg + " parameters, got " + args.length);
		MutableComponent ans = Component.translatable(key, args);
		if (format != null) {
			return ans.withStyle(format);
		}
		return ans;
	}

	public static void genLang(RegistrateLangProvider pvd) {
		for (LangData lang : LangData.values()) {
			pvd.add(lang.key, lang.def);
		}
		pvd.add("itemGroup.tab." + TwilightDelight.MODID, "Twilight's Delight");
		pvd.add("item.twilightdelight.fiery_knife.tooltip", "Burns targets. Cooks food when cut");
		pvd.add("item.twilightdelight.knightmetal_knife.tooltip", "Extra damage to armored targets");
		pvd.add("item.twilightdelight.teardrop_sword.tooltip", "Burns the target and has a chance to cry");
		pvd.add("block.twilightdelight.maze_stove.tooltip", "Cook Twilight meals faster");
		pvd.add("block.twilightdelight.fiery_cooking_pot.tooltip", "Heats itself. Cook Twilight meals fastly.");
		pvd.add("death.attack.twilightdelight.thorn_rose_tea", "%1$s acknowledged that every rose has its thorn");
		pvd.add("death.attack.twilightdelight.thorn_rose_tea.player", "1$s acknowledged that every rose has its thorn whilst trying to escape %2$s");
		pvd.add("effect.twilightdelight.fire_range.description","Ignite enemies around you, and make you cannot be ignited.");
		pvd.add("effect.twilightdelight.poison_range.description","Poison enemies around you, and make you immune to Poison.");
		pvd.add("effect.twilightdelight.frozen_range.description","Freeze enemies around you, and make you immune to Frosty. Can also freeze items.");
		pvd.add("effect.twilightdelight.temporal_sadness.description","Reduce movement speed and attack damage.");
		pvd.add("effect.twilightdelight.aurora_glowing.description","Make yourself and all entities your see glow with rainbow color.");
		PatchouliLang.genLang(pvd);
	}


}
