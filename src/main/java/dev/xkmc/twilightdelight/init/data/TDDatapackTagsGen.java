package dev.xkmc.twilightdelight.init.data;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TDDatapackTagsGen extends TagsProvider<DamageType> {

	public TDDatapackTagsGen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, ExistingFileHelper existingFileHelper) {
		super(pOutput, Registries.DAMAGE_TYPE, pLookupProvider, TwilightDelight.MODID, existingFileHelper);
	}


	protected void addTags(@NotNull HolderLookup.Provider provider) {
		tag(DamageTypeTags.BYPASSES_ARMOR).add(TDDatapackRegistriesGen.ROSE_TEA);
		tag(DamageTypeTags.BYPASSES_EFFECTS).add(TDDatapackRegistriesGen.ROSE_TEA);
		tag(DamageTypeTags.BYPASSES_RESISTANCE).add(TDDatapackRegistriesGen.ROSE_TEA);
		tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(TDDatapackRegistriesGen.ROSE_TEA);
	}

	public static Factory<TDDatapackTagsGen> makeFactory(CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
		return (output) -> new TDDatapackTagsGen(output, registries, existingFileHelper);
	}

}
