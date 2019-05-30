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

package net.okocraft.retcon.listener;

import java.time.LocalDateTime;

import lombok.val;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.okocraft.retcon.util.Configuration;
import net.okocraft.retcon.util.FileUtil;

public class PlayerCommandPreprocess implements Listener {
    private final Configuration config;

    public PlayerCommandPreprocess(Configuration config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        val command = event.getMessage();
        val time = LocalDateTime.now();

        event.getPlayer().sendMessage(String.format("[%s] %s", time, command));

        FileUtil.appendText(
                config.getCommandLog(),
                String.format("[%s] %s" + System.getProperty("line.separator"), time, command)
        );
    }
}
