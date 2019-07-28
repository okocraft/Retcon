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

package net.okocraft.retcon;

import java.util.Optional;
import java.util.logging.Logger;
import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.okocraft.retcon.command.CommandDispatcher;
import net.okocraft.retcon.listener.*;
import net.okocraft.retcon.task.CountPlayerTask;
import net.okocraft.retcon.task.GetTpsTask;
import net.okocraft.retcon.util.Configuration;

/**
 * Retcon. A Tool to track server's statistics.
 *
 * @author AKANE AKAGI (akaregi)
 */
public class Retcon extends JavaPlugin {
    /**
     * このプラグインのインスタンス
     */
    private static Retcon plugin;

    /**
     * このプラグインの設定
     */
    // NOTE: config だと getConfig() と競合する
    @Getter
    private final Configuration plConfig;

    /**
     * このプラグインのロガー
     */
    private final Logger log;

    /**
     * プラグインマネージャ
     */
    private final PluginManager pluginManager;

    /**
     * タスクを発火させるのに取る遅延時間（ティック単位）
     */
    // NOTE: 20L = 1秒
    private static final long TASK_DELAY  = 20L;

    /**
     * タスクを発火させる周期（ティック単位）
     */
    // NOTE: 36000L = 30分
    private static final long TASK_PERIOD = 36000L;

    public Retcon() {
        plConfig = new Configuration(this);
        log    = getLogger();
        pluginManager = Bukkit.getServer().getPluginManager();
    }

    @Override
    public void onEnable() {
        Optional.ofNullable(getCommand("retcon"))
                .ifPresent(command -> command.setExecutor(new CommandDispatcher()));

        new CountPlayerTask().runTaskTimer(this, TASK_DELAY, TASK_PERIOD);
        new GetTpsTask().runTaskTimer(this, TASK_DELAY, TASK_PERIOD);

        registerEvents(new PlayerCommandProcessEvent());
        registerEvents("Essentials", new PlayerBalanceUpdateEvent());
        registerEvents("Votifier", new VoteEvent());
        registerEvents("mcMMO", new mcMMOAdminChatEvent(), new mcMMOPartyChatEvent());

        // GO GO GO
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        log.info("Unregistered events.");
    }

    /**
     * イベントを登録する。
     *
     * @param events イベント
     */
    private void registerEvents(@Nonnull Listener... events) {
        for (Listener event: events) {
            pluginManager.registerEvents(event, this);
        }
    }

    /**
     * 特定のプラグインに依存したイベントを登録する。
     *
     * @param plugin プラグイン名
     * @param events イベント
     */
    private void registerEvents(@Nonnull String plugin, @Nonnull Listener... events) {
        if (pluginManager.isPluginEnabled(plugin)) {
            for (Listener event: events) {
                pluginManager.registerEvents(event, this);
            }

            log.info(String.format("%s is present. Enabled relevant events.", plugin));

            return;
        }

        log.warning(String.format("%s is absent. Passing.", plugin));
    }

    /**
     * このプラグインのインスタンスを返す。
     *
     * @return インスタンス
     */
    public static Retcon getInstance() {
        if (plugin == null) {
            plugin = (Retcon) Bukkit.getPluginManager().getPlugin("Retcon");
        }

        return plugin;
    }
}
