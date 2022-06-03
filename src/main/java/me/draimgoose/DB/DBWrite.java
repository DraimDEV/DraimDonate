package me.draimgoose.DB;

import me.draimgoose.Config.DBCore;
import me.draimgoose.Utils.MessageUtils;
import org.bukkit.entity.Player;

import java.util.Date;

public class DBWrite
{
    /**
     * Add player donate.
     *
     * @param p      the player
     * @param amount the amount
     */
    public static void addPlayerDonate(final Player p, final int amount) {
        DBCore.getDB().getCFG().set("DraimDonate.TotalPay", DBCore.getDB().getCFG().getInt("DraimDonate.TotalPay") + amount);
        if (DBCore.getDB().getCFG().getConfigurationSection("DraimDonate.Players." + p.getName()) == null) {
            setNewPlayer(p, amount);
        }
        writeConf(p, amount);
    }

    /**
     * Sets new player.
     *
     * @param p      the player
     * @param amount the amount
     */
    public static void setNewPlayer(final Player p, final int amount) {
        DBCore.getDB().getCFG().createSection("DraimDonate.Players." + p.getName());
        DBCore.getDB().getCFG().createSection("DraimDonate.Players." + p.getName() + ".AmountAll");
        DBCore.getDB().getCFG().createSection("DraimDonate.Players." + p.getName() + ".Donate");
        DBCore.getDB().getCFG().set("DraimDonate.Player." + p.getName() + ".AmountAll", 0);
    }

    /**
     * Write config.
     *
     * @param p      the player
     * @param amount the amount
     */
    public static void writeConf(final Player p, final int amount) {
        DBCore.getDB().getCFG().set("DraimDonate.Players." + p.getName() + ".AmountAll", DBCore.getDB().getCFG().getInt("DraimDonate.Players." + p.getName() + ".AmmountAll") + amount);
        final Date date = new Date();
        DBCore.getDB().getCFG().createSection("DraimDonate.Players." + p.getName() + ".Donate." + MessageUtils.getDate(date));
        DBCore.getDB().getCFG().set("DraimDonate.Players." + p.getName() + ".Donate." + MessageUtils.getDate(date), amount);
        DBCore.getDB().saveCFG();
    }
}
