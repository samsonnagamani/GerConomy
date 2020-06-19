package me.samsonnagamani.gerconomy;

import me.samsonnagamani.gerconomy.Player.PlayerManager;
import me.samsonnagamani.gerconomy.Team.TeamManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EconomyCore implements Economy {
    private GerConomy plugin = GerConomy.getPlugin();

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String uuid) {
        return plugin.playerManager.hasAccount(uuid);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return plugin.playerManager.hasAccount(offlinePlayer.getUniqueId().toString());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    public boolean hasTeamAccount(String name) {
        return plugin.teamManager.hasAccount(name);
    }

    @Override
    public double getBalance(String uuid) {
        Player player = plugin.getServer().getPlayer(UUID.fromString(uuid));
        if (hasAccount(uuid)) {
            if (plugin.playerManagerHashMap.containsKey(player.getUniqueId())) {
                PlayerManager playerManager = plugin.playerManagerHashMap.get(player.getUniqueId());
                return playerManager.getBalance();
            }
        }
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return 0;
    }

    @Override
    public double getBalance(String s, String s1) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
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

    public double getTeamBalance(OfflinePlayer offlinePlayer) {
        return 0;
    }

    public double getTeamBalance(String s, String s1) {
        return 0;
    }

    public double getTeamBalance(OfflinePlayer offlinePlayer, String s) {
        return 0;
    }

    @Override
    public boolean has(String s, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return false;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String uuid, double amount) {
        Player player = plugin.getServer().getPlayer(UUID.fromString(uuid));

        if (hasAccount(uuid)) {
            if (plugin.playerManagerHashMap.containsKey(UUID.fromString(uuid))) {
                PlayerManager playerManager = plugin.playerManagerHashMap.get(UUID.fromString(uuid));
                double balance = playerManager.getBalance();

                if (balance >= amount) {
                    playerManager.setBalance(balance - amount);
                    MessageManager.playerGood(player, "You have paid " + ChatColor.GREEN + "£" + amount);
                    return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "You paid £" + amount);
                } else {
                    MessageManager.playerBad(player, "You do not have enough money");
                    return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.FAILURE, "You do not have enough money");
                }
            }
        }
        MessageManager.playerBad(player, "You do not have an account");
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "ou do not have an account");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String uuid, double amount) {
        Player player = plugin.getServer().getPlayer(UUID.fromString(uuid));

        if (hasAccount(uuid)) {
            if (plugin.playerManagerHashMap.containsKey(UUID.fromString(uuid))) {
                PlayerManager playerManager = plugin.playerManagerHashMap.get(UUID.fromString(uuid));
                double balance = playerManager.getBalance();
                playerManager.setBalance(balance + amount);
                MessageManager.playerGood(player, "You have received " + ChatColor.GREEN + "£" + amount);
                return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "You received £" + amount);
            }
        }

        MessageManager.playerBad(player, "You do not have an account");
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "You do not have an account");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String uuid) {
        if (plugin.getServer().getPlayer(UUID.fromString(uuid)) != null) {
            if (!hasAccount(uuid)) {
                // Default starting balance for player account is 50
                plugin.playerManagerHashMap.put(UUID.fromString(uuid), new PlayerManager(uuid, 50, 0));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        String uuid = offlinePlayer.getUniqueId().toString();

        if (plugin.getServer().getPlayer(uuid) != null) {
            if (!hasAccount(offlinePlayer.getUniqueId().toString())) {
                plugin.playerManagerHashMap.put(offlinePlayer.getUniqueId(), new PlayerManager(offlinePlayer.getUniqueId().toString(), 50, 0));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean createPlayerAccount(String uuid, String worldName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String worldName) {
        String uuid = offlinePlayer.getUniqueId().toString();

        if (plugin.getServer().getPlayer(uuid) != null) {

            if (plugin.getServer().getWorld(worldName).getPlayers().contains(offlinePlayer)) {
                plugin.playerManagerHashMap.put(offlinePlayer.getUniqueId(), new PlayerManager(offlinePlayer.getUniqueId().toString(), 50, 0));
                return true;
            }
        }
        return false;
    }

    public boolean createTeamAccount(String name, List<String> playeruuids) {

        if (plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam(name) != null) {
            if (!hasTeamAccount(name)) {
                TeamManager teamManager = plugin.teamManagerHashMap.get(name);
                if (teamManager.hasBankaccount(name)) {
                    Random rnd = new Random();
                    Double newBankAccount = 10000000d + rnd.nextInt(90000000);
                    plugin.teamManagerHashMap.put(name, new TeamManager(name, 0, newBankAccount, teamManager.getPlayerUuids()));
                }
            }
        }
        return false;
    }
}
