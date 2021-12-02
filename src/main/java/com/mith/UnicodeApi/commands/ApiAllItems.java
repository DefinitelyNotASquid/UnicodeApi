package com.mith.UnicodeApi.commands;

import com.mith.UnicodeApi.UnicodeApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ApiAllItems implements CommandExecutor {
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!commandSender.hasPermission("unicodeapi.apicommands")) {
            commandSender.sendMessage(ChatColor.RED + "You need " + ChatColor.GOLD + "unicodeapi.apicommands" + ChatColor.RED + " to use this command.");
            return true;
        }

        if(commandSender instanceof Player)
        {

            Player player = (Player)commandSender;
            List<ItemStack> itemStackList = new ArrayList<>();
            Iterator<Recipe> iter = Bukkit.recipeIterator();
            while (iter.hasNext()) {
                Recipe r = iter.next();

                if(r instanceof BlastingRecipe){
                    final BlastingRecipe iteratedBlast = (BlastingRecipe) r;
                    itemStackList.add(iteratedBlast.getResult());
                    continue;
                }

                if(r instanceof CampfireRecipe){
                    final CampfireRecipe iteratedCamp = (CampfireRecipe) r;
                    itemStackList.add(iteratedCamp.getResult());
                    continue;
                }
                if(r instanceof FurnaceRecipe){
                    final FurnaceRecipe iteratedFurnace = (FurnaceRecipe) r;
                    itemStackList.add(iteratedFurnace.getResult());
                    continue;
                }

                if(r instanceof MerchantRecipe){
                    final MerchantRecipe merchantRecipe = (MerchantRecipe) r;
                    itemStackList.add(merchantRecipe.getResult());
                    continue;
                }

                if(r instanceof ShapedRecipe){
                    final ShapedRecipe shapedRecipe = (ShapedRecipe) r;
                    itemStackList.add(shapedRecipe.getResult());
                    continue;
                }

                if(r instanceof ShapelessRecipe){
                    final ShapelessRecipe shapelessRecipe = (ShapelessRecipe) r;
                    itemStackList.add(shapelessRecipe.getResult());
                    continue;
                }

                if(r instanceof SmithingRecipe){
                    final SmithingRecipe smithingRecipe = (SmithingRecipe) r;
                    itemStackList.add(smithingRecipe.getResult());
                    continue;
                }

                if(r instanceof SmokingRecipe){
                    final SmokingRecipe smokingRecipe = (SmokingRecipe) r;
                    itemStackList.add(smokingRecipe.getResult());
                    continue;
                }

                if(r instanceof StonecuttingRecipe){
                    final StonecuttingRecipe stonecuttingRecipe = (StonecuttingRecipe) r;
                    itemStackList.add(stonecuttingRecipe.getResult());
                }
            }

            for(PotionType potionType : PotionType.values()){
                //Create the tipped arrow with potion data
                ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, 1);
                PotionMeta arrowMeta = (PotionMeta)arrow.getItemMeta();
                arrowMeta.setBasePotionData(new PotionData(potionType));
                arrow.setItemMeta(arrowMeta);

                //Create the potion used in the recipe
                ItemStack potion = new ItemStack(Material.POTION);
                PotionMeta potionMeta = (PotionMeta)potion.getItemMeta();
                potionMeta.setBasePotionData(new PotionData(potionType));
                potion.setItemMeta(potionMeta);

                itemStackList.add(potion);
                itemStackList.add(arrow);
            }


            itemStackList = itemStackList.stream().distinct().collect(Collectors.toList());
            itemStackList.sort((ItemStack s1, ItemStack s2) -> s1.getType().name().compareToIgnoreCase(s2.getType().name()));
            String unicodeString = "";
            int delay = 0;
            for (ItemStack i: itemStackList) {
                String result = UnicodeApi.getPlugin().instanceManager.getFilteredUnicodeCharacter(i);
                if(result.equals("")){
                    System.out.println("Error Finding Unicode Character for " + i.getType().name() + " This maybe due to a few factors \n");
                }
                unicodeString = unicodeString + i.getType().name() + " " + result + "\n";
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
