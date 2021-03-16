package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.helper.RandomHelper;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class Flight extends Module {
    int counter = 0;

    public Flight() {
        super("Flight", "Allows you to fly", MType.MOVEMENT);
        this.mconf.add(new MultiOption("mode", "vanilla", new String[]{"vanilla", "static", "jetpack"}));
        this.mconf.add(new Toggleable("toggleFast", true));
        this.mconf.add(new Num("speed", 1.0, 30, 0));
    }

    @Override
    public void onExecute() {
        double speed = ((Num) this.mconf.getByName("speed")).getValue();
        if (((Toggleable) this.mconf.getByName("toggleFast")).isEnabled()) {
            if (counter > 10) counter = 0;
            counter++;
        } else counter = 0;
        switch (this.mconf.getByName("mode").value) {
            case "vanilla":
                assert Cornos.minecraft.player != null;
                Cornos.minecraft.player.abilities.flying = !(counter > 9);
                break;
            case "static":

                assert Cornos.minecraft.player != null;
                Vec3d rot = Cornos.minecraft.player.getRotationVector();
                rot = rot.multiply(speed);
                if (counter > 9) Cornos.minecraft.player.setVelocity(0, -0.1, 0);
                else if (Cornos.minecraft.options.keyForward.isPressed())
                    Cornos.minecraft.player.setVelocity(rot.x, rot.y, rot.z);
                else if (Cornos.minecraft.options.keyBack.isPressed())
                    Cornos.minecraft.player.setVelocity(-rot.x, -rot.y, -rot.z);
                else Cornos.minecraft.player.setVelocity(0, 0, 0);
                break;
            case "jetpack":
                if (Cornos.minecraft.world == null) return;
                if (Cornos.minecraft.options.keyJump.isPressed()) {
                    assert Cornos.minecraft.player != null;
                    Cornos.minecraft.player.addVelocity(0, speed / 30, 0);
                    Vec3d vp = Cornos.minecraft.player.getPos();
                    for (int i = 0; i < 10; i++) {
                        Cornos.minecraft.world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, vp.x, vp.y, vp.z, RandomHelper.rndD(.25) - .125, RandomHelper.rndD(.25) - .125, RandomHelper.rndD(.25) - .125);
                    }
                }
                break;
            default:
                ClientHelper.sendChat("Invalid flight mode. Please pick vanilla, static, or jetpack");
                this.mconf.getByName("mode").setValue("vanilla");
                break;
        }
        super.onExecute();
    }
}
