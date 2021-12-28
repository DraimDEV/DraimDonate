package me.draimgoose;

import me.draimgoose.Commands.AdminGUICommand;
import me.draimgoose.Commands.TabComplete;
import me.draimgoose.Config.DBCore;
import me.draimgoose.Config.MainConfig;
import me.draimgoose.Config.MessageConfig;
import me.draimgoose.Utils.ColorUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public final class DraimDonate extends JavaPlugin
{
    public static FileConfiguration config;
    public static NamespacedKey buttonKey;
    public static NamespacedKey AdminGUI;
    private static DraimDonate instance;
    private static String QiWiT;

    public static DraimDonate getInstance() {
        return instance;
    }
    public static String getQiwi() {
        return QiWiT;
    }

    // Подхват всех настроек из config.yml
    public static String getConfigString(String path) {
        return config.getString(path);
    }
    public static int getConfigInt(String path) {
        return config.getInt(path);
    }
    public static Set<String> getConfigKeys(String path, boolean deep) {
        return config.getConfigurationSection(path).getKeys(deep);
    }
    public static List<Integer> getConfigIntList(String path) {
        return config.getIntegerList(path);
    }
    public static Boolean getConfigBoolean(String path) {
        return config.getBoolean(path);
    }
    public static List<String> getConfigStringList(String path) {
        return config.getStringList(path);
    }

    public void onEnable() {
        printASCII();
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        DraimDonate.instance = this;
        DBCore.getDB().setUp();
        MainConfig.getMain().setUp();
        MessageConfig.getMSG().setUp();
        reloadToken();
        this.getCommand("draimdonate").setExecutor(new QiWiCMDs());
    }

    public void onDisable() {
        this.getLogger().info("Плагин был отключен.");
        DBCore.getDB().saveCFG();
    }

    public static void reloadToken() {
        DraimDonate.QiWiT = MainConfig.getMain().getCFG().getString("DraimDonate.Token");
    }

    // Команда "/draimdonate" выводит GUI-панель которая показывает админ функционал плагина
    // Такой как, доната, даты пополнения и т.д.
    public void initCMDs() {
        getCommand("draimdonate").setExecutor(new AdminGUICommand(this));
        getCommand("draimdonate").setTabCompleter(new TabComplete());
    }

    // Принт в Консоль, просто для красоты
    public void printASCII() {
        getLogger().info(ColorUtils.colorMessage(" &6 /$$$$$$$                     /$$               /$$$$$$$                                  /$$              "));
        getLogger().info(ColorUtils.colorMessage(" &6| $$__  $$                   |__/              | $$__  $$                                | $$              "));
        getLogger().info(ColorUtils.colorMessage(" &6| $$  \\ $$  /$$$$$$  /$$$$$$  /$$ /$$$$$$/$$$$ | $$  \\ $$  /$$$$$$  /$$$$$$$   /$$$$$$  /$$$$$$    /$$$$$$ "));
        getLogger().info(ColorUtils.colorMessage(" &6| $$  | $$ /$$__  $$|____  $$| $$| $$_  $$_  $$| $$  | $$ /$$__  $$| $$__  $$ |____  $$|_  $$_/   /$$__  $$"));
        getLogger().info(ColorUtils.colorMessage(" &6| $$  | $$| $$  \\__/ /$$$$$$$| $$| $$ \\ $$ \\ $$| $$  | $$| $$  \\ $$| $$  \\ $$  /$$$$$$$  | $$    | $$$$$$$$"));
        getLogger().info(ColorUtils.colorMessage(" &6| $$  | $$| $$      /$$__  $$| $$| $$ | $$ | $$| $$  | $$| $$  | $$| $$  | $$ /$$__  $$  | $$ /$$| $$_____/"));
        getLogger().info(ColorUtils.colorMessage(" &6| $$$$$$$/| $$     |  $$$$$$$| $$| $$ | $$ | $$| $$$$$$$/|  $$$$$$/| $$  | $$|  $$$$$$$  |  $$$$/|  $$$$$$$"));
        getLogger().info(ColorUtils.colorMessage(" &6|_______/ |__/      \\_______/|__/|__/ |__/ |__/|_______/  \\______/ |__/  |__/ \\_______/   \\___/   \\_______/"));
        getLogger().info(ColorUtils.colorMessage("                                                                                  "));
        getLogger().info(ColorUtils.colorMessage("                                                                &6by DraimGooSe        "));
        getLogger().info(ColorUtils.colorMessage("                                                                                  "));
    }
}
