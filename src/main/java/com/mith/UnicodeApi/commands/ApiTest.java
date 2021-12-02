package com.mith.UnicodeApi.commands;

import com.mith.UnicodeApi.Objects.Unicode;
import com.mith.UnicodeApi.UnicodeApi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ApiTest implements CommandExecutor {
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {

        if (!commandSender.hasPermission("unicodeapi.apicommands")) {
            commandSender.sendMessage(ChatColor.RED + "You need " + ChatColor.GOLD + "unicodeapi.apicommands" + ChatColor.RED + " to use this command.");
            return true;
        }

        if(commandSender instanceof Player)
        {
          Player player = (Player)commandSender;
          List<Unicode> unicodeList =  UnicodeApi.getUniCodeList();
          String unicodeString = "";
          int delay = 0;
          for (Unicode u: unicodeList) {
              unicodeString = unicodeString + u.getUnicodeCharacter() + " " + u.getName() + "\n";
              if(countLines(unicodeString) >= 10){
                  final String copy = unicodeString;
                  new BukkitRunnable() {
                      @Override
                      public void run() {
                          player.sendMessage(copy);
                      }
                  }.runTaskLater(UnicodeApi.getPlugin(), 20L * delay);
                  delay = delay + 1;
                  unicodeString = "";
              }
          }
        }
        return true;
    }

    private static int countLines(String str){
        String[] lines = str.split("\n");
        return  lines.length;
    }
}
