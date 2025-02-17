package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.command.Command;
import me.constantindev.ccl.features.command.CommandRegistry;
import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.gui.screen.DocsScreen;
import me.constantindev.ccl.gui.screen.XrayConfigScreen;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Help extends Command {
    public Help() {
        super("Help", "Shows all commands and modules", new String[]{"h", "help", "man", "?", "commands", "modules"});
    }

    @Override
    public void onExecute(String[] args) {
        new Thread(() -> {
            STL.sleep(10);
            Cornos.minecraft.openScreen(new DocsScreen(true));
        }).start();
        super.onExecute(args);
    }
}
