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
        if(e.hasItem() && BossAutel.isSummonerItem(e.getItem()))
        {
            Player player = e.getPlayer();
            BossAutel autel = BossAutel.getSummoner(e.getItem());

            e.setCancelled(true);

            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            {
                World world = player.getWorld();
                Location baseLoc = Objects.requireNonNull(e.getClickedBlock()).getLocation();

                if(player.isSneaking())
                {
                    if(StructureUtils.checkSpace(world, baseLoc))
                        StructureUtils.summonGhostAutel(e.getPlayer(), baseLoc.clone().add(0, 2, 0), autel);

                    return;
                }

                if(StructureUtils.checkAutelStructure(baseLoc, world, autel))
                {
                    Objects.requireNonNull(e.getItem()).setAmount(e.getItem().getAmount() - 1);
                    StructureUtils.clearAutelStructureAndSummon(baseLoc, world, player, autel);
                }
            }
            else if(e.getAction().equals(Action.LEFT_CLICK_BLOCK))
            {
                Location baseLoc = Objects.requireNonNull(e.getClickedBlock()).getLocation();

                if(player.isSneaking() && player.isOp())
                {
                    StructureUtils.buildAutel(player, baseLoc.clone().add(0, 2, 0), autel);
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerEggThrowEvent e)
    {
        e.getPlayer().getWorld().getEntities().stream().filter(entity -> entity instanceof FallingBlock).forEach(Entity::remove);
    }
}
