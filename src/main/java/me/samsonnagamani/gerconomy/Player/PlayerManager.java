package me.samsonnagamani.gerconomy.Player;

import me.samsonnagamani.gerconomy.GerConomy;
import me.samsonnagamani.gerconomy.MongoConnect;
import me.samsonnagamani.gerconomy.Team.TeamManager;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class PlayerManager {

    private String uuid;
    private double balance;
    private String team;
    private final GerConomy plugin = GerConomy.getPlugin();
    private final MongoConnect mongoConnect = plugin.mongoConnect;

    public PlayerManager() {}

    public PlayerManager(String uuid, double balance) {
        this.uuid = uuid;
        this.balance = balance;

        TeamManager teamManager = getTeamManagerFromUuid(uuid);
        setTeam(teamManager.getName());

        setBalance(balance);
    }
    public boolean hasAccount(String uuid) {
        return hasBalance(uuid) && hasUuid(uuid);
    }

    public boolean hasBalance(String uuid) {
        return mongoConnect.getPlayerDataDocument("balance", uuid) != null;
    }

    public boolean hasUuid(String uuid) {
        return mongoConnect.getPlayerDataDocument("uuid", uuid) != null;
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
        for (Team team : plugin.getServer().getScoreboardManager().getMainScoreboard().getTeams()) {
            if (team.getEntries().contains(playerName)) {
                name = team.getName();
            }
        }

        return plugin.teamManagerHashMap.getOrDefault(name, null);
    }
}
