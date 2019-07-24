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
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.okocraft.retcon.command.CommandDispatcher;
import net.okocraft.retcon.listener.PlayerCommandPreProcess;
import net.okocraft.retcon.listener.UserBalanceUpdate;
import net.okocraft.retcon.listener.VoteEvent;
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

    public Retcon() {
        config = new Configuration(this);
        log    = getLogger();
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

        registerDependEvent("Essentials", new UserBalanceUpdate(config));
        registerDependEvent("Votifier", new VoteEvent(config));

        // GO GO GO
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        log.info("Unregistered events.");
    }

    /**
     * 特定のプラグインに依存したイベントを登録する。
     *
     * @param plugin プラグイン名
     * @param event  イベント
     */
    private void registerDependEvent(String plugin, Listener event) {
        val pm = Bukkit.getServer().getPluginManager();

        if (pm.isPluginEnabled(plugin)) {
            pm.registerEvents(event, this);

            log.info(String.format("%s detected. Enabled relevant events.", plugin));

            return;
        }

        log.warning(String.format("%s is absent. Passing.", plugin));
    }
}
