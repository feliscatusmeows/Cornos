/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Leave
# Created by constantin at 22:59, Mär 04 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Leave extends Command {
    public Leave() {
        super("Leave", "Leaves the server", new String[]{"leave", "l", "quit"});
    }

    @Override
    public void onExecute(String[] args) {
        MinecraftClient.getInstance().getNetworkHandler().onDisconnected(Text.of("Disconnect triggered by cornos command"));
        super.onExecute(args);
    }
}
