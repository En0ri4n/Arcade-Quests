package fr.eno.arcadequests.utils;

import fr.eno.arcadequests.bosses.*;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.entity.*;

import java.util.*;

public class Utils
{
    private static final Random random = new Random();
    private static final List<Character> colors = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');

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
