package me.samsonnagamani.gerconomy.Player;

import me.samsonnagamani.gerconomy.GerConomy;
import me.samsonnagamani.gerconomy.MessageManager;
import me.samsonnagamani.gerconomy.MongoConnect;
import me.samsonnagamani.gerconomy.Team.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private String uuid;
    private double balance;
    private double bankaccount;
    private String team;
    private GerConomy plugin = GerConomy.getPlugin();
    private MongoConnect mongoConnect = plugin.mongoConnect;

    public PlayerManager() {}

    public PlayerManager(String uuid, double balance, double bankaccount) {
        this.uuid = uuid;
        this.balance = balance;
        this.bankaccount = bankaccount;

        TeamManager teamManager = getTeamManagerFromUuid(uuid);
        setTeam(teamManager.getName());

        setBalance(balance);
        setBankaccount(bankaccount);
    }
    public boolean hasAccount(String uuid) {
        if (hasBankaccount(uuid) && hasBalance(uuid) && hasUuid(uuid)) {
            return true;
        }
        return false;
    }

    public boolean hasBankaccount(String uuid) {
        if (mongoConnect.getPlayerDataDocument("bank_account", uuid) == null) {
            return false;
        }
        return true;
    }

    public boolean hasBalance(String uuid) {
        if (mongoConnect.getPlayerDataDocument("balance", uuid) == null) {
            return false;
        }

        return true;
    }

    public boolean hasUuid(String uuid) {
        if (mongoConnect.getPlayerDataDocument("uuid", uuid) == null) {
            return false;
        }

        return true;
    }

    public String getUuid() {
        return uuid;
    }

    public double getBalance() {
        return (double) mongoConnect.getPlayerDataDocument("balance", uuid);
    }

    public void setBalance(double balance) {
        this.balance = balance;
        mongoConnect.setPlayerDataDocument(balance, "balance", uuid);
    }

    public double getBankaccount() {
        return (double) mongoConnect.getPlayerDataDocument("bank_account", uuid);
    }

    public void setBankaccount(double bankaccount) {
        this.bankaccount = bankaccount;
        mongoConnect.setPlayerDataDocument(bankaccount, "bank_account", uuid);
    }

    public String getTeam() {
        return (String) mongoConnect.getPlayerDataDocument("team", uuid);
    }

    public void setTeam(String team) {
        this.team = team;
        mongoConnect.setPlayerDataDocument(team, "team", uuid);
    }

    public TeamManager getTeamManagerFromUuid(String uuid) {
        String playerName = plugin.getServer().getPlayer(UUID.fromString(uuid)).getName();
        String name = "";
        Iterator<Team> teams = plugin.getServer().getScoreboardManager().getMainScoreboard().getTeams().iterator();
        while (teams.hasNext()) {
            Team team = teams.next();
            if (team.getEntries().contains(playerName)) {
                name = team.getName();
            }
        }

        if (plugin.teamManagerHashMap.containsKey(name)) {
            return plugin.teamManagerHashMap.get(name);
        } else {
            return null;
        }
    }
}
