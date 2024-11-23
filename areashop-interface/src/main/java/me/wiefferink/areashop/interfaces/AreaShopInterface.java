package me.wiefferink.areashop.interfaces;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.function.Supplier;
import java.util.logging.Logger;

public interface AreaShopInterface {
	void debugI(Object... message);

	void debugI(Supplier<String> message);

	YamlConfiguration getConfig();

	WorldGuardPlugin getWorldGuard();

	WorldEditPlugin getWorldEdit();

	Logger getLogger();

	Plugin pluginInstance();
}
