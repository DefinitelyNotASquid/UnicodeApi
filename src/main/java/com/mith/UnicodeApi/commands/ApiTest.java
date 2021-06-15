package com.mith.UnicodeApi.commands;

import com.mith.UnicodeApi.Objects.Unicode;
import com.mith.UnicodeApi.UnicodeApi;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ApiTest implements CommandExecutor {
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {

        if(commandSender instanceof  Player)
        {
          Player player = (Player)commandSender;
          List<Unicode> unicodeList =  UnicodeApi.getUniCodeList();
          String unicodeString = "";
          for (Unicode u: unicodeList) {
                unicodeString = unicodeString + u.getUnicodeCharacter();
          }
          player.sendMessage(unicodeString);
        }
        return true;
    }
}
