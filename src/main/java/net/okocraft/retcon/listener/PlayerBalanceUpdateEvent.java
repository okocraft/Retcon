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
import java.text.NumberFormat;
import java.time.LocalDate;

import lombok.val;

import com.google.common.base.Strings;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.ess3.api.events.UserBalanceUpdateEvent;

import net.okocraft.retcon.Retcon;
import net.okocraft.retcon.util.FileUtil;
import net.okocraft.retcon.util.TextUtil;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class PlayerBalanceUpdateEvent implements Listener  {
    /**
     * プレイヤーの所持金が更新されたとき、変更前・後所持金および差分を記録する。
     *
     * @param event イベント
     */
    @EventHandler
    public void onUserBalanceUpdate(UserBalanceUpdateEvent event) {
        val config = Retcon.getInstance().getPlConfig();

        val today = LocalDate.now();

        val player   = event.getPlayer();
        val name     = player.getName();
        val uuid     = player.getUniqueId();

        // Max: 10,000,000,000,000
        Double original = event.getOldBalance().setScale(0, RoundingMode.HALF_UP).doubleValue();

        // Max: 10,000,000,000,000
        Double current = event.getNewBalance().setScale(0, RoundingMode.HALF_UP).doubleValue();

        // Difference between current and original
        Double diff = current - original;

        val formatter = NumberFormat.getNumberInstance();

        val log = String.format(
                "[%s] %s %s %s %s" + System.getProperty("line.separator"),
                TextUtil.padTime(),
                TextUtil.padPlayerName(name),
                // ORIGINAL.    PADDING 18, maximum value of Essentials's economy system(+ separator).
                Strings.padEnd(formatter.format(original), 18, ' '),
                // CURRENT.     PADDING 18, maximum value of Essentials's economy system(+ separator).
                Strings.padEnd(formatter.format(current), 18, ' '),
                // DIFFERENCE.  PADDING __.
                formatter.format(diff)
        );

        // Folder structure: plugins/Retcon/balance/<player>_<UUID>/<Time>.log
        val userSpace = config.getBalanceFolder().resolve(name + "_" + uuid);
        val balanceFile = userSpace.resolve(today + ".log");

        FileUtil.createFolder(userSpace);
        FileUtil.appendText(balanceFile, log);
    }
}
