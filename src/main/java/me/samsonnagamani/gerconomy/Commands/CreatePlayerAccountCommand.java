package me.samsonnagamani.gerconomy.Commands;

import me.samsonnagamani.gerconomy.GerConomy;
import me.samsonnagamani.gerconomy.MessageManager;
import me.samsonnagamani.gerconomy.Player.PlayerManager;
import me.samsonnagamani.gerconomy.Team.TeamManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.UUID;

public class CreatePlayerAccountCommand extends BukkitCommand {
    private GerConomy plugin = GerConomy.getPlugin();

    public CreatePlayerAccountCommand() {
        super("createaccount");
        this.description = "Create your economy account";
        this.usageMessage = command();
        this.setPermission("gerconomy.player.createaccount");
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(this.getPermission())) {
                MessageManager.playerBad(player, "You don't have permission.");
                return true;
            }

            if (args.length != 0) {
                MessageManager.playerBad(player, command());
                return true;
            }
            if (!plugin.economyCore.hasAccount(player.getUniqueId().toString())) {
                plugin.mongoConnect.addNewPlayer(player);
            }
        }else{
            MessageManager.consoleBad("Sorry but only players can use");
        }
        return true;
    }

    private String command(){
        return "/createaccount";
    }
}
