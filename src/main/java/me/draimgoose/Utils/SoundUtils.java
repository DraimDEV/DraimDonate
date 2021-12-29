package me.draimgoose.Utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static void playSound(Player p, Sound sound) {
        p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
    }

}
