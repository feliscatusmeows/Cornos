package me.constantindev.ccl.features.module.impl.combat;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class Surround extends Module {
    MConfNum delay = new MConfNum("delay", 0, 20, 0);
    MConfToggleable checkOnGround = new MConfToggleable("checkOnGround", true);
    MConfToggleable checkVelocity = new MConfToggleable("checkVelocity", false);
    MConfToggleable instant = new MConfToggleable("instant", false);
    MConfToggleable switchToObby = new MConfToggleable("switchObby", true);
    MConfToggleable blockMovements = new MConfToggleable("blockMovements", true);
    int delayWaited = 0;

    public Surround() {
        super("Surround", "Surrounds you with shit", ModuleType.COMBAT);
        mconf.add(delay);
        mconf.add(checkOnGround);
        mconf.add(checkVelocity);
        mconf.add(instant);
        mconf.add(switchToObby);
        mconf.add(blockMovements);
    }

    @Override
    public void onExecute() {
        Vec3d pvel = Cornos.minecraft.player.getVelocity();
        Vec3d ppos = Cornos.minecraft.player.getPos();
        if (ppos.y > 255) return;
        boolean otherCheck = true;
        boolean velCheck = true;
        if (checkOnGround.isEnabled()) {
            otherCheck = Cornos.minecraft.player.isOnGround();
        }
        if (checkVelocity.isEnabled()) {
            velCheck = pvel.x == 0 && pvel.z == 0 && pvel.y <= 0 && pvel.y > -0.079;
        }
        if (otherCheck && velCheck) {
            delayWaited++;
            if (delayWaited > delay.getValue()) {
                Vec3d[] positions = new Vec3d[]{
                        new Vec3d(0, -1, 0),
                        new Vec3d(1, 0, 0),
                        new Vec3d(0, 0, 1),
                        new Vec3d(-1, 0, 0),
                        new Vec3d(0, 0, -1)
                };
                List<Vec3d> positionsWeCanReplace = new ArrayList<>();
                for (Vec3d position : positions) {
                    Vec3d current = Cornos.minecraft.player.getPos().add(position);
                    BlockPos currentBP = new BlockPos(current.x, current.y, current.z);
                    BlockState w = Cornos.minecraft.world.getBlockState(currentBP);
                    if (w.getMaterial().isReplaceable()) positionsWeCanReplace.add(position);
                }
                if (positionsWeCanReplace.isEmpty()) return;
                if (blockMovements.isEnabled()) {
                    Cornos.minecraft.player.setVelocity(0, 0, 0);
                }
                if (switchToObby.isEnabled()) {
                    int obsidianIndex = -1;
                    for (int i = 0; i < 9; i++) {
                        ItemStack current = Cornos.minecraft.player.inventory.getStack(i);
                        if (current.getItem() == Items.OBSIDIAN) {
                            obsidianIndex = i;
                            break;
                        }
                    }
                    if (obsidianIndex == -1) {
                        // obsidian issue
                        return;
                    }
                    if (Cornos.minecraft.player.inventory.selectedSlot != obsidianIndex) {
                        Cornos.minecraft.player.inventory.selectedSlot = obsidianIndex;
                        return;
                    }
                }
                BlockPos bruh = Cornos.minecraft.player.getBlockPos();
                Vec3d newPos = new Vec3d(bruh.getX() + .5, bruh.getY(), bruh.getZ() + .5);
                Cornos.minecraft.player.updatePosition(newPos.x, newPos.y, newPos.z);
                for (Vec3d position : positionsWeCanReplace) {
                    Vec3d current = Cornos.minecraft.player.getPos().add(position);
                    BlockPos currentBP = new BlockPos(current.x, current.y, current.z);
                    BlockHitResult bhr = new BlockHitResult(new Vec3d(.5, .5, 5), Direction.DOWN, currentBP, false);
                    Cornos.minecraft.interactionManager.interactBlock(Cornos.minecraft.player, Cornos.minecraft.world, Hand.MAIN_HAND, bhr);
                    if (!instant.isEnabled()) break;
                }
            }
            if (delayWaited > delay.max + 1) delayWaited = (int) Math.ceil(delay.max);
        } else {
            delayWaited = 0;
        }
        super.onExecute();
    }
}
