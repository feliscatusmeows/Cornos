package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ModuleConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class ShiftTp extends Module {
    public ShiftTp() {
        super("ShiftTp", "Teleports you when shifting. Useful for phasing through walls");
        this.mconf.add(new ModuleConfig.ConfigKey("multiplier", "3"));
    }

    @Override
    public void onExecute() {
        int mtp = 3;
        try {
            mtp = Integer.parseInt(this.mconf.getByName("multiplier").value);
        } catch (Exception exc) {
            this.mconf.getOrDefault("multiplier", new ModuleConfig.ConfigKey("multiplier", "3")).setValue("3");
        }
        if (MinecraftClient.getInstance().options.keySneak.wasPressed()) {
            assert MinecraftClient.getInstance().player != null;
            Vec3d pos = MinecraftClient.getInstance().player.getPos();
            Vec3d rot = MinecraftClient.getInstance().player.getRotationVector();
            rot = rot.multiply(1, 0, 1);
            pos = pos.add(rot.multiply(mtp));
            MinecraftClient.getInstance().player.world.sendPacket(new PlayerMoveC2SPacket.PositionOnly(pos.x, pos.y, pos.z, false));
            MinecraftClient.getInstance().player.updatePosition(pos.x, pos.y, pos.z);

        }
        super.onExecute();
    }
}
