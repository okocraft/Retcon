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
        val time = LocalDateTime.now();

        // TODO: DECIDE WHICH NAME OR UUID IS LOGGED
        val player   = event.getPlayer();
        val name     = Strings.padEnd(player.getName(), 16, ' ');
        val original = event.getOldBalance();
        val current  = event.getNewBalance();

        val log = String.format(
                "[%s] %s %s >> %s",
                Strings.padEnd(time.toString(), 26, ' '),
                Strings.padEnd(name, 16, ' '),
                original,
                current
        );

        // Folder structure: plugins/Retcon/balance/<player>/<YYYY-MM-DDThh:mm:ss>.log
        val userSpace = config.getBalanceFolder().resolve(name);
        val balanceFile = userSpace.resolve(time + ".log");

        FileUtil.createFolder(userSpace);
        FileUtil.appendText(balanceFile, log);
    }
}
