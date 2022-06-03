package me.draimgoose.GUIs;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.draimgoose.DraimDonate;
import me.draimgoose.Utils.ColorUtils;
import me.draimgoose.Utils.HeadUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class AdminGUI {

    /**
     * The constant name.
     */
    public static final String name = "AdminGUI";

    /**
     * Gets menu.
     *
     * @return the menu
     */
    public Inventory getMenu() {
        String guiName = ColorUtils.colorMessage(DraimDonate.getConfigString("menus.admin-gui.title"));
        int guiSize = DraimDonate.getConfigInt("menus.admin-gui.size");
        Inventory adminGUI = Bukkit.createInventory(null, guiSize, guiName);

        for (String button : DraimDonate.getConfigKeys("menus.admin-gui.buttons", false)) {
            List<Integer> intSlots = DraimDonate.getConfigIntList("menus.admin-gui.buttons."+button+".slots");
            for (int i : intSlots)  {
                adminGUI.setItem(i, getItem(button));
            }
        }
        return adminGUI;
    }

    /**
     * Gets item.
     *
     * @param name the name
     * @return the item
     */
    public ItemStack getItem(String name) {
        ItemStack button;
        String materialString = DraimDonate.getConfigString("menus.admin-gui.buttons."+name+".material");
        String itemName = ColorUtils.colorMessage(DraimDonate.getConfigString("menus.admin-gui.buttons."+name+".name"));
        List<String> itemLore = new ArrayList<>();
        Boolean isGlowing = DraimDonate.getConfigBoolean("menus.admin-gui.buttons."+name+".glowing");
        String type = DraimDonate.getConfigString("menus.admin-gui.buttons."+name+".type").toUpperCase();
        for (String line : DraimDonate.getConfigStringList("menus.admin-gui.buttons."+name+".lore")){
            itemLore.add(ColorUtils.colorMessage(line));
        } if (materialString.contains("head:")) {
            String b64 = materialString.replace("head:", "").replace(" ", "");
            button = HeadUtils.itemFromBase64(b64);
        } else if (materialString.contains("hdb:")) {
            String id = materialString.replace("hdb:", "").replace(" ", "");
            button = new HeadDatabaseAPI().getItemHead(id);
        } else {
            Material itemMaterial = Material.getMaterial(materialString);
            button = new ItemStack(itemMaterial, 1);
        }

        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(itemLore);
        if (isGlowing){
            meta.addEnchant(Enchantment.LURE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.getPersistentDataContainer().set(DraimDonate.buttonKey, PersistentDataType.STRING, type);
        button.setItemMeta(meta);

        return button;
    }
}
