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

package net.okocraft.retcon.task;

import lombok.val;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CountPlayerTask extends BukkitRunnable {
    private final Plugin plugin;

    public CountPlayerTask(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        val server = plugin.getServer();

        int onlinePlayers = server.getOnlinePlayers().size();

        plugin.getLogger().info("Current TPS: " + onlinePlayers);
    }
}
