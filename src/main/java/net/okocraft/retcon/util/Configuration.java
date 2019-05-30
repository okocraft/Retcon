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

import lombok.Getter;

import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {
    @Getter
    private final Path rootFolder;

    @Getter
    private final Path commandFolder;

    @Getter
    private final Path tpsFolder;

    @Getter
    private final Path onlineFolder;

    public Configuration(JavaPlugin plugin) {
        plugin.saveDefaultConfig();

        rootFolder    = plugin.getDataFolder().toPath();
        commandFolder = rootFolder.resolve("command");
        tpsFolder     = rootFolder.resolve("tps");
        onlineFolder  = rootFolder.resolve("online");

        // plugins/Retcon/command/
        FileUtil.createFolder(commandFolder);

        // plugins/Retcon/tps/
        FileUtil.createFolder(tpsFolder);

        // plugins/Retcon/online/
        FileUtil.createFolder(onlineFolder);
    }
}
