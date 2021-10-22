package fr.eno.arcadequests.bosses;

import fr.eno.arcadequests.ArcadeQuests;
import fr.eno.arcadequests.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

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
            e.getDrops().add(SummonerInfo.getSummonerItem(level + 1L).getSummonerItem());
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
            boss.damage(e.getDamage(), e.getDamager());
            e.setCancelled(true);
        }
    }

    public static class BossBarRunnable implements Runnable
    {
        private final BossBar bossBar;
        private final double maxHealth;
        private final UUID creatureUuid;
        private final int taskId;

        private BossBarRunnable(BossBar bar, Creature creature)
        {
            this.bossBar = bar;
            this.maxHealth = creature.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            this.creatureUuid = creature.getUniqueId();

            this.taskId = Bukkit.getScheduler().runTaskTimer(ArcadeQuests.getInstance(), this, 0L, 10L).getTaskId();
        }

        @Override
        public void run()
        {
            double health = ((Creature) Bukkit.getEntity(creatureUuid)).getHealth();
            double progress = health / this.maxHealth;
            this.bossBar.setProgress(progress);

            if(this.bossBar.getProgress() <= 0D)
            {
                Bukkit.getScheduler().cancelTask(this.taskId);
                this.bossBar.removeAll();
            }
        }

        public static void runBossBar(BossBar bar, Creature creature)
        {
            new BossBarRunnable(bar, creature);
        }
    }
}