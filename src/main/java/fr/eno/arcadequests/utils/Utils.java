package fr.eno.arcadequests.utils;

import fr.eno.arcadequests.bosses.BossManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagLong;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils
{
    private static Random random = new Random();
    private static List<Character> colors = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');

    public static String getRainbowString(String str)
    {
        String output = "";

        for(char chara : str.toCharArray())
        {
            output = output.concat(ChatColor.getByChar(colors.get(random.nextInt(colors.size()))) + String.valueOf(chara));
        }

        return output;
    }

    public static void initializeBossBar(Player player, Creature creature, String name)
    {
        BossBar bar = Bukkit.createBossBar(name, BarColor.values()[random.nextInt(BarColor.values().length)], BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
        bar.addPlayer(player);
        bar.setVisible(true);
        BossManager.BossBarRunnable.runBossBar(bar, creature);
    }

    
}
