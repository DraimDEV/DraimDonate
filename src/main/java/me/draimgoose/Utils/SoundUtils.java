package me.draimgoose.Utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * The type Sound utils.
 */
public class SoundUtils {

    /**
     * Play sound.
     *
     * @param p the player
     * @param sound the sound
     */
    public static void playSound(Player p, Sound sound) {
        p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
    }

}
