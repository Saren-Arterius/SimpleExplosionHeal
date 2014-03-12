package src.net.wtako.SimpleExplosionHeal.EventHandlers;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import src.net.wtako.SimpleExplosionHeal.Main;

public class ExplosionsListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (Main.getInstance().getConfig().getBoolean("variable.Disable")) {
            return;
        }
        final int HealDelay = Main.getInstance().getConfig().getInt("variable.HealDelay");
        final int FullHealTime = Main.getInstance().getConfig().getInt("variable.FullHealTime");
        final boolean AllowTNTChainReaction = Main.getInstance().getConfig()
                .getBoolean("variable.AllowTNTChainReaction");
        for (final Block block: event.blockList()) {
            final Material mat = block.getType();
            final Location loc = block.getLocation();
            final String world = loc.getWorld().getName();
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Main.getInstance().getServer().getWorld(world).getBlockAt(loc).setType(mat);
                }
            }, new Random().nextInt(FullHealTime) + HealDelay);
            if (AllowTNTChainReaction) {
                if (block.getType() != Material.TNT) {
                    block.setType(Material.AIR);
                }
            } else {
                block.setType(Material.AIR);
            }
        }
    }
}
