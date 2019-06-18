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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.val;

import com.google.common.base.Strings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.okocraft.retcon.util.Configuration;
import net.okocraft.retcon.util.Converter;
import net.okocraft.retcon.util.FileUtil;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class PlayerCommandPreProcess implements Listener {
    private final Configuration config;

    public PlayerCommandPreProcess(Configuration config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        val time  = LocalDateTime.now();
        val today = LocalDate.now();

        val player = event.getPlayer();
        val name   = player.getName();
        val locale = player.getLocale();

        val address = Optional.ofNullable(event.getPlayer().getAddress())
                .map(InetSocketAddress::getAddress)
                .map(InetAddress::getHostAddress)
                .orElse("unknown");

        val location = player.getLocation();
        val command  = event.getMessage();

        val log = String.format(
                "[%s] %s %s %s %s %s" + System.getProperty("line.separator"),
                // Time, like 2019-06-18T23:55:41.074126
                Strings.padEnd(time.toString(), 26, '0'),
                // ID, like Akikawa_
                Strings.padEnd(name, 16, ' '),
                // Locale, like es_es
                locale,
                // IP Address, like 255.255.255.255
                Strings.padEnd(address, 15, ' '),
                // Location, like
                Strings.padEnd(Converter.locationToString(location), 40, ' '),
                // Command, like /genocide
                command
        );

        FileUtil.appendText(config.getCommandFolder().resolve(today + ".log"), log);
    }
}
