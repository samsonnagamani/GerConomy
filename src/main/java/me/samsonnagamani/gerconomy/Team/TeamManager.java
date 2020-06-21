package me.samsonnagamani.gerconomy.Team;

import me.samsonnagamani.gerconomy.GerConomy;
import me.samsonnagamani.gerconomy.MongoConnect;
import org.bson.Document;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class TeamManager {
    private String name;
    private double balance;
    private List<String> playersuuid;
    private GerConomy plugin = GerConomy.getPlugin();
    private MongoConnect mongoConnect = plugin.mongoConnect;
    private Scoreboard board = plugin.board;

    public TeamManager() {}

    public TeamManager(String name, double balance, List<String> playersuuid) {
        this.name = name;
        this.balance = balance;
        this.playersuuid = playersuuid;

        setName(name);
        setBalance(balance);

        
    }

    public List<String> getTeamNames() {
        List<Document> teams = (List<Document>) mongoConnect.getTeamDataCollection().find().into(new ArrayList<Document>());
        List<String> teamnames = new ArrayList<>();
        for (Document team : teams) {
            String name = (String) team.get("name");
            teamnames.add(name);
        }
        return teamnames;
    }

    public boolean hasAccount(String name) {
        return hasBalance(name) && hasName(name);
    }


    public boolean hasBalance(String name) {
        return mongoConnect.getTeamDataDocument("balance", name) != null;
    }

    public boolean hasName(String name) {
        return mongoConnect.getTeamDataDocument("name", name) != null;
    }

    public void setName(String name) {
        this.name = name;
        mongoConnect.setTeamDataDocument(name, "name", name);
    }

    public String getName() {
        return (String) mongoConnect.getTeamDataDocument("name", name);
    }

    public double getBalance() {
        return (double) mongoConnect.getTeamDataDocument("balance", name);
    }

    public void setBalance(double balance) {
        this.balance = balance;
        mongoConnect.setTeamDataDocument(balance, "balance", name);
    }

    public List<String> getPlayerUuids() {
        return (List<String>) mongoConnect.getTeamDataDocument("players", name);
    }

    public void setPlayerUuids(List<String> playersuuid) {
        this.playersuuid = playersuuid;
        mongoConnect.setTeamDataDocument(playersuuid, "players", name);
    }
}
