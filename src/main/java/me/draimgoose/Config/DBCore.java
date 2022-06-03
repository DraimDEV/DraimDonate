package me.draimgoose.Config;

import me.draimgoose.DraimDonate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class DBCore
{
    private static DBCore dbc;
    private File f;
    private FileConfiguration fc;

    /**
     * Sets up.
     */
    public void setUp() {
        if (this.f == null) {
            this.f = new File(DraimDonate.getInstance().getDataFolder(), "db.yml");
        }
        this.fc = YamlConfiguration.loadConfiguration(this.f);
        if(!this.f.exists()) {
            try (final InputStream in = DraimDonate.getInstance().getResource("db.yml")) {
                Files.copy(in, this.f.toPath());
                this.fc = YamlConfiguration.loadConfiguration(this.f);
                Bukkit.getServer().getConsoleSender().sendMessage("[DraimDonate] Файл локализации успешно создан.");
            }
            catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("[DraimDonate] Произошла ошибка при создании файла локализации.");
            }
        }
    }

    /**
     * Reload config.
     */
    public void reloadCFG() {
        this.f = new File(DraimDonate.getInstance().getDataFolder(), "db.yml");
        this.fc = YamlConfiguration.loadConfiguration(this.f);
    }

    /**
     * Gets config.
     *
     * @return the config.
     */
    public FileConfiguration getCFG() {
        if (this.fc == null) {
            this.reloadCFG();
        }
        return this.fc;
    }

    /**
     * Save config.
     */
    public void saveCFG() {
        try {
            this.getCFG().save(this.f);
        }
        catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Ошибка в сохранении файла локализации" + this.f);
        }
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public static DBCore getDB() {
        return DBCore.dbc;
    }

    static {
        DBCore.dbc = new DBCore();
    }
}
