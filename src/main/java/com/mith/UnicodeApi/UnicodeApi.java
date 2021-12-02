package com.mith.UnicodeApi;

import com.mith.UnicodeApi.Objects.Unicode;
import com.mith.UnicodeApi.Objects.UnicodeType;
import com.mith.UnicodeApi.commands.ApiAllItems;
import com.mith.UnicodeApi.commands.ApiItem;
import com.mith.UnicodeApi.commands.ApiName;
import com.mith.UnicodeApi.commands.ApiTest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UnicodeApi extends JavaPlugin implements Listener {
	
	public InstanceManager instanceManager;
	private static UnicodeApi plugin;
	public static List<Unicode> UniCodeList = new ArrayList<>();

	public static List<Unicode> getUniCodeList() {
		return UniCodeList;
	}

	public void onEnable(){
		plugin = this;
		loadUnicodeCharacters();
		instanceManager = new InstanceManager();
		loadCommands();
	}

	private void loadUnicodeCharacters() {
		loadUnicodeLib("/Minecraft.txt", UnicodeType.MINECRAFT);
		loadUnicodeLib("/Emoji.txt", UnicodeType.EMOJI);
		loadUnicodeLib("/Discord.txt", UnicodeType.DISCORD);
	}

	private void loadCommands() {
		getCommand("apitest").setExecutor(new ApiTest());
		getCommand("apiname").setExecutor(new ApiName());
		getCommand("apiitem").setExecutor(new ApiItem());
		getCommand("apiallitems").setExecutor(new ApiAllItems());
	}
	public static UnicodeApi getPlugin() { return plugin; }
	private void loadUnicodeLib(String path, UnicodeType type){
		try {
			InputStream listInput = getClass().getResourceAsStream(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(listInput, "UTF-8"));
			List<String> iconBlacklist = getBlacklist(plugin.getConfig());
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#")) { // Ignored lines
					continue;
				}
				String[] arrOfStr  = line.split(":");
				boolean isRestricted = iconBlacklist.contains(arrOfStr[1]);
				Unicode unicodeCharacter = new Unicode(arrOfStr[0], type, arrOfStr[1], isRestricted);
				UniCodeList.add(unicodeCharacter);
			}
			bufferedReader.close();
			listInput.close();
		} catch (Exception e) {
			System.out.println("An error occured while loading " + type.name() + ". More info below.");
			e.printStackTrace();
		}
	}

	public List<String> getBlacklist(FileConfiguration config){
		List<String> iconBlackList = new ArrayList<>();
		for (String blacklistUnicode : config.getStringList("icon-blacklist")) {
			if (blacklistUnicode == null) {
				plugin.getLogger().warning("Invalid unicode specified in 'icon-blacklist': '" + blacklistUnicode + "'. Skipping...");
				continue;
			}
			iconBlackList.add(blacklistUnicode); // Remove disabled emojis from the emoji list
		}
		return iconBlackList;
	}
}
