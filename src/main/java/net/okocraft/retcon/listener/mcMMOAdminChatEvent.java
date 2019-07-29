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

import lombok.val;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.chat.McMMOAdminChatEvent;

import net.okocraft.retcon.Retcon;
import net.okocraft.retcon.util.FileUtil;
import net.okocraft.retcon.util.TextUtil;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class mcMMOAdminChatEvent implements Listener {
    /**
     * mcMMO の AdminChat にチャットが投稿されたとき、ファイルに記録する
     *
     * @param event イベント
     */
    @EventHandler
    public void onAdminChat(McMMOAdminChatEvent event) {
        val config = Retcon.getInstance().getPlConfig();

        val today   = LocalDate.now();

        val name    = event.getSender();
        val message = event.getMessage();

        val log = String.format(
                "[%s] %s %s" + System.getProperty("line.separator"),
                TextUtil.padTime(),
                TextUtil.padPlayerName(name),
                message
        );

        val userSpace = config.getMcMMOFolder().resolve("AdminChat");

        FileUtil.createFolder(userSpace);
        Retcon.getExecutor().submit(FileUtil.getAppendText(userSpace.resolve(today + ".log"), log));
    }
}
