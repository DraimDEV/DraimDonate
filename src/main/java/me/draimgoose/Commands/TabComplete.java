package me.draimgoose.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter
{
    List<String> completions = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (completions.isEmpty()) {
            completions.add("reload");
            completions.add("dshop");
        }

        List<String> fit = new ArrayList<>();

        if (args.length == 1) {
            for (String a : completions) {
                if (a.startsWith(args[0])) {
                    fit.add(a);
                }
            }
            return fit;
        }

        return null;
    }
}