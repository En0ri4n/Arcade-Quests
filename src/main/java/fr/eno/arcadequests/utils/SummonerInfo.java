package fr.eno.arcadequests.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum SummonerInfo
{
    BASE(Material.BOWL, StructureInfo.BASE);

    private final Material item;
    private final StructureInfo structureInfo;

    SummonerInfo(Material item, StructureInfo info)
    {
        this.item          = item;
        this.structureInfo = info;
    }

    public ItemStack getSummonerItem()
    {
        ItemStack stack = new ItemStack(this.item);
        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        stackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        stackMeta.setDisplayName(Utils.getRainbowString("Boss Summoner"));
        stackMeta.setUnbreakable(true);
        stackMeta.setLore(Arrays.asList("", ChatColor.GREEN + "Level " + ChatColor.GOLD + this.getLevel(), "", ChatColor.RED + "" + ChatColor.ITALIC + "Clique droit sur la structure", ChatColor.RED + "" + ChatColor.ITALIC + "pour invoquer un Boss !"));
        stack.setItemMeta(stackMeta);
        return NBTUtils.setLevelTag(stack, this.getLevel());
    }

    public long getLevel() { return this.structureInfo.getLevel(); }

    public Material getItem()
    {
        return item;
    }

    public StructureInfo getStructureInfo()
    {
        return structureInfo;
    }

    public static boolean isSummonerItem(ItemStack stack)
    {
        return NBTUtils.getLevelTag(stack) > -1L;
    }

    public static SummonerInfo getSummonerItem(long level)
    {
        for(SummonerInfo info : SummonerInfo.values())
        {
            if(level == info.getLevel()) return info;
        }

        return SummonerInfo.BASE;
    }

    public static StructureInfo getStructureInfo(ItemStack stack)
    {
        return getSummonerItem(NBTUtils.getLevelTag(stack)).getStructureInfo();
    }
}
