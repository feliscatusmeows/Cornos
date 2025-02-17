/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Enchant
# Created by constantin at 07:53, Mär 26 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.command.Command;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;

public class Enchant extends Command {
    public Enchant() {
        super("Enchant", "Enchants your item", new String[]{"enchant", "en"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 2) {
            STL.notifyUser("Syntax: enchant <level> <full enchantment name>");
            STL.notifyUser("All enchantments you can apply:");
            for (Field field : Enchantments.class.getFields()) {
                try {
                    field.setAccessible(true);
                    Enchantment bruh = (Enchantment) field.get(Enchantments.class);
                    STL.notifyUser("  - " + new TranslatableText(bruh.getTranslationKey()).getString() + "");
                } catch (Exception ignored) {

                }
            }
            return;
        }
        if (!STL.tryParseI(args[0])) {
            STL.notifyUser("Homie idk if the enchantment level is valid");
            return;
        }
        assert Cornos.minecraft.player != null;
        if (Cornos.minecraft.player.inventory.getStack(Cornos.minecraft.player.inventory.selectedSlot).isEmpty()) {
            STL.notifyUser("idk if you are holding an item ngl");
            return;
        }
        int level = Integer.parseInt(args[0]);
        String[] argsTrimmed = ArrayUtils.subarray(args, 1, args.length);
        boolean found = false;
        for (Field field : Enchantments.class.getFields()) {
            try {
                field.setAccessible(true);
                Enchantment bruh = (Enchantment) field.get(Enchantments.class);
                String name = new TranslatableText(bruh.getTranslationKey()).getString();
                if (name.equalsIgnoreCase(String.join(" ", argsTrimmed))) {
                    found = true;
                    ItemStack is = Cornos.minecraft.player.inventory.getStack(Cornos.minecraft.player.inventory.selectedSlot);
                    CompoundTag ct = is.getOrCreateTag();
                    if (!ct.contains("Enchantments", 9)) ct.put("Enchantments", new ListTag());

                    ListTag lt = ct.getList("Enchantments", 10);
                    CompoundTag ct1 = new CompoundTag();
                    ct1.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(bruh)));
                    ct1.putInt("lvl", level);
                    lt.add(ct1);
                    break;
                }
            } catch (Exception ignored) {
                STL.notifyUser("Failed to look up enchantments for some reason");
                break;
            }
        }
        if (!found) STL.notifyUser("homie idk if thats an enchantment i know of");
        super.onExecute(args);
    }
}
