package me.draimgoose;

import me.draimgoose.Commands.MainCommands;
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

    /**
     * The constant config.
     */
    public static FileConfiguration config;

    /**
     * The constant buttonKey.
     */
    public static NamespacedKey buttonKey;

    /**
     * The constant AdminGUI.
     */
    public static NamespacedKey AdminGUI;
    private static DraimDonate instance;
    private static String QiWiT;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DraimDonate getInstance() {
        return instance;
    }

    /**
     * Gets qiwi.
     *
     * @return the qiwi
     */
    public static String getQiwi() {
        return QiWiT;
    }

    /**
     * Gets config string.
     *
     * @param path the path
     * @return the config string
     */
    public static String getConfigString(String path) {
        return config.getString(path);
    }

    /**
     * Gets config int.
     *
     * @param path the path
     * @return the config int
     */
    public static int getConfigInt(String path) {
        return config.getInt(path);
    }

    /**
     * Gets config keys.
     *
     * @param path the path
     * @param deep the deep
     * @return the config keys
     */
    public static Set<String> getConfigKeys(String path, boolean deep) {
        return config.getConfigurationSection(path).getKeys(deep);
    }

    /**
     * Gets config int list.
     *
     * @param path the path
     * @return the config int list
     */
    public static List<Integer> getConfigIntList(String path) {
        return config.getIntegerList(path);
    }

    /**
     * Gets config boolean.
     *
     * @param path the path
     * @return the config boolean
     */
    public static Boolean getConfigBoolean(String path) {
        return config.getBoolean(path);
    }

    /**
     * Gets config string list.
     *
     * @param path the path
     * @return the config string list
     */
    public static List<String> getConfigStringList(String path) {
        return config.getStringList(path);
    }

    /**
     * Init keys.
     */
    public void initKeys() {
        buttonKey = new NamespacedKey(this, "adminGuiButton");
        AdminGUI = new NamespacedKey(this, "adminGuiMenu");
    }

    @Override
    public void onEnable() {
        printASCII();
        initCommands();
        initKeys();
        instance = this;
        this.saveDefaultConfig();
        config = getConfig();
        DBCore.getDB().setUp();
        MainConfig.getMain().setUp();
        MessageConfig.getMSG().setUp();
        reloadToken();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Плагин был отключен.");
        DBCore.getDB().saveCFG();
    }

    /**
     * Reload configuration.
     */
    public void reloadConfiguration() {
        reloadConfig();
        config = getConfig();
    }

    /**
     * Reload token.
     */
    public static void reloadToken() {
        QiWiT = MainConfig.getMain().getCFG().getString("DraimDonate.Token");
    }

    /**
     *  Команда "/draimdonate" выводит GUI-панель которая показывает админ функционал плагина
     *  Такой как, доната, даты пополнения и т.д.
     */
    public void initCommands() {
        getCommand("draimdonate").setExecutor(new MainCommands(this));
        getCommand("draimdonate").setTabCompleter(new TabComplete());
    }

    /**
     * Print ascii.
     */
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
