package me.draimgoose;

import me.draimgoose.Config.DBCore;
import me.draimgoose.Config.MainConfig;
import me.draimgoose.Config.MessageConfig;
import me.draimgoose.Utils.ColorUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class DraimDonate extends JavaPlugin {

    private static DraimDonate instance;
    private static String QiWiT;

    public static DraimDonate getInstance() {
        return DraimDonate.instance;
    }

    public static String getQiwi() {
        return DraimDonate.QiWiT;
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
