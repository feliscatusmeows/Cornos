package me.constantindev.ccl.module.WORLD;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", "Automatically picks the fastest tool out of your hotbar", MType.WORLD);
    }

    @Override
    public void onExecute() {
        if (Cornos.minecraft.options.keyAttack.isPressed()) {
            if (!(Cornos.minecraft.crosshairTarget instanceof BlockHitResult)) return;
            BlockHitResult r = ((BlockHitResult) Cornos.minecraft.crosshairTarget);
            BlockPos b = r.getBlockPos();
            BlockState bstate = Cornos.minecraft.player.world.getBlockState(b);
            PlayerInventory pinv = Cornos.minecraft.player.inventory;
            float best = 1f;
            int bs1 = -1;
            for (int i = 0; i < 9; i++) {
                ItemStack is = pinv.getStack(i);
                float s = is.getMiningSpeedMultiplier(bstate);
                if (s > best) bs1 = i;
            }
            if (bs1 != -1) {
                pinv.addPickBlock(pinv.getStack(bs1));
            }
        }
        super.onExecute();
    }
}
