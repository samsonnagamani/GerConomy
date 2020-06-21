package me.samsonnagamani.gerconomy.Commands;

import me.samsonnagamani.gerconomy.GerConomy;
import me.samsonnagamani.gerconomy.MessageManager;
import me.samsonnagamani.gerconomy.Player.PlayerManager;
import me.samsonnagamani.gerconomy.Team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamBalanceCommand extends BukkitCommand {
    private GerConomy plugin = GerConomy.getPlugin();
    private Scoreboard board = plugin.board;


    public TeamBalanceCommand() {
        super("teambalance");
        this.description = "Check balance on current player";
        this.usageMessage = command();
        this.setPermission("gerconomy.player.teambalance");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(this.getPermission())) {
                MessageManager.playerBad(player, "You don't have permission.");
                return true;
            }

            if (args.length != 0) {
                MessageManager.playerBad(player, command());
                MessageManager.playerBad(player, command() + " <player>");
                return true;
            }

            String playerName = player.getName();
            String name = "";
            for (Team team : board.getTeams()) {
                if (team.getEntries().contains(playerName)) {
                    name = team.getName();
                }
            }


            TeamManager teamManager = plugin.teamManagerHashMap.get(name);
            PlayerManager playerManager = plugin.playerManagerHashMap.get(player.getUniqueId());
            if (playerManager.getTeam().equalsIgnoreCase(teamManager.getName())) {
                double teamBalance = teamManager.getBalance();

                if (teamBalance > 0) {
                    MessageManager.playerGood(player, "Your balance is " + ChatColor.GREEN + "$" + teamBalance);
                } else {
                    MessageManager.playerBad(player, "You do not have an account");
                }
            } else {
                MessageManager.playerBad(player, "You are not in a team");
            }
        }
        return true;
    }

    private String command(){
        return "/teambalance";
    }
}
