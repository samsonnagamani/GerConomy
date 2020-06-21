package me.samsonnagamani.gerconomy.Commands;

import me.samsonnagamani.gerconomy.GerConomy;
import me.samsonnagamani.gerconomy.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class BalanceCommand extends BukkitCommand {
    private GerConomy plugin = GerConomy.getPlugin();


    public BalanceCommand() {
        super("balance");
        this.description = "Check balance on current player";
        this.usageMessage = command();
        this.setPermission("gerconomy.player.balance");
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
//                MessageManager.playerBad(player, command() + " <player>");
                return true;
            }

            double balance = plugin.economyCore.getBalance(player.getUniqueId());

            if (balance > 0) {
                MessageManager.playerGood(player, "Your balance is " + ChatColor.GREEN + "£" + balance);
            } else {
                MessageManager.playerBad(player, "You do not have an account");
            }
        }
        return true;
    }

    private String command(){
        return "/balance";
    }
}
