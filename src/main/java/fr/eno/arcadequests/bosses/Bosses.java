package fr.eno.arcadequests.bosses;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import java.util.*;

public class Bosses
{
    public static final AQBoss CHICKEN = new AnimalBoss(0L, EntityType.CHICKEN, 10D, 1D, 0.4D, ChatColor.YELLOW + "Chicken 'n Pigs");

    public static final List<AQBoss> BOSSES = Arrays.asList(CHICKEN);

    public static AQBoss getBoss(long level)
    {
        return BOSSES.stream().filter(boss -> boss.getLevel() == level).findFirst().orElse(null);
    }
}
