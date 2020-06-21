package me.samsonnagamani.gerconomy;

import me.samsonnagamani.gerconomy.Player.PlayerManager;
import me.samsonnagamani.gerconomy.Team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


import java.util.List;
import java.util.UUID;

public class EconomyCore {
    private GerConomy plugin = GerConomy.getPlugin();

    public boolean hasAccount(String uuid) {
        return plugin.playerManager.hasAccount(uuid);
    }

    public boolean hasTeamAccount(String name) {
        return plugin.teamManager.hasAccount(name);
    }

    public double getBalance(UUID uuid) {
        Player player = plugin.getServer().getPlayer(uuid);
        if (hasAccount(uuid.toString())) {
            if (plugin.playerManagerHashMap.containsKey(uuid)) {
                PlayerManager playerManager = plugin.playerManagerHashMap.get(player.getUniqueId());
                return playerManager.getBalance();
            }
        }
        return 0;
    }

    public double getTeamBalance(String uuid) {
        Player player = plugin.getServer().getPlayer(UUID.fromString(uuid));
        if (hasAccount(uuid)) {
            if (plugin.playerManagerHashMap.containsKey(player.getUniqueId())) {
                PlayerManager playerManager = plugin.playerManagerHashMap.get(player.getUniqueId());
                return playerManager.getBalance();
            }
        } else {
            MessageManager.playerBad(player, "You do not have an account");
        }
        return 0;
    }

    public void withdrawPlayer(UUID uuid, double amount) {
        Player player = plugin.getServer().getPlayer(uuid);

        if (hasAccount(uuid.toString())) {
            if (plugin.playerManagerHashMap.containsKey(uuid)) {
                PlayerManager playerManager = plugin.playerManagerHashMap.get(uuid);
                double balance = playerManager.getBalance();

                if (balance >= amount) {
                    playerManager.setBalance(balance - amount);
                    MessageManager.playerGood(player, "You have paid " + ChatColor.GREEN + "£" + amount);
                } else {
                    MessageManager.playerBad(player, "You do not have enough money");
                }
                return;
            }
        }
        MessageManager.playerBad(player, "You do not have an account");
    }

    public void depositPlayer(UUID uuid, double amount) {
        Player player = plugin.getServer().getPlayer(uuid);

        if (hasAccount(uuid.toString())) {
            if (plugin.playerManagerHashMap.containsKey(uuid)) {
                PlayerManager playerManager = plugin.playerManagerHashMap.get(uuid);
                double balance = playerManager.getBalance();
                playerManager.setBalance(balance + amount);
                MessageManager.playerGood(player, "You have received " + ChatColor.GREEN + "£" + amount);
                return;
            }
        }

        MessageManager.playerBad(player, "You do not have an account");
    }

    public void createPlayerAccount(String uuid) {
        if (plugin.getServer().getPlayer(UUID.fromString(uuid)) != null) {
            if (!hasAccount(uuid)) {
                // Default starting balance for player account is 50
                plugin.playerManagerHashMap.put(UUID.fromString(uuid), new PlayerManager(uuid, 50));
            }
        }
    }

    public boolean createTeamAccount(String name, List<String> playeruuids) {

        if (plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam(name) != null) {
            if (!hasTeamAccount(name)) {
                TeamManager teamManager = plugin.teamManagerHashMap.get(name);
                plugin.teamManagerHashMap.put(name, new TeamManager(name, 50, teamManager.getPlayerUuids()));
            }
        }
        return false;
    }
}
