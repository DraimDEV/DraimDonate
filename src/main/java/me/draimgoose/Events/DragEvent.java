package me.draimgoose.Events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DragEvent implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onDrag(InventoryClickEvent e) {
        if (e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getType().equals(Material.AIR)) {
            return;
        }
        if (e.getCursor() == null) return;
        if (e.getCursor().getType().equals(Material.AIR)) {
            return;
        }
        e.setCancelled(true);
    }
}
