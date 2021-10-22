package fr.eno.arcadequests.utils;

import fr.eno.arcadequests.ArcadeQuests;
import fr.eno.arcadequests.bosses.Bosses;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StructureUtils
{
    public static void clearStructureAndSummon(Location location, World world, Player player)
    {
        Location loca = location.clone().add(-1D, 1D, -1D);

        int index = 0;

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 3; z++)
                {
                    int finalZ = z;
                    int finalY = y;
                    int finalX = x;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(ArcadeQuests.getInstance(), () ->
                    {
                        Location loc = loca.clone().add(finalX, -finalY, finalZ);
                        world.playEffect(loc, Effect.STEP_SOUND, world.getBlockAt(loc).getType(), 30);
                        world.playSound(loc, Sound.BLOCK_STONE_BREAK, 0.5F, 1F);
                        world.getBlockAt(loc).setType(Material.AIR);
                    }, index);

                    index = index + 2;
                }

        Bukkit.getScheduler().scheduleSyncDelayedTask(ArcadeQuests.getInstance(), () ->
        {
            Bosses.CHICKEN.summonBoss(world, location.clone().add(0.5D, 0, 0.5D), player);
            world.strikeLightningEffect(location);
        }, index);

    }

    public static boolean checkStructure(Location location, World world, StructureInfo info)
    {
        boolean isBased = checkBase(location, world, info);
        boolean hasTop = isType(world, location, info.getTop());
        boolean hasTopDeco = isType(world, location.clone().add(0, 1, 0), info.getCornersDeco());
        return isBased && hasTop && hasTopDeco;
    }

    private static boolean checkBase(Location loc, World world, StructureInfo info)
    {
        Location location = loc.clone().subtract(1D, 1D, 1D);

        int index = 0;
        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
            {
                Location l = new Location(world, location.getBlockX() + x, location.getBlockY(), location.getBlockZ() + z);

                if(index == 0 || index == 2 || index == 6 || index == 8)
                {
                    if(!isType(world, l, info.getCorners()) || !isType(world, l.clone().add(0, 1, 0), info.getCornersDeco())) return false;
                }
                else
                {
                    if(!isType(world, l, info.getBase())) return false;
                }

                index++;
            }

        return true;
    }

    private static boolean isType(World world, Location loc, Material type)
    {
        return world.getBlockAt(loc).getType().equals(type);
    }
}
