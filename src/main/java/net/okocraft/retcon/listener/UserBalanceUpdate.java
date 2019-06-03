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

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.val;

import com.google.common.base.Strings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.ess3.api.events.UserBalanceUpdateEvent;

import net.okocraft.retcon.util.Configuration;
import net.okocraft.retcon.util.FileUtil;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class UserBalanceUpdate implements Listener  {
    private final Configuration config;

    public UserBalanceUpdate(Configuration config) {
        this.config = config;
    }

    /**
     * Logs when a player's balance is changed
     *
     * @param event UserBalanceUpdateEvent
     */
    @EventHandler
    public void onUserBalanceUpdate(UserBalanceUpdateEvent event) {
        val time  = LocalDateTime.now();
        val today = LocalDate.now();

        val player   = event.getPlayer();
        val name     = player.getName();
        val uuid     = player.getUniqueId();
        // NOTE: IS IT CORRECT: BigDecimal#toPlainString()
        val original = event.getOldBalance().setScale(0, RoundingMode.HALF_UP).toString();
        val current  = event.getNewBalance().setScale(0, RoundingMode.HALF_UP).toString();

        val log = String.format(
                "[%s] %s %s >> %s" + System.getProperty("line.separator"),
                // PADDING 26, fixed length for ISO 8601.
                Strings.padEnd(time.toString(), 26, '0'),
                // PADDING 16, the longest player name.
                Strings.padEnd(player.getName(), 16, ' '),
                // PADDING 14, maximum expression for Java primitive double. See Essentials' configuration.
                Strings.padEnd(original, 14, ' '),
                current
        );

        // Folder structure: plugins/Retcon/balance/<player>_<UUID>/<YYYY-MM-DD>.log
        val userSpace = config.getBalanceFolder().resolve(name + "_" + uuid);
        val balanceFile = userSpace.resolve(today + ".log");

        FileUtil.createFolder(userSpace);
        FileUtil.appendText(balanceFile, log);
    }
}
