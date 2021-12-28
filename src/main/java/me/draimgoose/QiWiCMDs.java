package me.draimgoose;

import me.draimgoose.Config.MainConfig;
import me.draimgoose.Config.MessageConfig;
import me.draimgoose.Utils.QiWiModule;
import me.draimgoose.Utils.UsefulFunc;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QiWiCMDs implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (true) {
            final Player p = (Player)sender;
            if (args.length == 0) {
                for (final String s : MessageConfig.getMSG().getCFG().getStringList("Messages.Help")) {
                    p.sendMessage(UsefulFunc.color(s));
                }
                return false;
            }
            final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "reload": {
                    if (p.hasPermission("draimdonate.reload")) {
                        MainConfig.getMain().reloadCFG();
                        MessageConfig.getMSG().reloadCFG();
                        DraimDonate.reloadToken();
                        UsefulFunc.sendUsefulMSG(p, "Messages.Another.Reloaded");
                        UsefulFunc.sendLog("&7Конфиг перезагружен");
                        UsefulFunc.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        break;
                    }
                    UsefulFunc.sendUsefulMSG(p, "Messages.Another.NoPerm");
                    UsefulFunc.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                case "pay": {
                    if (args.length != 2 || !isNumeric(args[1])) {
                        UsefulFunc.sendUsefulMSG(p, "Messages.Another.ArgError");
                        return true;
                    }
                    QiWiModule.generateBill(p, Integer.parseInt(args[1]));
                    UsefulFunc.sendLog(UsefulFunc.config("messages","Messages.Console.PayLink", p, Integer.parseInt(args[1])));
                    UsefulFunc.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
                    break;
                }
                case "check": {
                    if (QiWiModule.getClients().containsKey(p.getUniqueId())) {
                        QiWiModule.checkBill(p);
                        break;
                    }
                    UsefulFunc.sendUsefulMSG(p, "Messages.Another.NoBill");
                    UsefulFunc.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                case "reject": {
                    if (QiWiModule.getClients().containsKey(p.getUniqueId())) {
                        QiWiModule.getClients().remove(p.getUniqueId());
                        UsefulFunc.sendUsefulMSG(p, "Messages.Another.BillRejected");
                        UsefulFunc.sendLog(UsefulFunc.config("messages","Messages.Console.RejectLink", p, 0));
                        UsefulFunc.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        break;
                    }
                    UsefulFunc.sendUsefulMSG(p, "Messages.Another.NoBill");
                    UsefulFunc.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                default: {
                    p.sendMessage(UsefulFunc.config("messages","Messages.Another.NoArg", p, 0));
                    for (final String s2 : MessageConfig.getMSG().getCFG().getStringList("Messages.Help")) {
                        p.sendMessage(UsefulFunc.color(s2));
                    }
                    UsefulFunc.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
            }
        }
        return false;
    }

    public static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
