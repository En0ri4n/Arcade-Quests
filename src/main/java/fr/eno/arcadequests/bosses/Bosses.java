package fr.eno.arcadequests.bosses;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import java.util.*;

public class Bosses
{
    public static final List<AQBoss> BOSSES = new ArrayList<>();

    public static AQBoss getBoss(long level)
    {
        return BOSSES.stream().filter(boss -> boss.getLevel() == level).findFirst().orElse(null);
    }

    private static AQBoss addBoss(AQBoss boss)
    {
        BOSSES.add(boss);
        return boss;
    }

    static
    {
        addBoss(new AnimalBoss(0L, EntityType.CHICKEN, 10D, 1D, 0.315468745D, ChatColor.YELLOW + "Crazy Chicken"));
        addBoss(new AnimalBoss(1L, EntityType.COW, 30D, 5D, 0.343248554D, ChatColor.GOLD + "Angry Cow"));
    }
}
