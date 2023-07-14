package dev.xkmc.twilightdelight.init.data;

import dev.xkmc.twilightdelight.init.TwilightDelight;
import dev.xkmc.twilightdelight.init.world.TreeConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TDDatapackRegistriesGen extends DatapackBuiltinEntriesProvider {

	public static final ResourceKey<DamageType> ROSE_TEA = ResourceKey.create(Registries.DAMAGE_TYPE,
			new ResourceLocation(TwilightDelight.MODID, "thorn_rose_tea"));

	private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.DAMAGE_TYPE, ctx -> {
				ctx.register(ROSE_TEA, new DamageType("twilightdelight.thorn_rose_tea", 0));
			})
			.add(Registries.CONFIGURED_FEATURE, TreeConfig::bootstrap);


	public TDDatapackRegistriesGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of("minecraft", TwilightDelight.MODID));
	}

	@NotNull
	public String getName() {
		return "Create's Damage Type Data";
	}

}
