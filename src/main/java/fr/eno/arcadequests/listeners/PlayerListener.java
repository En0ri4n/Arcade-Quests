package fr.eno.arcadequests.listeners;

import fr.eno.arcadequests.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;

import java.util.*;

public class PlayerListener implements Listener
{
    @EventHandler
    public void onClick(PlayerInteractEvent e)
    {
        if(e.hasItem() && Autel.isSummonerItem(e.getItem()))
        {
            Player player = e.getPlayer();
            Autel autel = Autel.getSummoner(e.getItem());

            e.setCancelled(true);

            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && player.isSneaking())
            {
                Location baseLoc = Objects.requireNonNull(e.getClickedBlock()).getLocation();
                Location loc = baseLoc.clone().add(-1, 1, -1);

                boolean canBuild = true;

                for(int x = 0; x < 3; x++)
                    for(int y = 0; y < 3; y++)
                        for(int z = 0; z < 3; z++)
                            if(!player.getWorld().getBlockAt(loc.clone().add(x, y, z)).getType().equals(Material.AIR))
                            {
                                canBuild = false;
                                break;
                            }

                if(canBuild)
                    StructureUtils.summonPhantomAutel(baseLoc.add(0, 2, 0), e.getPlayer().getWorld(), autel);
            }
            else if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            {
                Location loc = Objects.requireNonNull(e.getClickedBlock()).getLocation();
                World world = player.getWorld();

                if(StructureUtils.checkAutelStructure(loc, world, autel))
                {
                    Objects.requireNonNull(e.getItem()).setAmount(e.getItem().getAmount() - 1);
                    StructureUtils.clearAutelStructureAndSummon(loc, world, player, autel);
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerEggThrowEvent e)
    {
        e.getPlayer().getWorld().getEntities().stream().filter(entity -> entity instanceof FallingBlock).forEach(fallingBlock -> fallingBlock.remove());
    }
}
