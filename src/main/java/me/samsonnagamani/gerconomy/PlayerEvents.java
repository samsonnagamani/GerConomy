package me.samsonnagamani.gerconomy;

import me.samsonnagamani.gerconomy.Player.PlayerManager;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerEvents implements Listener {
    private GerConomy plugin = GerConomy.getPlugin();

    // Setup/load player and team data
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.mongoConnect.addTeamsToDb();
        plugin.mongoConnect.loadTeamData();
        plugin.mongoConnect.loadPlayerData(player);
        plugin.mongoConnect.configurePlayer(player);
    }
    
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        plugin.mongoConnect.loadTeamData();
        plugin.mongoConnect.loadPlayerData(player);
    }

}
