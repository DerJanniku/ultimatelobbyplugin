
package org.derjannik.ultimatelobbyplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyCommands implements CommandExecutor {

    private UltimateLobbyPlugin plugin;

    public LobbyCommands(UltimateLobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                // Display help message
                player.sendMessage("Available commands: /lobby help, /lobby set spawn, /lobby delete spawn, /lobby set minigame, /lobby delete minigame");
                return true;
            }

            if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equalsIgnoreCase("spawn")) {
                    Location location = player.getLocation();
                    if (args.length == 5) {
                        try {
                            double x = Double.parseDouble(args[2]);
                            double y = Double.parseDouble(args[3]);
                            double z = Double.parseDouble(args[4]);
                            location = new Location(player.getWorld(), x, y, z);
                        } catch (NumberFormatException e) {
                            player.sendMessage("Invalid coordinates.");
                            return false;
                        }
                    }
                    // Set spawn location
                    plugin.getConfig().set("spawn.location", location);
                    plugin.saveConfig();
                    player.sendMessage("Lobby spawn location set.");
                    return true;
                }

                if (args[1].equalsIgnoreCase("minigame")) {
                    if (args.length < 6) {
                        player.sendMessage("Usage: /lobby set minigame <name> <item> <slot> <x> <y> <z>");
                        return false;
                    }
                    String minigameName = args[2];
                    Material itemMaterial = Material.getMaterial(args[3].toUpperCase());
                    if (itemMaterial == null) {
                        player.sendMessage("Invalid item.");
                        return false;
                    }
                    int slot;
                    try {
                        slot = Integer.parseInt(args[4]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("Invalid slot number.");
                        return false;
                    }
                    double x, y, z;
                    try {
                        x = Double.parseDouble(args[5]);
                        y = Double.parseDouble(args[6]);
                        z = Double.parseDouble(args[7]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("Invalid coordinates.");
                        return false;
                    }
                    Location location = new Location(player.getWorld(), x, y, z);
                    // Set minigame location
                    plugin.getConfig().set("minigames." + minigameName + ".item", itemMaterial.toString());
                    plugin.getConfig().set("minigames." + minigameName + ".slot", slot);
                    plugin.getConfig().set("minigames." + minigameName + ".location", location);
                    plugin.saveConfig();
                    player.sendMessage("Minigame " + minigameName + " set.");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("delete")) {
                if (args[1].equalsIgnoreCase("spawn")) {
                    // Delete spawn location
                    plugin.getConfig().set("spawn.location", null);
                    plugin.saveConfig();
                    player.sendMessage("Lobby spawn location deleted.");
                    return true;
                }

                if (args[1].equalsIgnoreCase("minigame")) {
                    if (args.length < 3) {
                        player.sendMessage("Usage: /lobby delete minigame <name>");
                        return false;
                    }
                    String minigameName = args[2];
                    // Delete minigame
                    plugin.getConfig().set("minigames." + minigameName, null);
                    plugin.saveConfig();
                    player.sendMessage("Minigame " + minigameName + " deleted.");
                    return true;
                }
            }
        }
        return false;
    }
}
