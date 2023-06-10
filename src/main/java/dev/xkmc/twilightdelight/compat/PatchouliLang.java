package dev.xkmc.twilightdelight.compat;

import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import dev.xkmc.twilightdelight.init.TwilightDelight;

public enum PatchouliLang {
	TITLE("title", "Twilight's Flavor & Delight Guide"),
	LANDING("landing", "Enjoy crossover among Twilight Forest, Farmer's Delight, and Neapolitan");

	private final String key, def;

	PatchouliLang(String key, String def) {
		this.key = "patchouli." + TwilightDelight.MODID + "." + key;
		this.def = def;
	}

	public static void genLang(RegistrateLangProvider pvd) {
		for (PatchouliLang lang : PatchouliLang.values()) {
			pvd.add(lang.key, lang.def);
		}
	}

}
