package de.jeff_media.CobbleGen;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CobbleListener implements Listener {
    private final Main main;

    public CobbleListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onCobble(BlockBreakEvent event) {
        Block block = event.getBlock();

        Block blockAbove = block.getRelative(BlockFace.UP);
        Block blockDown = block.getRelative(BlockFace.DOWN);
        if (blockAbove.getType() != Material.WATER) {
            return;
        }
        if (blockDown.getType() != Material.LAVA) {
            return;
        }


        Material newMat = main.probabilityUtils.getMaterial();

        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            block.setType(newMat);
        }, 1L);
    }

    @EventHandler
    public void onWaterPlaceInNether(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {

            return;
        }

        Block block = event.getClickedBlock();
        if (block.getWorld().getEnvironment() != World.Environment.NETHER) {

            return;
        }


        ItemStack item = event.getItem();
        if (item == null) {

            return;
        }

        if (item.getType() != Material.WATER_BUCKET) {

            return;
        }

        if (event.getItem().getAmount() != 1) {

            return;
        }


        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            event.getPlayer().getInventory().remove(item);

            BlockFace face = event.getBlockFace();
            block.getRelative(face).setType(Material.WATER);
        }, 1l);

    }
}
