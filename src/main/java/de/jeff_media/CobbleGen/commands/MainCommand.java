package de.jeff_media.CobbleGen.commands;

import de.jeff_media.CobbleGen.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    private final Main main;

    public MainCommand(Main main) {
        this.main=main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if(args.length == 0) {
            return false;
        }

        switch (args[0].toLowerCase()) {

            case "reload":
                return ReloadCommand.run(main, commandSender, command, alias, args);

            default:
                return false;
        }
    }
}
