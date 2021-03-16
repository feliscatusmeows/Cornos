package me.constantindev.ccl.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Give extends Command {
    public Give() {
        super("Give", "Like /give but without the /. (Requires Creative)", new String[]{"give"});
    }

    @Override
    public void onExecute(String[] args) {
        assert Cornos.minecraft.player != null;
        if (!Cornos.minecraft.player.isCreative()) {
            ClientHelper.sendChat("\u00A7cYou must be in creative to use this command. I can't make items magically appear, sorry!");
            return;
        }
        if (args.length == 0) {
            ClientHelper.sendChat("Usage: }give <Item>{nbt} <amount>");//TODO make it look at prefix and use the actual prefix
            return;
        }

        ItemStackArgumentType itemStackArgumentType = new ItemStackArgumentType();

        ItemStackArgument itemStackArgument;

        try {
            itemStackArgument = itemStackArgumentType.parse(new StringReader(args[0]));
        } catch (CommandSyntaxException e) {
            ClientHelper.sendChat("\u00A7cError: That is not a minecraft item that I know about.");
            return;
        }

        int amount = 1;

        try {
            amount = Integer.parseInt(args[1]);
        } catch (Exception ignore) {

        }

        ItemStack itemStack;

        try {
            itemStack = itemStackArgument.createStack(amount, false);
        } catch (CommandSyntaxException e) {
            ClientHelper.sendChat("\u00A7cThere was an error trying to create the ItemStack, please check all arguments.");
            return;
        }

        Cornos.minecraft.player.inventory.addPickBlock(itemStack);
        ClientHelper.sendChat("Gave you " + amount + " " + (amount > 1 ? itemStack.getName().getString() + "s" : itemStack.getName().getString()));

        super.onExecute(args);
    }
}
