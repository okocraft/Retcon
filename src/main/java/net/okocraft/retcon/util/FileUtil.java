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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author AKANE AKAGI (akaregi)
 */
public class FileUtil {
    /**
     * Creates a folder. If a folder exists, does nothing.
     *
     * @param path Folder to be created
     */
    public static void createFolder(Path path) {
        try {
            if (Files.isDirectory(path)) {
                return;
            }

            Files.createDirectory(path);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * ファイルにテキストを挿入する処理を {@code Runnable} で返す。
     *
     * @param target 宛先ファイル
     * @param text   テキスト
     *
     * @return Runnable
     */
    public static Runnable getAppendText(Path target, String text) {
        return () -> {
            try {
                Files.write(
                        target,
                        text.getBytes(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
