package me.samsonnagamani.gerconomy;

import me.samsonnagamani.gerconomy.Commands.Manager.CommandManager;
import me.samsonnagamani.gerconomy.Player.PlayerManager;
import me.samsonnagamani.gerconomy.Team.TeamManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;
import java.util.UUID;

public final class GerConomy extends JavaPlugin {
    private static GerConomy plugin;
    public MongoConnect mongoConnect;
    private CommandManager commandManager;
    public EconomyCore economyCore;
    public HashMap<UUID, PlayerManager> playerManagerHashMap = new HashMap<>();
    public HashMap<String, TeamManager> teamManagerHashMap = new HashMap<>();
    public PlayerManager playerManager;
    public TeamManager teamManager;
    public Scoreboard board;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        board = getServer().getScoreboardManager().getMainScoreboard();

        instanceClasses();

        mongoConnect.connect();
        commandManager.setup();

        if (!setupEconomy()) {
            MessageManager.consoleBad("Economy could not be registered... Vault is missing!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);

        MessageManager.consoleGood("\n\n GerConomy has been Enabled \n\n");
    }

    // Check if the Vault plugin exists
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        getServer().getServicesManager().register(Economy.class, economyCore, this, ServicePriority.Highest);
        MessageManager.consoleGood("Economy has been registered...");
        return true;
    }

    private void instanceClasses() {
        mongoConnect = new MongoConnect();
        commandManager = new CommandManager();
        economyCore = new EconomyCore();
        playerManager = new PlayerManager();
        teamManager = new TeamManager();
    }

    public static GerConomy getPlugin() {
        return plugin;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MessageManager.consoleBad("\n\n GerConomy has been Disabled \n\n");
    }
}
