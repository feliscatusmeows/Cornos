/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Clip
# Created by constantin at 17:46, Feb 28 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*///helo i like catnip meow
package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.util.math.Vec3d;

public class Clip extends Command {
    int failedAttempts = 0;

    public Clip() {
        super("Clip", "Basically teleporting but cool", new String[]{"clip", "cl", "tp"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 0) {
            ClientHelper.sendChat("Usage: clip v/h distance");
            return;
        }
        assert Cornos.minecraft.player != null;
        Vec3d p = Cornos.minecraft.player.getPos();
        Vec3d np = p;
        switch (args[0].toLowerCase()) {
            case "v":
                if (args.length == 1) {
                    ClientHelper.sendChat("You need to tell me how many blocks to clip up/down");
                    failedAttempts = 1;
                    break;
                }
                if (!ClientHelper.isIntValid(args[1])) {
                    ClientHelper.sendChat(failedAttempts == 1 ? "That isn't a number that I can read" : (failedAttempts == 2 ? "DUDE read what I put above" : "You have to do clip v (number)"));//oh god what is this i cant read aaaaaaaaaa
                    failedAttempts = 2;
                    break;
                }
                int h = Integer.parseInt(args[1]);
                np = new Vec3d(p.x, p.y + h, p.z);
                break;
            case "h":
                if (args.length == 1) {
                    ClientHelper.sendChat("You need to tell me how many blocks to clip forwards");
                    failedAttempts = 1;
                    break;
                }
                if (!ClientHelper.isIntValid(args[1])) {
                    ClientHelper.sendChat(failedAttempts == 1 ? "That isn't a number that I can read" : (failedAttempts == 2 ? "DUDE read what I put above" : "You have to do clip h (number)"));
                    failedAttempts = 2;
                    break;
                }
                int mtp = Integer.parseInt(args[1]);
                Vec3d origv = Cornos.minecraft.player.getRotationVector();
                Vec3d newv = new Vec3d(origv.x, 0, origv.z).multiply(mtp);
                np = new Vec3d(p.x + newv.x, p.y, p.z + newv.z);
                break;
            default:
                ClientHelper.sendChat("Usage: clip v/h distance");
                return;
        }
        Cornos.minecraft.player.updatePosition(np.x, np.y, np.z);
        super.onExecute(args);
    }
}
