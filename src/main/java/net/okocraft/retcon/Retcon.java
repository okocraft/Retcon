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

import net.okocraft.retcon.listener.VoteEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import net.okocraft.retcon.command.CommandDispatcher;
import net.okocraft.retcon.listener.PlayerCommandPreProcess;
import net.okocraft.retcon.listener.UserBalanceUpdate;
import net.okocraft.retcon.util.Configuration;

/**
 * Retcon. A Tool to track server's statistics.
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

        pm.registerEvents(new PlayerCommandPreProcess(config), this);

        if (pm.isPluginEnabled("Essentials")) {
            pm.registerEvents(new UserBalanceUpdate(config), this);

            log.info("Essentials detected. Enabled relevant events.");
        }

        if (pm.isPluginEnabled("Votifier")) {
            pm.registerEvents(new VoteEvent(config), this);

            log.info("Votifier detected. Enabled relevant events.");
        }

        // GO GO GO
        log.info("Enabled Retcon v" + version);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);

        // GOOD BYE.
        log.info("Disabled Retcon v" + version);
    }
}
