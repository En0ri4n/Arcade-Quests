package fr.eno.arcadequests;

import fr.eno.arcadequests.bosses.*;
import fr.eno.arcadequests.commands.*;
import fr.eno.arcadequests.listeners.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;

import java.util.*;

public class ArcadeQuests extends JavaPlugin
{
    private static ArcadeQuests INSTANCE;
    private final List<Listener> listeners = new ArrayList<>();

    @Override
    public void onEnable()
    {
        INSTANCE = this;
        registerListeners();
        listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, getInstance()));
        Bukkit.getPluginCommand("givesummoner").setExecutor(new GiveSummonerCommand());
    }

    private void registerListeners()
    {
        listeners.add(new PlayerListener());
        listeners.add(new BossManager());
        listeners.add(new BossListener());
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