package me.draimgoose.Utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils 
{
    public static void sendMessage(String msg, CommandSender p)
    {
        if (!(p instanceof Player player))
        {
            p.sendMessage(ColorUtils.colorMessage(msg
                    .replace("chat! ", "").replace("chat!", "")
                    .replace("title! ", "").replace("title!", "")
                    .replace("actionbar! ", "").replace("actionbar!", "")
            ));
        }
        else
        {
            if (msg.startsWith("chat!"))
            {
                sendChatMSG(msg.replace("chat! ","").replace("chat!",""), player);
            }
            else if (msg.startsWith("title!"))
            {
                sendTitleMSG(msg.replace("title! ", "").replace("title!",""), player);
            }
            else if (msg.startsWith("actionbar!"))
            {
                sendActionBarMSG(msg.replace("actionbar! ","").replace("actionbar!",""), player);
            }
            else
            {
                sendChatMSG(msg.replace("chat! ","").replace("chat!",""), player);
            }
        }
        
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
