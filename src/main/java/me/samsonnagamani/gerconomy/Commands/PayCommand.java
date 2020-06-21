package me.samsonnagamani.gerconomy.Commands;

import me.samsonnagamani.gerconomy.GerConomy;
import me.samsonnagamani.gerconomy.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PayCommand extends BukkitCommand {
    private GerConomy plugin = GerConomy.getPlugin();

    public PayCommand() {
        super("pay");
        this.description = "Pay another player";
        this.usageMessage = command() + " <amount> <player>";
        this.setPermission("gerconomy.player.pay");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (!player.hasPermission(this.getPermission())) {
                MessageManager.playerBad(player, "You don't have permission.");
                return true;
            }

            if (args.length == 2) {
                double amount = Double.parseDouble(args[0]);
                String playername = args[1];
                Player receiver = plugin.getServer().getPlayer(playername);

                if (receiver == null) {
                    MessageManager.playerBad(player, "Sorry but we cant send your money to no one! " + playername + " does not exist or is not online");
                    return true;
                }

                plugin.economyCore.withdrawPlayer(uuid, amount);
                plugin.economyCore.depositPlayer(receiver.getUniqueId(), amount);


            } else {
                MessageManager.playerBad(player, this.usageMessage);
            }
        }
        return true;
    }

    private String command() {
        return "/pay";
    }
}
