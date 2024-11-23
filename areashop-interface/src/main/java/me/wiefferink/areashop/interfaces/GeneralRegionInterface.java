package me.wiefferink.areashop.interfaces;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.World;

public interface GeneralRegionInterface {
	ProtectedRegion getRegion();

	String getName();

	World getWorld();

	String getWorldName();

	int getWidth();

	int getDepth();

	int getHeight();

	default BlockVector3 computeDimensions() {
		return BlockVector3.at(getWidth(), getHeight(), getDepth());
	}
}
