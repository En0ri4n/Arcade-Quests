package fr.eno.arcadequests.listeners;

import fr.eno.arcadequests.bosses.Bosses;
import fr.eno.arcadequests.utils.StructureUtils;
import fr.eno.arcadequests.utils.SummonerInfo;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener
{
    @EventHandler
    public void onClick(PlayerInteractEvent e)
    {
        if(e.hasItem() && SummonerInfo.isSummonerItem(e.getItem()))
        {
            e.setCancelled(true);

            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            {
                Location loc = e.getClickedBlock().getLocation();
                World world = e.getPlayer().getWorld();
                if(StructureUtils.checkStructure(loc, world, SummonerInfo.getStructureInfo(e.getItem())))
                {
                    e.getItem().setAmount(e.getItem().getAmount() - 1);
                    StructureUtils.clearStructureAndSummon(loc, world, e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerDropItemEvent e)
    {
        e.getPlayer().getInventory().addItem(SummonerInfo.BASE.getSummonerItem());
        Bosses.CHICKEN.summonBoss(e.getPlayer().getWorld(), e.getPlayer().getLocation(), e.getPlayer());
    }
}
