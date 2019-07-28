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

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.common.base.Strings;
import lombok.val;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.VotifierEvent;

import net.okocraft.retcon.Retcon;
import net.okocraft.retcon.util.FileUtil;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class VoteEvent implements Listener {
    /**
     * 投票されたとき、それを記録する。
     *
     * @param event イベント
     */
    @EventHandler
    public void onVote(VotifierEvent event) {
        val config = Retcon.getInstance().getPlConfig();

        val vote = event.getVote();

        val time    = LocalDateTime.now();
        val today   = LocalDate.now();

        val service = vote.getServiceName();
        val player  = vote.getUsername();

        val log = String.format(
                "[%s] %s %s" + System.getProperty("line.separator"),
                Strings.padEnd(time.toString(), 26, '0'),
                Strings.padEnd(player, 16, ' '),
                service
        );

        FileUtil.appendText(config.getVoteFolder().resolve(today + ".log"), log);
    }
}
