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

import java.util.Objects;
import java.util.logging.Logger;

import lombok.val;

import org.bukkit.plugin.java.JavaPlugin;

import net.okocraft.retcon.command.CommandDispatcher;
import net.okocraft.retcon.task.CountOnlineTask;
import net.okocraft.retcon.task.GetTickPerSecondTask;

public class Retcon extends JavaPlugin  {
    /**
     * Instance of this plugin.
     */
    private final Retcon plugin;

    /**
     * Version information.
     */
    private final String version;

    /**
     * Logger for this plugin.
     */
    private final Logger log;

    public Retcon(Retcon plugin) {
        this.version = getDescription().getVersion();
        this.plugin = plugin;
        this.log = getLogger();
    }

    @Override
    public void onEnable() {
        // Register command /retcon
        Objects.requireNonNull(
                getServer().getPluginCommand("retcon")).setExecutor(new CommandDispatcher()
        );

        // Register tasks
        val scheduler = getServer().getScheduler();

        //
        // int scheduleSyncRepeatingTask​(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period)
        //
        // plugin - Plugin that owns the task
        // task - Task to be executed
        // delay - Delay in server ticks before executing first repeat
        // period - Period in server ticks of the task
        //
        // FIXME: This is *sync* scheduling, it must be asynchronous.
        //        For your information: Asynchronous task can't access Bukkit API.
        //
        scheduler.scheduleSyncRepeatingTask(plugin, new CountOnlineTask(),      10L, 20L);
        scheduler.scheduleSyncRepeatingTask(plugin, new GetTickPerSecondTask(), 10L, 20L);

        // GO GO GO
        log.info("Retcon v." + version + " has been enabled.");
    }

    @Override
    public void onDisable() {
        log.info("Retcon v." + version + " has been disabled.");
    }
}
