package me.draimgoose.Commands;

import me.draimgoose.Config.MainConfig;
import me.draimgoose.Config.MessageConfig;
import me.draimgoose.DraimDonate;
import me.draimgoose.Utils.*;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                    p.sendMessage(ColorUtils.color(s));
                }
                return false;
            } final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "reload": {
                    if (p.hasPermission("draimdonate.reload")) {
                        MainConfig.getMain().reloadCFG();
                        DraimDonate.reloadToken();
                        MessageUtils.sendMessage(DraimDonate.getConfigString("messages.plugin-reload"), sender);
                        MessageUtils.sendLog(DraimDonate.getConfigString("messages.plugin-reload"));
                        SoundUtils.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        break;
                    }
                    MessageUtils.sendMessage(DraimDonate.getConfigString("messages.no-permission"), sender);
                    SoundUtils.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                case "pay": {
                    if (args.length != 2 || !isNumeric(args[1])) {
                        MessageUtils.sendMessage(DraimDonate.getConfigString("messages.arg-error"), sender);
                        return true;
                    }
                    QiWiModule.generateBill(p, Integer.parseInt(args[1]));
                    MessageUtils.sendLog(MessageUtils.config("messages","Messages.Console.PayLink", p, Integer.parseInt(args[1])));
                    SoundUtils.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
                    break;
                }
                case "check": {
                    if  (QiWiModule.getClients().containsKey(p.getUniqueId())) {
                        QiWiModule.checkBill(p);
                        break;
                    }
                    MessageUtils.sendUsefulMSG(p, "Messages.Another.NoBill");
                    SoundUtils.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                case "reject": {
                    if (QiWiModule.getClients().containsKey(p.getUniqueId())) {
                        QiWiModule.getClients().remove(p.getUniqueId());
                        MessageUtils.sendMessage(DraimDonate.getConfigString("messages.bill.reject"), sender);
                        MessageUtils.sendLog(MessageUtils.config("messages", "Messages.Console.RejectLink", p, 0));
                        SoundUtils.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        break;
                    }
                    MessageUtils.sendMessage(DraimDonate.getConfigString("messages.bill.no-bill"), sender);
                    SoundUtils.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                default: {
                    p.sendMessage(MessageUtils.config("messages","Messages.Another.NoArg", p, 0));
                    for (final String s2 : MessageConfig.getMSG().getCFG().getStringList("Messages.Help")) {
                        p.sendMessage(ColorUtils.color(s2));
                    }
                    SoundUtils.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
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
        /* Перемещу эту залупу сюда, а то говорят можно потерять,
           как создавать открытие меню, а ешё девственность. */

//                    p.openInventory(new AdminGUI().getMenu());
//                    p.getPersistentDataContainer().set(pl.AdminGUI, PersistentDataType.STRING, AdminGUI.name);
//                    return true;
