
package org.derjannik.ultimatelobbyplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyGUI implements Listener {

    private UltimateLobbyPlugin plugin;

    public LobbyGUI(UltimateLobbyPlugin plugin) {
        this.plugin = plugin;
    }

    public void openLobbyGUI(Player player) {
        Inventory lobbyGUI = Bukkit.createInventory(null, 27, "Lobby Menu");

        // Add spawn location item
        ItemStack spawnItem = new ItemStack(Material.NETHER_STAR);
        ItemMeta spawnMeta = spawnItem.getItemMeta();
        spawnMeta.setDisplayName("Spawn Location");
        spawnItem.setItemMeta(spawnMeta);
        lobbyGUI.setItem(13, spawnItem);

        // Add minigame items
        for (String minigameName : plugin.getConfig().getConfigurationSection("minigames").getKeys(false)) {
            Material itemMaterial = Material.getMaterial(plugin.getConfig().getString("minigames." + minigameName + ".item"));
            int slot = plugin.getConfig().getInt("minigames." + minigameName + ".slot");
            ItemStack minigameItem = new ItemStack(itemMaterial);
            ItemMeta minigameMeta = minigameItem.getItemMeta();
            minigameMeta.setDisplayName(minigameName);
            minigameItem.setItemMeta(minigameMeta);
            lobbyGUI.setItem(slot, minigameItem);
        }

        player.openInventory(lobbyGUI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();

        if (event.getView().getTitle().equals("Lobby Menu")) {
            event.setCancelled(true);
            if (item != null && item.hasItemMeta()) {
                String itemName = item.getItemMeta().getDisplayName();
                if (itemName.equals("Spawn Location")) {
                    Location spawnLocation = (Location) plugin.getConfig().get("spawn.location");
                    if (spawnLocation != null) {
                        player.teleport(spawnLocation);
                    } else {
                        player.sendMessage("Spawn location is not set.");
                    }
                } else {
                    Location minigameLocation = (Location) plugin.getConfig().get("minigames." + itemName + ".location");
                    if (minigameLocation != null) {
                        player.teleport(minigameLocation);
                    } else {
                        player.sendMessage("Minigame location is not set.");
                    }
                }
            }
        }
    }
}
