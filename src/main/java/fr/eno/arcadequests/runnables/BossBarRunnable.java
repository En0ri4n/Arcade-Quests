package fr.eno.arcadequests.runnables;

import fr.eno.arcadequests.*;
import fr.eno.arcadequests.bosses.*;
import org.bukkit.*;
import org.bukkit.attribute.*;
import org.bukkit.boss.*;
import org.bukkit.entity.*;

import java.util.*;

public class BossBarRunnable implements Runnable
{
    private static final Random random = new Random();

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

    private static void runBossBar(BossBar bar, Creature creature)
    {
        new BossBarRunnable(bar, creature);
    }

    public static void initializeBossBar(Player player, Creature creature, String name)
    {
        BossBar bar = Bukkit.createBossBar(name, BarColor.values()[random.nextInt(BarColor.values().length)], BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
        bar.addPlayer(player);
        bar.setVisible(true);
        runBossBar(bar, creature);
    }
}