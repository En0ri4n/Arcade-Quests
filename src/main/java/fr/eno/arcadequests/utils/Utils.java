package fr.eno.arcadequests.utils;

import org.bukkit.*;

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

    public static Location toBlockLocation(Location loc)
    {
        return new Location(loc.getWorld(), loc.getBlockX() + 0.5D, loc.getBlockY(), loc.getBlockZ() + 0.5D);
    }
}
