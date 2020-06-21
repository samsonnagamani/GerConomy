package me.samsonnagamani.gerconomy.Commands.Manager;

import me.samsonnagamani.gerconomy.Commands.BalanceCommand;
import me.samsonnagamani.gerconomy.Commands.CreatePlayerAccountCommand;
import me.samsonnagamani.gerconomy.Commands.PayCommand;
import me.samsonnagamani.gerconomy.Commands.TeamBalanceCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class CommandManager {


    public void setup() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register("createaccount", new CreatePlayerAccountCommand());
            commandMap.register("balance", new BalanceCommand());
            commandMap.register("teambalance", new TeamBalanceCommand());
            commandMap.register("pay", new PayCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
