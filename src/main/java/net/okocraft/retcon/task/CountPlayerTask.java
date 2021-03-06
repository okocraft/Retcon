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

import java.time.LocalDate;

import lombok.val;

import org.bukkit.scheduler.BukkitRunnable;

import net.okocraft.retcon.Retcon;
import net.okocraft.retcon.util.FileUtil;
import net.okocraft.retcon.util.TextUtil;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class CountPlayerTask extends BukkitRunnable {
    @Override
    public void run() {
        val plugin = Retcon.getInstance();
        val config = plugin.getPlConfig();

        val today   = LocalDate.now();

        val online = plugin.getServer().getOnlinePlayers().size();

        val log = String.format(
                "[%s] %s" + System.lineSeparator(),
                TextUtil.padTime(),
                online
        );

        Retcon.getExecutor().submit(FileUtil.getAppendText(config.getOnlineFolder().resolve(today + ".log"), log));
    }
}
