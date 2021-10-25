package fr.eno.arcadequests.utils;

import fr.eno.arcadequests.*;
import fr.eno.arcadequests.bosses.*;
import org.bukkit.*;
import org.bukkit.block.data.*;
import org.bukkit.entity.*;

public class StructureUtils
{
    public static void clearAutelStructureAndSummon(Location location, World world, Player player, BossAutel autel)
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

    public static boolean checkAutelStructure(Location location, World world, BossAutel info)
    {
        boolean isBased = checkBase(location, world, info);
        boolean hasTop = isType(world, location, info.getTop());
        boolean hasTopDeco = isType(world, location.clone().add(0, 1, 0), info.getCornersDeco());
        return isBased && hasTop && hasTopDeco;
    }

    private static boolean checkBase(Location loc, World world, BossAutel info)
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

    public static void buildAutel(Player player, Location loc, BossAutel autel)
    {
        if(!player.isOp() || !checkSpace(player.getWorld(), loc)) return;

        World world = player.getWorld();

        removeLastGhostAutel(player);

        world.getBlockAt(loc).setType(autel.getTop());
        world.getBlockAt(loc.clone().add(0, 1, 0)).setType(autel.getCornersDeco());

        Location location = loc.clone().add(-1D, -1, -1D);

        int index = 0;
        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
            {
                Location l = new Location(world, location.getX() + (double) x, location.getBlockY(), location.getZ() + (double) z);

                if(index == 0 || index == 2 || index == 6 || index == 8)
                {
                    world.getBlockAt(l).setType(autel.getCorners());
                    world.getBlockAt(l.clone().add(0, 1, 0)).setType(autel.getCornersDeco());
                }
                else
                {
                    world.getBlockAt(l).setType(autel.getBase());
                }

                index++;
            }
    }

    public static void summonGhostAutel(Player player, Location loca, BossAutel infos)
    {
        World world = player.getWorld();
        Location loc = Utils.toBlockLocation(loca);

        removeLastGhostAutel(player);

        createFallingBlock(player, world, loc.clone(), infos.getTop().createBlockData());
        createFallingBlock(player, world, loc.clone().add(0, 1, 0), infos.getCornersDeco().createBlockData());

        Location location = loc.clone().add(-1D, -1, -1D);

        int index = 0;
        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
            {
                Location l = new Location(world, location.getX() + (double) x, location.getBlockY(), location.getZ() + (double) z);

                if(index == 0 || index == 2 || index == 6 || index == 8)
                {
                    createFallingBlock(player, world, l, infos.getCorners().createBlockData());
                    createFallingBlock(player, world, l.clone().add(0, 1, 0), infos.getCornersDeco().createBlockData());
                }
                else
                {
                    createFallingBlock(player, world, l, infos.getBase().createBlockData());
                }

                index++;
            }
    }

    public static boolean checkSpace(World world, Location baseLoc)
    {
        Location loc = baseLoc.clone().add(-1, 1, -1);

        for(int x = 0; x < 3; x++)
            for(int y = 0; y < 3; y++)
                for(int z = 0; z < 3; z++)
                    if(!world.getBlockAt(loc.clone().add(x, y, z)).getType().equals(Material.AIR))
                    {
                        return false;
                    }

        return true;
    }

    private static void createFallingBlock(Player player, World world, Location loc, BlockData data)
    {
        FallingBlock fallingBlock = world.spawnFallingBlock(loc, data);
        fallingBlock.setGravity(false);
        fallingBlock.setDropItem(false);
        fallingBlock.addScoreboardTag("GHOST_AUTEL_" + player.getName());
    }

    private static void removeLastGhostAutel(Player player)
    {
        player.getWorld().getEntities().stream()
                .filter(entity -> entity instanceof FallingBlock && entity.getScoreboardTags().contains("GHOST_AUTEL_" + player.getName()))
                .forEach(Entity::remove);
    }

    private static boolean isType(World world, Location loc, Material type)
    {
        return world.getBlockAt(loc).getType().equals(type);
    }
}
