package me.samsonnagamani.gerconomy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.samsonnagamani.gerconomy.Player.PlayerManager;
import me.samsonnagamani.gerconomy.Team.TeamManager;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;


public class MongoConnect {
    private GerConomy plugin = GerConomy.getPlugin();

    protected MongoDatabase database;
    private MongoCollection playerData;
    private MongoCollection teamData;
    private Scoreboard board = plugin.board;

    public void connect() {
        MongoClient client = MongoClients.create();
        setDatabase(client.getDatabase("minecraft_server"));
        setPlayerDataCollection(database.getCollection("users"));
        setTeamDataCollection(database.getCollection("teams"));
        MessageManager.consoleGood("Database Connected");

    }

    public void loadPlayerData(Player player) {
        // If player has joined server before load their data
        if (player.hasPlayedBefore()) {
            UUID uuid = player.getUniqueId();

            if (plugin.economyCore.hasAccount(uuid.toString())) {
                Document playerdata = (Document) plugin.mongoConnect.getPlayerDataCollection().find(new Document("uuid", uuid.toString())).first();
                double balance = playerdata.getDouble("balance");

                plugin.playerManagerHashMap.put(uuid, new PlayerManager(uuid.toString(), balance));
                MessageManager.playerInfo(player, "Player Info Loaded!");
            }
        } else {
            addNewPlayer(player);
        }
    }

    public void addNewPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (plugin.getServer().getPlayer(uuid) != null) {
            plugin.economyCore.createPlayerAccount(uuid.toString());
        }

        // TODO add a sensible comment
        configurePlayer(player);

    }

    public void configurePlayer(Player player) {
        TeamManager teamManager;
        PlayerManager playerManager;
        String playerName = player.getName();
        String name = "";
        for (Team team : board.getTeams()) {
            if (team.getEntries().contains(playerName)) {
                name = team.getName();
            }
        }

        if (plugin.teamManagerHashMap.containsKey(name) && plugin.economyCore.hasAccount(player.getUniqueId().toString())) {
            playerManager = plugin.playerManagerHashMap.get(player.getUniqueId());
            teamManager = plugin.teamManagerHashMap.get(name);
            if (playerManager.getTeam().equals(teamManager.getName())) {
                playerManager.setTeam(teamManager.getName());
                MessageManager.playerGood(player, player.getName() + " team account is set");
            }
        } else {
            MessageManager.playerBad(player, "You do not have an account " + ChatColor.WHITE + "or " + ChatColor.RED + "You are not in a team");
        }
    }

    public void addTeamsToDb() {
        for (Team team : board.getTeams()) {
            String name = team.getName();
            Set<String> players = team.getEntries();
            List<String> playersuuid = new ArrayList<>();
            for (String playername : players) {
                Player player = (Player) Bukkit.getPlayer(playername);
                String uuid = player.getUniqueId().toString();
                playersuuid.add(uuid);
            }

            setTeamDataDocument(team.getName(), "name", name);
            setTeamDataDocument(playersuuid, "players", name);
        }

        MessageManager.consoleGood("Teams have been saved to Db");
    }

    public void addNewTeam(String name) {
        List<String> playeruuids = new ArrayList<>(board.getTeam(name).getEntries());
        if (!plugin.economyCore.hasTeamAccount(name)) {
            // Default starting balance for team account is 50
            plugin.teamManagerHashMap.put(name, new TeamManager(name, 50, playeruuids));
            MessageManager.consoleGood("Team " + name + " has been initialised");
        } else {
            MessageManager.consoleInfo("Team " + name + " already exists");
        }
    }

    public void loadTeamData() {
        for (String teamname : plugin.teamManager.getTeamNames()) {
            Team team = board.getTeam(teamname);
            if (plugin.economyCore.hasTeamAccount(teamname)) {
                Document teamdata = (Document) plugin.mongoConnect.getTeamDataCollection().find(new Document("name", teamname)).first();
                double balance = teamdata.getDouble("balance");

                List<String> teamPlayerUuids = new ArrayList<>();

                for (String s : team.getEntries()) {
                    Player teamPlayer = plugin.getServer().getPlayer(s);
                    teamPlayerUuids.add(teamPlayer.getUniqueId().toString());
                }

                plugin.teamManagerHashMap.put(teamname, new TeamManager(teamname, balance, teamPlayerUuids));
                MessageManager.consoleInfo("Team info loaded");

            } else {
                addNewTeam(team.getName());
            }
        }
        MessageManager.consoleGood("Team Info Loaded!");
    }

    public void setPlayerDataDocument(Object value, String identifier, String uuid) {
        Document document = new Document("uuid", uuid);
        if (playerData.find(document).first() == null) {
            playerData.insertOne(document);
        }
        Bson newValue = new Document(identifier, value);
        Bson updateOperation = new Document("$set", newValue);
        playerData.updateOne(document, updateOperation);
    }

    public Object getPlayerDataDocument(String identifier, String uuid) {
        Document document = new Document("uuid", uuid);
        if (playerData.find(document).first() != null) {
            Document found =  (Document) playerData.find(document).first();
            return found.get(identifier);
        }

        return null;
    }

    public void setTeamDataDocument(Object value, String identifier, String name) {
        Document document = new Document("name", name);
        if (teamData.find(document).first() == null) {
            teamData.insertOne(document);
        }
        Bson newValue = new Document(identifier, value);
        Bson updateOperation = new Document("$set", newValue);
        teamData.updateOne(document, updateOperation);
    }

    public Object getTeamDataDocument(String identifier, String name) {
        Document document = new Document("name", name);
        if (teamData.find(document).first() != null) {
            Document found =  (Document) teamData.find(document).first();
            return found.get(identifier);
        }

        MessageManager.consoleBad("Document is null for name " + name);
        return null;
    }

    public MongoCollection getTeamDataCollection() {
        return teamData;
    }

    public void setTeamDataCollection(MongoCollection teamData) {
        this.teamData = teamData;
    }

    public MongoCollection getPlayerDataCollection() {
        return playerData;
    }

    public void setPlayerDataCollection(MongoCollection playerData) {
        this.playerData = playerData;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }
}
