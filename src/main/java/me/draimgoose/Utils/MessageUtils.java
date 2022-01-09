package me.draimgoose.Utils;

import me.draimgoose.Config.DBCore;
import me.draimgoose.Config.MainConfig;
import me.draimgoose.Config.MessageConfig;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import static me.draimgoose.Utils.ColorUtils.color;

public class MessageUtils {
    public static void sendMessage(String msg, CommandSender p) {
        if (!(p instanceof Player player)) {
            p.sendMessage(ColorUtils.colorMessage(msg
                    .replace("chat! ", "").replace("chat!", "")
                    .replace("title! ", "").replace("title!", "")
                    .replace("actionbar! ", "").replace("actionbar!", "")
            ));
        }
        else {
            if (msg.startsWith("chat!")) {
                sendChatMSG(msg.replace("chat! ","").replace("chat!",""), player);
            }
            else if (msg.startsWith("title!")) {
                sendTitleMSG(msg.replace("title! ", "").replace("title!",""), player);}
            else if (msg.startsWith("actionbar!")) {
                sendActionBarMSG(msg.replace("actionbar! ","").replace("actionbar!",""), player);
            }
            else {
                sendChatMSG(msg.replace("chat! ","").replace("chat!",""), player);
            }
        }
    }

    // Префикс лога в консоли.
    public static void sendLog(String log) {
        Logger.getLogger("DraimDonate").info(color(log));
    }
    public static void sendUsefulMSG(Player p, String path) {
        String messages = MessageConfig.getMSG().getCFG().getString(path);
        p.sendMessage(color(messages));
    }

    public static String CFGOperator(String mes, Player p, int ammout) {
        String mes1 = placeholder(mes, p, ammout);
        String mes2 = color(mes1);
        return mes2;
    }

    public static String placeholder(String mes, Player p, int amount) {
        String mes1 = mes.replace("%player%", p.getName());
        String mes2 = mes1.replace("%amount%", String.valueOf(amount));
        return mes2;
    }

    public static String config(String db, String path, Player p, int amount) {
        if (db.equals("msg")) {
            String mes = MessageConfig.getMSG().getCFG().getString(path);
            String mes1 = CFGOperator(mes, p, amount);
            return mes1;
        }
        if (db.equals("QiWiCFG")) {
            String mes = MainConfig.getMain().getCFG().getString(path);
            String mes1 = CFGOperator(mes, p, amount);
            return mes1;
        }
        if (db.equals("db")) {
            String mes = DBCore.getDB().getCFG().getString(path);
            String mes1 = CFGOperator(mes, p, amount);
            return mes1;
        }
        else return "<не найдено сообщения, обратитесь к администрации>";
    }

    public static String getDate(Date date) {
        String strDateFormat = "d-MM-yyyy_H-m-s";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    // Метод отправки сообщения в ActionBar
    public static void sendActionBarMSG(String msg, Player p) {
        BaseComponent component = ComponentSerializer.parse(ColorUtils.colorBungee(msg))[0];
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
    }

    // Метод отправки сообщения в Title
    public static void sendTitleMSG(String msg, Player p) {
        p.sendTitle(ColorUtils.colorMessage(msg), "", 20, 80, 20);
    }

    // Метод отправки сообщения в Chat
    public static void sendChatMSG(String msg, Player p) {
        p.sendMessage(ColorUtils.colorMessage(msg));
    }

    // Метод проверки сообщения
    public static String toCheckMSG(String msg) {
        return ChatColor.stripColor(msg).toLowerCase();
    }
}
