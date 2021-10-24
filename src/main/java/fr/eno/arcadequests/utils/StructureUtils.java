package fr.eno.arcadequests.utils;

import fr.eno.arcadequests.*;
import fr.eno.arcadequests.bosses.*;
import org.bukkit.*;
import org.bukkit.block.data.*;
import org.bukkit.entity.*;

public class StructureUtils
{
    public static void clearAutelStructureAndSummon(Location location, World world, Player player, Autel autel)
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
            Bosses.getBoss(autel.getLevel()).summonBoss(world, location.clone().add(0.5D, 0, 0.5D), player);
            world.strikeLightningEffect(location);
        }, index);

    }

    public static boolean checkAutelStructure(Location location, World world, Autel info)
    {
        boolean isBased = checkBase(location, world, info);
        boolean hasTop = isType(world, location, info.getTop());
        boolean hasTopDeco = isType(world, location.clone().add(0, 1, 0), info.getCornersDeco());
        return isBased && hasTop && hasTopDeco;
    }

    private static boolean checkBase(Location loc, World world, Autel info)
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

    public static void summonPhantomAutel(Location loca, World world, Autel infos)
    {
        Location loc = Utils.toBlockLocation(loca);

        createFallingBlock(world, loc.clone(), infos.getTop().createBlockData());
        createFallingBlock(world, loc.clone().add(0, 1, 0), infos.getCornersDeco().createBlockData());

        Location location = loc.clone().add(-1D, -1, -1D);

        int index = 0;
        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
            {
                Location l = new Location(world, location.getX() + (double) x, location.getBlockY(), location.getZ() + (double) z);

                if(index == 0 || index == 2 || index == 6 || index == 8)
                {
                    createFallingBlock(world, l, infos.getCorners().createBlockData());
                    createFallingBlock(world, l.clone().add(0, 1, 0), infos.getCornersDeco().createBlockData());
                }
                else
                {
                    createFallingBlock(world, l, infos.getBase().createBlockData());
                }

                index++;
            }
    }

    private static void createFallingBlock(World world, Location loc, BlockData data)
    {
        FallingBlock fallingBlock = world.spawnFallingBlock(loc, data);
        fallingBlock.setGravity(false);
        fallingBlock.setDropItem(false);
    }

    private static boolean isType(World world, Location loc, Material type)
    {
        return world.getBlockAt(loc).getType().equals(type);
    }
}
