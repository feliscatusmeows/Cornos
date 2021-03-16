/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: AutoSign
# Created by constantin at 02:57, Mär 13 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.WORLD;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketApplyEvent;
import me.constantindev.ccl.etc.helper.RandomHelper;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;

public class AutoSign extends Module {
    MultiOption type;

    public AutoSign() {
        super("AutoSign", "Lulw", MType.WORLD);
        type = (MultiOption) this.mconf.add(new MultiOption("mode", "noise", new String[]{"noise", "copypasta", "longlines"}));
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETHANDLE, event -> {
            PacketApplyEvent PE = (PacketApplyEvent) event;
            if (PE.packet instanceof SignEditorOpenS2CPacket && parent.isOn.isOn()) {
                event.cancel();
                SignEditorOpenS2CPacket p = (SignEditorOpenS2CPacket) PE.packet;
                String[] lines = new String[4];
                switch (type.value) {
                    case "noise":
                        lines = new String[]{
                                RandomHelper.rndBinStr(15),
                                RandomHelper.rndBinStr(15),
                                RandomHelper.rndBinStr(15),
                                RandomHelper.rndBinStr(15)
                        };
                        break;
                    case "copypasta":
                        lines = new String[]{
                                "cornos is just",
                                "the best client",
                                "to ever exist",
                                "it's true"
                        };
                        break;
                    case "longlines":
                        lines = new String[]{
                                RandomHelper.rndBinStr(50),
                                RandomHelper.rndBinStr(50),
                                RandomHelper.rndBinStr(50),
                                RandomHelper.rndBinStr(50)
                        };
                }
                net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket packet = new UpdateSignC2SPacket(p.getPos(), lines[0], lines[1], lines[2], lines[3]);
                Cornos.minecraft.getNetworkHandler().sendPacket(packet);
            }
        });
    }
}
