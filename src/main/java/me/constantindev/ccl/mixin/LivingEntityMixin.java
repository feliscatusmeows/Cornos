package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.movement.Jesus;
import me.constantindev.ccl.features.module.impl.render.Freecam;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    public void cWOF(Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        if (((Object) this) instanceof ClientPlayerEntity) {
            ClientPlayerEntity e = (ClientPlayerEntity) ((Object) this);
            if (Cornos.minecraft.player.getUuid().equals(e.getUuid())) {
                if (ModuleRegistry.search(Jesus.class).isEnabled() && (Jesus.mode.value.equalsIgnoreCase("solid") || Jesus.mode.value.equalsIgnoreCase("herobrine3")))
                    cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "isInsideWall", at = @At("HEAD"), cancellable = true)
    public void bruh(CallbackInfoReturnable<Boolean> cir) {
        if (((Object) this) instanceof ClientPlayerEntity) {
            ClientPlayerEntity e = (ClientPlayerEntity) ((Object) this);
            if (Cornos.minecraft.player.getUuid().equals(e.getUuid())) {
                if (ModuleRegistry.search(Freecam.class).isEnabled())
                    cir.setReturnValue(false);
            }
        }
    }
}
