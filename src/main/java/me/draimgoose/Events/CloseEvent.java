package me.draimgoose.Events;

import me.draimgoose.DraimDonate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.persistence.PersistentDataType;

public class CloseEvent implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getPlayer().getPersistentDataContainer().has(DraimDonate.AdminGUI, PersistentDataType.STRING)) {
            e.getPlayer().getPersistentDataContainer().remove(DraimDonate.AdminGUI);
        }
    }
}
