/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Hologram
# Created by constantin at 14:38, Mär 18 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.command.impl;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.command.Command;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class Hologram extends Command {
    public Hologram() {
        super("Hologram", "Creates a hologram where you stand", new String[]{"hologram", "holo", "hg", "hlg"});
    }

    public static void spawnHologram(Vec3d pos, String text, boolean egg, boolean baby, String type) {
        ItemStack is = getHoloStack(pos, text, egg, baby, type);
        assert Cornos.minecraft.player != null;
        Cornos.minecraft.player.inventory.addPickBlock(is);
    }

    public static ItemStack getHoloStack(Vec3d pos, String text, boolean egg, boolean baby, String type) {
        ItemStack is = new ItemStack(egg ? Items.BAT_SPAWN_EGG : Items.ARMOR_STAND);
        CompoundTag ct = is.getOrCreateTag();
        ByteTag vulnerable = ByteTag.ONE;
        ByteTag visible = ByteTag.ONE;
        ByteTag nogravity = ByteTag.ONE;
        ByteTag showName = ByteTag.ONE;
        ByteTag small = ByteTag.of(baby);
        String name = text.replaceAll("&", "§").trim();
        ListTag lt1 = new ListTag();
        lt1.add(DoubleTag.of(pos.x));
        lt1.add(DoubleTag.of(pos.y));
        lt1.add(DoubleTag.of(pos.z));
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("Pos", lt1);
        compoundTag.put("CustomName", StringTag.of("{\"text\":\"" + name + "\"}"));
        compoundTag.put("CustomNameVisible", showName);
        compoundTag.put("Invisible", visible);
        compoundTag.put("Invulnerable", vulnerable);
        compoundTag.put("NoGravity", nogravity);
        compoundTag.put("Small", small);
        if (egg) compoundTag.put("id", StringTag.of("minecraft:" + type));
        compoundTag.put("Time", IntTag.of(5));
        ct.put("EntityTag", compoundTag);
        is.setCustomName(Text.of("§r§cHologram §4generated by §lCornos"));
        is.setTag(ct);
        return is;
    }

    public static ItemStack getFallingBlockStack(Vec3d pos, Block b) {
        Identifier id = Registry.BLOCK.getId(b);
        System.out.println(id);
        ItemStack is = new ItemStack(Items.BAT_SPAWN_EGG);
        CompoundTag ct = is.getOrCreateTag();
        CompoundTag ct1 = new CompoundTag();
        ListTag lt1 = new ListTag();
        lt1.add(DoubleTag.of(pos.x));
        lt1.add(DoubleTag.of(pos.y));
        lt1.add(DoubleTag.of(pos.z));
        CompoundTag ct2 = new CompoundTag();
        ct2.put("Name", StringTag.of("minecraft:" + id.getPath()));
        ct1.put("BlockState", ct2);
        ct1.put("Time", IntTag.of(5));
        ct1.put("Pos", lt1);
        ct1.put("id", StringTag.of("minecraft:falling_block"));
        ct.put("EntityTag", ct1);
        return is;
    }

    @Override
    public void onExecute(String[] args) {
        OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("force");
        optionParser.accepts("spawnEgg");
        optionParser.accepts("baby");
        optionParser.accepts("sand");
        OptionSet oset = optionParser.parse(args);
        assert Cornos.minecraft.player != null;
        if (!Cornos.minecraft.player.isCreative() && !oset.has("force")) {
            STL.notifyUser("You need to be in creative (or provide --force)");
            return;
        }
        if (args.length < 1) {
            STL.notifyUser("Syntax ([required], <optional>): .hlg [text] <--spawnEgg> <--force> <--baby> <--sand>");
            return;
        }
        List<String> bruh = new ArrayList<>();
        for (String s : args) {
            if (!s.startsWith("--")) bruh.add(s);
        }

        boolean doSpawnEgg = oset.has("spawnEgg");
        spawnHologram(Cornos.minecraft.player.getPos(), String.join(" ", bruh), doSpawnEgg, oset.has("baby"), oset.has("sand") ? "falling_block" : "armor_stand");

        super.onExecute(args);
    }
}
