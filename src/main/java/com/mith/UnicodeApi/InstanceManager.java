package com.mith.UnicodeApi;

import com.bestvike.linq.Linq;
import com.mith.UnicodeApi.Objects.Unicode;
import com.mith.UnicodeApi.Objects.UnicodeType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;


public class InstanceManager {

	static List<Unicode> minecraftUnicode = UnicodeApi.getUniCodeList().stream().filter(u -> u.getType().equals(UnicodeType.MINECRAFT)).collect(Collectors.toList());
	static List<Unicode> emojiUnicode = UnicodeApi.getUniCodeList().stream().filter(u -> u.getType().equals(UnicodeType.EMOJI)).collect(Collectors.toList());
	static List<Unicode> cosmeticUnicode = UnicodeApi.getUniCodeList().stream().filter(u -> u.getType().equals(UnicodeType.COSMETICSKIN)).collect(Collectors.toList());
	static List<Unicode> diseasesUnicode = UnicodeApi.getUniCodeList().stream().filter(u -> u.getType().equals(UnicodeType.DISEASES)).collect(Collectors.toList());
	static List<Unicode> harshEnvironments = UnicodeApi.getUniCodeList().stream().filter(u -> u.getType().equals(UnicodeType.DISEASES)).collect(Collectors.toList());
	static List<Unicode> globalUnicode = Linq.of(emojiUnicode)
											 .concat(Linq.of(harshEnvironments))
											 .concat(Linq.of(minecraftUnicode))
											 .concat(Linq.of(diseasesUnicode))
											 .concat(Linq.of(cosmeticUnicode))
											 .toList();
	/***
	 * Returns Minecraft Item Unicode character, will not take anything else into account.
	 * @param itemStack
	 * @return
	 */
	public String getItemUnicodeCharacter(ItemStack itemStack)
	{
		if(itemStack == null){
			return "";
		}

		return Linq.of(minecraftUnicode)
				   .where(is -> is.getName().equals(itemStack.getType().name()))
				   .firstOrDefault()
				   .getName();
	}

	/***
	 * Returns Cosmetic skin character of an item.
	 * @param itemStack
	 * @return
	 */
	public String getCosmeticSkinUnicodeCharacter(ItemStack itemStack)
	{
		if(itemStack == null){
			return "";
		}

		if(!itemStack.hasItemMeta()){
			return "";
		}

		if(!itemStack.getItemMeta().hasCustomModelData()){
			return "";
		}

		return Linq.of(cosmeticUnicode)
				.where(is -> is.getName().equals(itemStack.getType().name()) &&
						     is.getCustomModelData() == itemStack.getItemMeta().getCustomModelData())
				.firstOrDefault()
				.getName();
	}

	/***
	 * Returns custom model data of non-cosmetic items (HE,DISEASES)
	 * @param itemStack
	 * @return
	 */
	public String getHeUnicodeCharacter(ItemStack itemStack)
	{
		if(itemStack == null){
			return "";
		}

		if(!itemStack.hasItemMeta()){
			return "";
		}

		if(!itemStack.getItemMeta().hasCustomModelData()){
			return "";
		}

		return Linq.of(harshEnvironments)
				.where(is -> is.getName().equals(itemStack.getType().name()) &&
						is.getCustomModelData() == itemStack.getItemMeta().getCustomModelData())
				.firstOrDefault()
				.getName();
	}

	/***
	 * Returns custom model data of non-cosmetic items (DISEASES)
	 * @param itemStack
	 * @return
	 */
	public String getDiseasesUnicodeCharacter(ItemStack itemStack)
	{
		if(itemStack == null){
			return "";
		}

		if(!itemStack.hasItemMeta()){
			return "";
		}

		if(!itemStack.getItemMeta().hasCustomModelData()){
			return "";
		}

		return Linq.of(diseasesUnicode)
				.where(is -> is.getName().equals(itemStack.getType().name()) &&
						is.getCustomModelData() == itemStack.getItemMeta().getCustomModelData())
				.firstOrDefault()
				.getName();
	}

	/**
	 * Returns the Emoji of a given string, if no Emoji is found you will get and empty string back.
	 * @param emojiName
	 * @return
	 */
	public String getEmoji(String emojiName)
	{
		return Linq.of(emojiUnicode)
				.where(emoji -> emojiName.equals(emoji.getName()))
				.firstOrDefault()
				.getName();
	}


	/**
	 * Returns the global list of UnicodeCharacters
	 * @return
	 */
	public List<Unicode> getGlobalList(){
		return globalUnicode;
	}

	/***
	 * Returns the entire unfiltered list for Minecraft Unicode characters.
	 * @return
	 */
	public List<Unicode> getMinecraftUnicode(){
		return minecraftUnicode;
	}

	/***
	 * Returns the entire unfiltered list for Emoji Unicode characters.
	 * @return
	 */
	public List<Unicode> getEmojiUnicode(){
		return emojiUnicode;
	}

	/***
	 * Returns the entire unfiltered list for Cosmetic Unicode characters.
	 * @return
	 */
	public List<Unicode> getCosmeticUnicode(){
		return cosmeticUnicode;
	}

	/***
	 * Returns the entire unfiltered list for Diseases Unicode characters.
	 * @return
	 */
	public List<Unicode> getDiseasesUnicode(){
		return diseasesUnicode;
	}

	/***
	 * Returns the entire unfiltered list for HarshEnvironments Unicode characters.
	 * @return
	 */
	public List<Unicode> getHarshEnvironments(){
		return harshEnvironments;
	}

}