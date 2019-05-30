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

package net.okocraft.retcon.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import lombok.Getter;
import lombok.val;

import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {
    @Getter
    private final Path logFolder;

    @Getter
    private final Path commandLog;

    @Getter
    private final Path tpsLog;

    @Getter
    private final Path onlinePlayersLog;

    public Configuration(JavaPlugin plugin) {
        val config = plugin.getConfig();

        val logFolderConf = plugin.getDataFolder().getAbsolutePath() + "/logs/";
        logFolder = Paths.get(
                logFolderConf
        );

        val commandLogConf = config.getString("files.command", "command.log");
        commandLog = Paths.get(
                logFolderConf + Objects.requireNonNull(commandLogConf)
        );

        val tpsLogConf = config.getString("files.tps", "tps.log");
        tpsLog = Paths.get(
                logFolderConf + Objects.requireNonNull(tpsLogConf)
        );

        val onlinePlayersConf = config.getString("files.onlinePlayers", "online.log");
        onlinePlayersLog = Paths.get(
                logFolderConf + Objects.requireNonNull(onlinePlayersConf)
        );
    }
}
