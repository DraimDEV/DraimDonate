package me.draimgoose.Commands;

import me.draimgoose.DraimDonate;
import me.draimgoose.GUIs.AdminGUI;
import me.draimgoose.Utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class AdminGUICommand implements CommandExecutor
{
    private DraimDonate pl;

    public AdminGUICommand(DraimDonate pl)
    {
        super();
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,String label, String[] args)
    {
        if (command.getName().equals("dn"))
        {
            if (args.length == 0)
            {
                if (sender instanceof Player p)
                {
                    p.openInventory(new AdminGUI().getMenu());
                    p.getPersistentDataContainer().set(DraimDonate.AdminGUI, PersistentDataType.STRING, AdminGUI.name);
                    return true;
                }
            }

            else if (args.length == 1)
            {
                if (args[0].equalsIgnoreCase("reload"))
                {
                    if (sender.hasPermission("draimdonate.reload"))
                    {
                        DraimDonate.getInstance().reloadConfig();
                        DraimDonate.getInstance().onDisable();
                        DraimDonate.getInstance().onEnable();
                        MessageUtils.sendMessage(DraimDonate.getConfigString("messages.plugin-reload"), sender);
                    }
                    else
                    {
                        MessageUtils.sendMessage(DraimDonate.getConfigString("messages.no-permission"), sender);
                    }
                    return true;
                }
            }
            else if (args.length == 2)
            {
                if (args[0].equalsIgnoreCase("dshop")){

                }
            }
        }
        return false;
    }
}
