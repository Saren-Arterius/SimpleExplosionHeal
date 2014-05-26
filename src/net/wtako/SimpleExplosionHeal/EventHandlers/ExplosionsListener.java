package net.wtako.SimpleExplosionHeal.EventHandlers;

import java.text.MessageFormat;
import java.util.Random;

import net.wtako.SimpleExplosionHeal.Main;
import net.wtako.SimpleExplosionHeal.Utils.Lang;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class ExplosionsListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (Main.getInstance().getConfig().getBoolean("variable.Disable")) {
            return;
        }

        if (Main.getInstance().getConfig().getBoolean("system.WorldGuardSupport")) {
            try {
                final WorldGuardPlugin worldGuard = ExplosionsListener.getWorldGuard();
                final RegionManager regionManager = worldGuard.getRegionManager(event.getLocation().getWorld());
                final ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitUtil.toVector(event
                        .getLocation()));
                if (!set.allows(DefaultFlag.VINE_GROWTH)) {
                    ExplosionsListener.recoverExplosionEvent(event);
                }
            } catch (final Error e) {
                Main.log.info(MessageFormat.format(Lang.ERROR_HOOKING.toString(), "WorldGuard"));
                return;
            }
        } else {
            ExplosionsListener.recoverExplosionEvent(event);
        }
    }

    private static void recoverExplosionEvent(EntityExplodeEvent event) {
        final int HealDelay = Main.getInstance().getConfig().getInt("variable.HealDelay");
        final int FullHealTime = Main.getInstance().getConfig().getInt("variable.FullHealTime");
        final boolean AllowTNTChainReaction = Main.getInstance().getConfig()
                .getBoolean("variable.AllowTNTChainReaction");
        for (final Block block: event.blockList()) {
            final Material mat = block.getType();
            final Location loc = block.getLocation();
            final String world = loc.getWorld().getName();

            int healTime;
            if (mat == Material.CARPET || mat == Material.SIGN_POST || mat == Material.WALL_SIGN) {
                healTime = FullHealTime + HealDelay + new Random().nextInt(FullHealTime) + HealDelay;
            } else {
                healTime = new Random().nextInt(FullHealTime) + HealDelay;
            }

            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Main.getInstance().getServer().getWorld(world).getBlockAt(loc).setType(mat);
                }
            }, healTime);
            if (AllowTNTChainReaction) {
                if (block.getType() != Material.TNT) {
                    block.setType(Material.AIR);
                }
            } else {
                block.setType(Material.AIR);
            }
        }
    }

    private static WorldGuardPlugin getWorldGuard() {
        final Plugin plugin = Main.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }
}
