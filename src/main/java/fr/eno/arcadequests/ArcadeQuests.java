package fr.eno.arcadequests;

import fr.eno.arcadequests.bosses.BossManager;
import fr.eno.arcadequests.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ArcadeQuests extends JavaPlugin
{
    private static ArcadeQuests INSTANCE;

    @Override
    public void onEnable()
    {
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), getInstance());
        Bukkit.getPluginManager().registerEvents(new BossManager(), getInstance());
    }

    @Override
    public void onDisable()
    {
        Bukkit.getBossBars().forEachRemaining(kbb -> Bukkit.removeBossBar(kbb.getKey()));
    }

    public static ArcadeQuests getInstance()
    {
        return INSTANCE;
    }

    public static String getPrefix()
    {
        return ChatColor.GOLD + "[" + ChatColor.GREEN + "Arcade" + ChatColor.DARK_AQUA + "-" + ChatColor.LIGHT_PURPLE + "Quests" + ChatColor.GOLD + "] ";
    }
}