package fr.eno.arcadequests.utils;

import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

public enum Autel
{
    BASE(0L, Material.BOWL, Material.COBBLESTONE, Material.OAK_PLANKS, Material.GRANITE, Material.TORCH);

    private final long level;
    private final Material summoner;
    private final Material base;
    private final Material corners;
    private final Material top;
    private final Material cornersDeco;

    Autel(long level, Material summoner, Material base, Material corners, Material top, Material cornersDeco)
    {
        this.level = level;
        this.summoner = summoner;
        this.base = base;
        this.corners = corners;
        this.top = top;
        this.cornersDeco = cornersDeco;
    }

    public long getLevel()
    {
        return level;
    }

    public Material getSummoner()
    {
        return summoner;
    }

    public ItemStack getSummonerItem()
    {
        ItemStack stack = new ItemStack(this.summoner);
        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        stackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        stackMeta.setDisplayName(Utils.getRainbowString("Boss Summoner"));
        stackMeta.setUnbreakable(true);
        stackMeta.setLore(Arrays.asList("", ChatColor.GREEN + "Level " + ChatColor.GOLD + this.getLevel(), "", ChatColor.RED + "" + ChatColor.ITALIC + "Clique droit sur la structure", ChatColor.RED + "" + ChatColor.ITALIC + "pour invoquer un Boss !"));
        stack.setItemMeta(stackMeta);
        return NBTUtils.setTagLevel(stack, this.getLevel());
    }

    public static Autel getSummonerItem(long level)
    {
        for(Autel autel : Autel.values())
        {
            if(level == autel.getLevel()) return autel;
        }

        return null;
    }

    public static boolean isSummonerItem(ItemStack stack)
    {
        return NBTUtils.getTagLevel(stack) > -1L;
    }


    public static Autel getSummoner(ItemStack stack)
    {
        return getSummonerItem(NBTUtils.getTagLevel(stack));
    }

    public Material getBase()
    {
        return base;
    }

    public Material getCorners()
    {
        return corners;
    }

    public Material getTop()
    {
        return top;
    }

    public Material getCornersDeco()
    {
        return cornersDeco;
    }
}