package me.draimgoose.Utils;

import me.draimgoose.Config.DBCore;
import me.draimgoose.Config.MainConfig;
import me.draimgoose.Config.MessageConfig;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class UsefulFunc {
    public static String color(String mes) {
        return mes.replace("&", "§");
    }

    public static String placeholder(String mes, Player p, int amount) {
        String mes1 = mes.replace("%player%", p.getName());
        String mes2 = mes1.replace("%amount%", String.valueOf(amount));
        return mes2;
    }

    public static String CFGOperator(String mes, Player p, int amount) {
        String mes1 = UsefulFunc.placeholder(mes, p, amount);
        String mes2 = UsefulFunc.color(mes1);
        return mes2;
    }

    public static String config(String db, String path, Player p, int amount) {
        if (db.equals("messages")) {
            String mes = MessageConfig.getMSG().getCFG().getString(path);
            String mes1 = UsefulFunc.CFGOperator(mes, p, amount);
            return mes1;
        }
        if (db.equals("qiwi")) {
            String mes = MainConfig.getMain().getCFG().getString(path);
            String mes1 = UsefulFunc.CFGOperator(mes, p, amount);
            return mes1;
        }
        if (db.equals("database")) {
            String mes = DBCore.getDB().getCFG().getString(path);
            String mes1 = UsefulFunc.CFGOperator(mes, p, amount);
            return mes1;
        }
        else return "<no existing string file selected>";
    }

    public static void sendUsefulMSG(Player p, String path) {
        String messages = MessageConfig.getMSG().getCFG().getString(path);
        p.sendMessage(color(messages));
    }

    public static void sendLog(String log) {
        Logger.getLogger("DraimDonate").info(color(log));
    }
    public static void playSound(Player p, Sound sound) {
        p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
    }

    public static String getDate(Date date) {
        String strDateFormat = "d-MM-yyyy_H-m-s";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
