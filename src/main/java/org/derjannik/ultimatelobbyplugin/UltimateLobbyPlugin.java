
package org.derjannik.ultimatelobbyplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class UltimateLobbyPlugin extends JavaPlugin {

    private PlayerManager playerManager;
    private MenuManager menuManager;
    private Navigator navigator;
    private PermissionSystem permissionSystem;
    private MySQL mySQL;
    private Cosmetics cosmetics;
    private AdminMenu adminMenu;
    private LobbyCommands lobbyCommands;
    private LobbyGUI lobbyGUI;

    @Override
    public void onEnable() {
        playerManager = new PlayerManager(this);
        menuManager = new MenuManager(this);
        navigator = new Navigator(this);
        permissionSystem = new PermissionSystem();
        mySQL = new MySQL("localhost", 3306, "minecraft", "root", "password");
        cosmetics = new Cosmetics(this);
        adminMenu = new AdminMenu(this);
        lobbyCommands = new LobbyCommands(this);
        lobbyGUI = new LobbyGUI(this);

        getServer().getPluginManager().registerEvents(playerManager, this);
        getServer().getPluginManager().registerEvents(menuManager, this);
        getServer().getPluginManager().registerEvents(navigator, this);
        getServer().getPluginManager().registerEvents(cosmetics, this);
        getServer().getPluginManager().registerEvents(adminMenu, this);
        getServer().getPluginManager().registerEvents(lobbyGUI, this);

        getCommand("lobby").setExecutor(lobbyCommands);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
