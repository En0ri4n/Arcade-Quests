package fr.eno.arcadequests.bosses;

import fr.eno.arcadequests.*;
import fr.eno.arcadequests.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class BossManager implements Listener
{
    @EventHandler
    public void onBossDeath(EntityDeathEvent e)
    {
        if(e.getEntity().getScoreboardTags().contains("AQ_BOSS"))
        {
            if(e.getEntity().getKiller() != null)
            {
                e.getEntity().getKiller().sendMessage(ArcadeQuests.getPrefix() + ChatColor.AQUA + "Bravo, tu as battu " + ChatColor.DARK_PURPLE + e.getEntity().getCustomName() + ChatColor.AQUA + " !");
            }

            e.getEntity().getPassengers().forEach(Entity::remove);
            if(e.getEntity().getVehicle() != null) e.getEntity().getVehicle().remove();
            e.getDrops().clear();
            long level = NBTUtils.getLevel((Creature) e.getEntity());
            e.getDrops().add(BossAutel.getSummoner(level + 1L).getSummonerItem());
            e.setDroppedExp(Math.toIntExact(level * 3L));
        }
    }

    @EventHandler
    public void onBossDamageOnBlocks(EntityDamageByBlockEvent e)
    {
        if(e.getEntity().getScoreboardTags().contains("AQ_BOSS_PASSENGER") && e.getEntity().getScoreboardTags().contains("AQ_BOSS"))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBossDamage(EntityDamageByEntityEvent e)
    {
        if(e.getEntity().getScoreboardTags().contains("AQ_BOSS_PASSENGER") && e.getEntity() instanceof Creature)
        {
            Creature passenger = (Creature) e.getEntity();
            Creature boss = (Creature) passenger.getVehicle();
            if(boss != null)
                boss.damage(e.getDamage(), e.getDamager());
            e.setCancelled(true);
        }
    }
}