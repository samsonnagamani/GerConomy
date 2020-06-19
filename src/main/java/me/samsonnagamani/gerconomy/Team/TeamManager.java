package me.samsonnagamani.gerconomy.Team;

import me.samsonnagamani.gerconomy.GerConomy;
import me.samsonnagamani.gerconomy.MongoConnect;
import org.bson.Document;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class TeamManager {
    private String name;
    private double balance;
    private double bankaccount;
    private List<String> playersuuid;
    private GerConomy plugin = GerConomy.getPlugin();
    private MongoConnect mongoConnect = plugin.mongoConnect;
    private Scoreboard board = plugin.board;

    public TeamManager() {}

    public TeamManager(String name, double balance, double bankaccount, List<String> playersuuid) {
        this.name = name;
        this.balance = balance;
        this.bankaccount = bankaccount;
        this.playersuuid = playersuuid;

        setName(name);
        setBankaccount(bankaccount);
        setBalance(balance);

        
    }

    public List<String> getTeamNames() {
        Iterator<Document> teams = mongoConnect.getTeamDataCollection().find().into(new ArrayList<Document>()).iterator();
        List<String> teamnames = new ArrayList<String>();
        while(teams.hasNext()) {
            Document team = teams.next();
            String name = (String) team.get("name");
            teamnames.add(name);
        }
        return teamnames;
    }

    public boolean hasAccount(String name) {
        if (hasBankaccount(name) && hasBalance(name) && hasName(name)) {
            return true;
        }
        return false;
    }

    public boolean hasBankaccount(String name) {
        if (mongoConnect.getTeamDataDocument("bank_account", name) == null) {
            return false;
        }
        return true;
    }

    public boolean hasBalance(String name) {
        if (mongoConnect.getTeamDataDocument("balance", name) == null) {
            return false;
        }

        return true;
    }

    public boolean hasName(String name) {
        if (mongoConnect.getTeamDataDocument("name", name) == null) {
            return false;
        }

        return true;
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

    public double getBankaccount() {
        return (double) mongoConnect.getTeamDataDocument("bank_account", name);
    }

    public void setBankaccount(double bankaccount) {
        this.bankaccount = bankaccount;
        mongoConnect.setTeamDataDocument(bankaccount, "bank_account", name);
    }

    public List<String> getPlayerUuids() {
        return (List<String>) mongoConnect.getTeamDataDocument("players", name);
    }

    public void setPlayerUuids(List<String> playersuuid) {
        this.playersuuid = playersuuid;
        mongoConnect.setTeamDataDocument(playersuuid, "players", name);
    }
}
