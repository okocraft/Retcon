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

import java.util.Optional;

import lombok.val;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class Converter {
    /**
     * {@code Location} をテキスト表現（例：{@code (world, X:30, Y:20, Z:10)}）に変換する。
     * ワールド名が空の場合は unknown が代わりに充てがわれる。
     *
     * @param location {@code Location}
     *
     * @return 変換されたテキスト表現
     */
    public static String locationToString(Location location) {
        val X     = location.getBlockX();
        val Y     = location.getBlockY();
        val Z     = location.getBlockZ();

        val world = Optional.ofNullable(location.getWorld())
                .map(World::getName)
                .orElse("unknown");

        return String.format("(%s, X:%s, Y:%s, Z:%s)", world, X, Y, Z);
    }
}
