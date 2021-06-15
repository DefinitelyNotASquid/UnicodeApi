package com.mith.UnicodeApi;

import com.mith.UnicodeApi.Objects.Unicode;
import com.mith.UnicodeApi.Objects.UnicodeType;
import com.mith.UnicodeApi.commands.ApiTest;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UnicodeApi extends JavaPlugin implements Listener {
	
	public InstanceManager instanceManager;

	public static List<Unicode> UniCodeList = new ArrayList<>();

	public static List<Unicode> getUniCodeList() {
		return UniCodeList;
	}

	public void onEnable(){
		loadUnicodeCharacters();
		instanceManager = new InstanceManager();
		loadCommands();
	}

	private void loadUnicodeCharacters() {
		loadUnicodeLib("/Emoji.txt", UnicodeType.EMOJI);
		loadUnicodeLib("/Minecraft.txt", UnicodeType.MINECRAFT);
		//TODO gotta change it so that if the optional *hear me out liam* boolean is passed in we also register the custom model data
		//loadUnicodeLib("/Emoji.txt", UnicodeType.DISEASES);
		//loadUnicodeLib("/Emoji.txt", UnicodeType.HARSHENVIRONMENTS);
		//loadUnicodeLib("/Emoji.txt", UnicodeType.COSMETICSKIN);
	}

	private void loadCommands() {
		getCommand("apitest").setExecutor(new ApiTest());
	}

	private void loadUnicodeLib(String path, UnicodeType type){
		try {
			InputStream listInput = getClass().getResourceAsStream(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(listInput, "UTF-8"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#")) { // Ignored lines
					continue;
				}
				String[] arrOfStr  = line.split(":");
				Unicode unicodeCharacter = new Unicode(arrOfStr[0], type, arrOfStr[1]);
				UniCodeList.add(unicodeCharacter);
			}
			bufferedReader.close();
			listInput.close();
		} catch (Exception e) {
			System.out.println("An error occured while loading " + type.name() + ". More info below.");
			e.printStackTrace();
		}
	}
}
