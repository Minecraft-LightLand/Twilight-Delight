
package dev.xkmc.twilightdelight.init.data;

import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.neoforged.neoforgespi.locating.IModFile;

public class ModFilePackResources implements Pack.ResourcesSupplier {
	protected final IModFile modFile;
	protected final String sourcePath;

	public ModFilePackResources(IModFile modFile, String sourcePath) {
		this.modFile = modFile;
		this.sourcePath = sourcePath;
	}

	@Override
	public PackResources openPrimary(PackLocationInfo location) {
		return new PathPackResources(location, modFile.findResource(sourcePath));
	}

	@Override
	public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
		return new PathPackResources(location, modFile.findResource(sourcePath));
	}
}