package com.mith.UnicodeApi.commands;

import com.mith.UnicodeApi.UnicodeApi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ApiName implements CommandExecutor {
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {

        if (!commandSender.hasPermission("unicodeapi.apicommands")) {
            commandSender.sendMessage(ChatColor.RED + "You need " + ChatColor.GOLD + "unicodeapi.apicommands" + ChatColor.RED + " to use this command.");
            return true;
        }

        if(commandSender instanceof Player)
        {
            Player player = (Player)commandSender;
            String param = array[0];
            String retrieve = UnicodeApi.getPlugin().instanceManager.getItemUnicodeCodeFromString(param);
            player.sendMessage("Character Resolved " + retrieve);
        }
        return true;
    }
}