package src.net.wtako.SimpleExplosionHeal.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import src.net.wtako.SimpleExplosionHeal.Commands.Mlimit.ArgReload;

public class CommandSeh implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                new ArgReload(sender);
                return true;
            }
        }
        return false;
    }
}
