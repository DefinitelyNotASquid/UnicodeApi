package com.mith.UnicodeApi;

import com.bestvike.linq.Linq;
import com.mith.UnicodeApi.Objects.Unicode;
import com.mith.UnicodeApi.Objects.UnicodeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.List;
import java.util.stream.Collectors;

public class InstanceManager {

	static List<Unicode> minecraftUnicode = Linq.of(UnicodeApi.getUniCodeList())
			.where(cu -> cu.getType().equals(UnicodeType.MINECRAFT))
			.orderBy(Unicode::getName)
			.toList();

	static List<Unicode> emojiUnicode = Linq.of(UnicodeApi.getUniCodeList())
			.where(cu -> cu.getType().equals(UnicodeType.EMOJI))
			.orderBy(Unicode::getName)
			.toList();

	static List<Unicode> globalUnicodeFiltered =  Linq.of(UnicodeApi.getUniCodeList())
			.where(cu -> !cu.getRestricted())
			.orderBy(Unicode::getName)
			.toList();

	static List<Unicode> discordUnicode = Linq.of(UnicodeApi.getUniCodeList())
			.where(cu -> cu.getType().equals(UnicodeType.DISCORD))
			.orderBy(Unicode::getName)
			.toList();

	static List<Unicode> globalUnicode = Linq.of(UnicodeApi.getUniCodeList())
											 .concat(Linq.of(minecraftUnicode))
											 .concat(Linq.of(discordUnicode))
											 .toList();

	static List<Unicode> blacklistUnicode = Linq.of(UnicodeApi.getUniCodeList())
			.where(Unicode::getRestricted)
			.orderBy(Unicode::getName)
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

	public String getItemUnicodeCodeFromString(String param){

		if(param.isEmpty()){
			return "";
		}

		Unicode result = Linq.of(minecraftUnicode)
				.where(is -> is.getName().equals(param))
				.firstOrDefault();

		if(result == null){
			System.out.println(", parameter passed was: " + param);
			return "";
		}

		return result.getUnicodeCharacter();
	}

	public String getFilteredUnicodeCharacter(ItemStack itemStack)
	{
		if(itemStack == null){
			return "";
		}

		return getItemUnicodeCodeFromString(filterItemStackInstance(itemStack));
	}

	public String filterItemStackInstance(ItemStack itemStack) {

		ItemMeta itemMetaData = itemStack.getItemMeta();

		if (itemMetaData instanceof PotionMeta) {

			String potionInfo = ((PotionMeta) itemMetaData).getBasePotionData().getType().toString();
			String itemstackType = itemStack.getType().toString();

			switch(potionInfo) {
				case "AWKWARD":
				case "THICK":
				case "MUNDANE":
				case "WATER":
					potionInfo = "WATER";
					break;
				case "JUMP":
					potionInfo = "leaping";
					break;
				case "SPEED":
					potionInfo = "swiftness";
					break;
				case "TURTLE_MASTER":
					potionInfo = "the-turtle-master";
					break;
				case "INSTANT_HEAL":
					potionInfo = "healing";
					break;
				case "REGEN":
					potionInfo = "regeneration";
					break;
				case "INSTANT_DAMAGE":
					potionInfo = "harming";
					break;
				case "SLOW_FALLING":
					potionInfo = "slow-falling";
					break;
				case "NIGHT_VISION":
					potionInfo = "night-vision";
					break;
				case "FIRE_RESISTANCE":
					potionInfo = "fire-resistance";
					break;
				case "WATER_BREATHING":
					potionInfo = "water-breathing";
					break;
				default:

			}
			//For some reason they deprecated the arrow value and called it "TIPPED-ARROW" but in vanilla its still "Arrow"
			if(itemStack.getType() == Material.TIPPED_ARROW){
				itemstackType =   itemStack.getType().toString().replace("TIPPED_ARROW", "arrow-of").toLowerCase();
			}

			if(itemStack.getType() == Material.LINGERING_POTION){
				itemstackType =   itemStack.getType().toString().replace("LINGERING_POTION", "lingering-potion-of").toLowerCase();
				if(potionInfo.equals("WATER")){
					return "lingering-water-bottle";
				}
			}
			if(itemStack.getType() == Material.SPLASH_POTION){
				itemstackType =   itemStack.getType().toString().replace("SPLASH_POTION", "splash-potion-of").toLowerCase();
				if(potionInfo.equals("WATER")){
					return "splash-water-bottle";
				}
			}
			if(itemStack.getType() == Material.POTION){
				itemstackType =   itemStack.getType().toString().replace("POTION", "potion-of").toLowerCase();
				if(potionInfo.equals("WATER")){
					return "water-bottle";
				}
			}

			if(potionInfo.toLowerCase().equals("uncraftable")){
				return  potionInfo.toLowerCase()+ "-" + itemstackType.toLowerCase();
			}

			return itemstackType.toLowerCase() + "-" + potionInfo.toLowerCase();
		}

		//Finding the color for the banner meta on the shield, works mostly havent had any issues with the code.
		if (itemMetaData instanceof BlockStateMeta && itemStack.getType() == Material.SHIELD){
			BlockStateMeta blockStateMeta = (BlockStateMeta) itemMetaData;
			String bannerInfo = blockStateMeta.getBlockState().getType().toString();
			String color = bannerInfo.replace("_BANNER", "").toLowerCase();
			return color + "-" + itemStack.getType().name().toLowerCase();
		}

		return itemStack.getType().toString().replace("_","-").toLowerCase();
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

	public List<Unicode> getDisabledUnicode(){return globalUnicode;}

	/**
	 * Returns the global list of UnicodeCharacters
	 * @return
	 */
	public List<Unicode> getGlobalUnicode(){
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
	public List<Unicode> getDiscordUnicode(){
		return discordUnicode;
	}

	/***
	 * Returns the filtered list for Emoji Unicode characters.
	 * @return
	 */
	public List<Unicode> getGlobalUnicodeFiltered() {return globalUnicodeFiltered;}

	/***
	 * Returns the Black list Unicode characters.
	 * @return
	 */
	public List<Unicode> getBlacklistUnicode(){return blacklistUnicode;}
}