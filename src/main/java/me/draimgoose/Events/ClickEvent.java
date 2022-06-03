package me.draimgoose.Events;

import me.draimgoose.DraimDonate;
import me.draimgoose.GUIs.AdminGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

public class ClickEvent implements Listener {

    /**
     * On click.
     *
     * @param e the event
     */
    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null) {
            return;
        }

        Player p = (Player) e.getWhoClicked();

        if (!p.getPersistentDataContainer().has(DraimDonate.AdminGUI, PersistentDataType.STRING)) {
            return;
        }
        else if (!p.getPersistentDataContainer().get(DraimDonate.AdminGUI, PersistentDataType.STRING).equalsIgnoreCase(AdminGUI.name)) {
            return;
        }

        e.setCancelled(true);

        if (e.getClickedInventory().getHolder() != null) {
            if (e.getClickedInventory().getHolder().equals(e.getWhoClicked())) return;
        }

        switch (e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(DraimDonate.buttonKey, PersistentDataType.STRING)) {
            case ("EXIT"):
                p.closeInventory();
                break;
            default:
                break;
        }
    }
}
