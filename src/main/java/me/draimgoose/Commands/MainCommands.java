package me.draimgoose.Commands;

import me.draimgoose.Config.MessageConfig;
import me.draimgoose.DraimDonate;
import me.draimgoose.GUIs.AdminGUI;
import me.draimgoose.Utils.MessageUtils;
import me.draimgoose.Utils.QiWiModule;
import me.draimgoose.Utils.UsefulFunc;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class MainCommands implements CommandExecutor {
    private DraimDonate pl;

    public MainCommands(DraimDonate pl) {
        super();
        this.pl = pl;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equals("draimdonate")) {
            final Player p = (Player)sender;
            if (args.length == 0) {
                for (final String s : MessageConfig.getMSG().getCFG().getStringList("Messages.Help")) {
                    p.sendMessage(UsefulFunc.color(s));
                }
                return false;
//                    p.openInventory(new AdminGUI().getMenu());
//                    p.getPersistentDataContainer().set(pl.AdminGUI, PersistentDataType.STRING, AdminGUI.name);
//                    return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (p.hasPermission("draimdonate.reload")) {
                        pl.getInstance().reloadConfig();
                        pl.getInstance().onDisable();
                        pl.getInstance().onEnable();
                        MessageUtils.sendMessage(pl.getConfigString("messages.plugin-reload"), sender);
                        UsefulFunc.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    } else {
                        MessageUtils.sendMessage(pl.getConfigString("messages.no-permission"), sender);
                        UsefulFunc.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    }
                    return true;
                }
            } else if (args.length != 2 || !isNumeric(args[1])) {
                if (args[0].equalsIgnoreCase("pay")) {
                    QiWiModule.generateBill(p, Integer.parseInt(args[1]));
                    UsefulFunc.sendLog(UsefulFunc.config("messages","Messages.Console.PayLink", p, Integer.parseInt(args[1])));
                    UsefulFunc.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
                } else {
                    UsefulFunc.sendUsefulMSG(p, "Messages.Another.ArgError");
                    UsefulFunc.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                }
                return true;
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("check")) {
                    if (QiWiModule.getClients().containsKey(p.getUniqueId())) {
                        QiWiModule.checkBill(p);
                    } else {
                        UsefulFunc.sendUsefulMSG(p, "Messages.Another.ArgError");
                        UsefulFunc.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    }
            }
        }
        }
        return false;
    }

    // Проверка на циферки, вместо буковок, а то бывают ебанаты, которые пишут /dn pay PIZDA,  а нужно /dn pay <сумма>
    public static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
