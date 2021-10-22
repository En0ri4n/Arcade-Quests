package fr.eno.arcadequests.utils;

import net.minecraft.nbt.*;
import org.bukkit.craftbukkit.v1_17_R1.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class NBTUtils
{
    public static ItemStack setLevelTag(ItemStack stack, long value)
    {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound stackCompound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        stackCompound.setLong("Level", value);
        nmsStack.setTag(stackCompound);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static long getLevelTag(ItemStack stack)
    {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        if(nmsStack.hasTag())
        {
            if(nmsStack.getTag().hasKey("Level"))
            {
                return ((NBTTagLong) nmsStack.getTag().get("Level")).asLong();
            }
        }

        return -1L;
    }

    public static long getLevel(Creature creature)
    {
        String level = creature.getScoreboardTags().stream().filter(s -> s.contains("LEVEL")).findAny().get().substring(6);

        return Integer.parseInt(level);
    }
}
