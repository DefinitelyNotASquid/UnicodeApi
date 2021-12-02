package com.mith.UnicodeApi.commands;

import com.mith.UnicodeApi.UnicodeApi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ApiItem implements CommandExecutor {
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {

        if (!commandSender.hasPermission("unicodeapi.apicommands")) {
            commandSender.sendMessage(ChatColor.RED + "You need " + ChatColor.GOLD + "unicodeapi.apicommands" + ChatColor.RED + " to use this command.");
            return true;
        }

        if(commandSender instanceof Player)
        {
            Player player = (Player)commandSender;
            ItemStack is = player.getInventory().getItemInMainHand();
            String retrieve = UnicodeApi.getPlugin().instanceManager.getFilteredUnicodeCharacter(is);
            player.sendMessage("Character Resolved " + retrieve);
        }
        return true;
    }
}
