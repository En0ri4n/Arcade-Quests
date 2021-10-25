package fr.eno.arcadequests.listeners;

import fr.eno.arcadequests.bosses.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class BossListener implements Listener
{
    @EventHandler
    public void onBossBurn(EntityCombustEvent event)
    {
        Entity entity = event.getEntity();
        World world = entity.getWorld();
        Block block = world.getBlockAt(entity.getLocation());

        if(block.getLightFromSky() > 11)
            if(block.getType() != Material.LAVA && block.getType() != Material.FIRE)
                if(entity.getScoreboardTags().contains(AQBoss.AQ_BOSS_TAG))
                    event.setCancelled(true);
    }
}
