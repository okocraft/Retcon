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

import java.time.LocalDateTime;
import javax.annotation.Nonnull;

import com.google.common.base.Strings;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class TextUtil {
    /**
     * ISO 8601 形式の時刻表現を固定幅(26)で返す。文字数が固定幅に満たない場合0で埋められる。
     * 例： {@code 2019-07-28T17:52:35.560000}
     *
     * @return 固定幅の時刻表現
     */
    @Nonnull
    public static String padTime() {
        return Strings.padEnd(LocalDateTime.now().toString(), 26, '0');
    }

    /**
     * Minecraft ID を固定幅(16)にして返す。文字数が固定幅に満たない場合空白で埋められる。
     *
     * @param name Minecraft ID
     *
     * @return 固定幅(16)の Minecraft ID
     */
    @Nonnull
    public static String padPlayerName(@Nonnull String name) {
        if (name.length() > 16) {
            throw new IllegalArgumentException("This is invalid Minecraft ID.");
        }

        return Strings.padEnd(name, 16, ' ');
    }
}