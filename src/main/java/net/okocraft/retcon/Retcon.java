/*
 * This is a part of Retcon.
 * Copyright (C) 2019 AKANE AKAGI (akaregi)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.okocraft.retcon;

import java.util.Optional;
import java.util.logging.Logger;

import lombok.val;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import net.okocraft.retcon.command.CommandDispatcher;
import net.okocraft.retcon.listener.PlayerCommandPreprocess;
import net.okocraft.retcon.util.Configuration;

/**
 * Retcon. A server's statistics logger.
 *
 * @author AKANE AKAGI (akaregi)
 */
public class Retcon extends JavaPlugin {
    /**
     * Configuration.
     */
    private final Configuration config;

    /**
     * Logger for this plugin.
     */
    private final Logger log;

    /**
     * Version information.
     */
    private final String version;

    public Retcon() {
        // Initialize configuration
        config = new Configuration(this);

        // Misc
        log = getLogger();
        version = getDescription().getVersion();
    }

    @Override
    public void onEnable() {
        // Register command /retcon
        Optional.ofNullable(getCommand("retcon"))
                .ifPresent(command -> command.setExecutor(new CommandDispatcher()));

        // Register tasks
        // new CountOnlinePlayerTask(plugin).runTaskTimerAsynchronously(this, 10L, 10L);
        // new GetTickPerSecondTask(plugin).runTaskTimerAsynchronously(this, 10L, 10L);

        // Register events
        val pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new PlayerCommandPreprocess(config), this);

        // GO GO GO
        log.info("Enabled Retcon v" + version);
    }

    @Override
    public void onDisable() {
        log.info("Disabled Retcon v" + version);

        HandlerList.unregisterAll(this);
    }
}