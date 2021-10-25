package fr.eno.arcadequests.utils;

import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

public enum BossAutel
{
    BASE(0L, Material.WOODEN_SWORD, Material.COBBLESTONE, Material.OAK_PLANKS, Material.GRANITE, Material.TORCH),
    BASE_1(1L, Material.BLAZE_POWDER, Material.ACACIA_WOOD, Material.COAL_BLOCK, Material.COPPER_ORE, Material.FLOWER_POT);

    private final long level;
    private final Material summoner;
    private final Material base;
    private final Material corners;
    private final Material top;
    private final Material cornersDeco;

    BossAutel(long level, Material summoner, Material base, Material corners, Material top, Material cornersDeco)
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

    public static BossAutel getSummoner(long level)
    {
        for(BossAutel autel : BossAutel.values())
        {
            if(level == autel.getLevel()) return autel;
        }

        return BossAutel.BASE;
    }

    public static boolean isSummonerItem(ItemStack stack)
    {
        return NBTUtils.getTagLevel(stack) > -1L;
    }


    public static BossAutel getSummoner(ItemStack stack)
    {
        return getSummoner(NBTUtils.getTagLevel(stack));
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