package src.net.wtako.SimpleExplosionHeal.Commands.Mlimit;

import org.bukkit.command.CommandSender;

import src.net.wtako.SimpleExplosionHeal.Main;
import src.net.wtako.SimpleExplosionHeal.Utils.Lang;

public class ArgReload {

    public ArgReload(CommandSender sender) {
        if (!sender.hasPermission("SimpleExplosionHeal.reload")) {
            sender.sendMessage(Lang.NO_PERMISSION_COMMAND.toString());
            return;
        }
        Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        Main.getInstance().getServer().getPluginManager().enablePlugin(Main.getInstance());
        Main.getInstance().reloadConfig();
        sender.sendMessage(Lang.PLUGIN_RELOADED.toString());
    }

}
