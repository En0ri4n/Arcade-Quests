package fr.eno.arcadequests.commands;

import fr.eno.arcadequests.utils.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class GiveSummonerCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if((sender instanceof Player) && args.length == 1)
        {
            Player player = (Player) sender;

            try
            {
                long level = Long.parseLong(args[0]);
                player.getInventory().addItem(BossAutel.getSummoner(level).getSummonerItem());
            }
            catch(NumberFormatException e)
            {
                return false;
            }
        }

        return false;
    }
}
