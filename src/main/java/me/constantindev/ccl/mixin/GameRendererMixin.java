package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.helper.SilentRotations;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;raycast(DFZ)Lnet/minecraft/util/hit/HitResult;"))
    public HitResult raycast(Entity entity, double maxDistance, float tickDelta, boolean includeFluids) {
        if (!SilentRotations.nextRotationSilent) {
            return entity.raycast(maxDistance, tickDelta, includeFluids);
        }
        Vec3d vec3d = entity.getCameraPosVec(tickDelta);
        Vec3d vec3d2 = getRotationVector(getPitch(SilentRotations.camPitch, SilentRotations.prevCamPitch, tickDelta), getYaw(SilentRotations.camYaw, SilentRotations.prevCamYaw, tickDelta));
        Vec3d vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
        return entity.getEntityWorld().raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, entity));
    }

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;"))
    public Vec3d getRotationVec(Entity entity, float tickDelta) {
        if (!SilentRotations.nextRotationSilent) {
            return entity.getRotationVec(tickDelta);
        }
        return getRotationVector(getPitch(SilentRotations.camPitch, SilentRotations.prevCamPitch, tickDelta), getYaw(SilentRotations.camYaw, SilentRotations.prevCamYaw, tickDelta));
    }

    @Inject(at = @At("TAIL"), method = "updateTargetedEntity")
    public void updateTargetedEntityTail(float tickDelta, CallbackInfo ci) {
        if (SilentRotations.nextRotationSilent) {
            SilentRotations.nextRotationSilent = false;
        }
    }

    private Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    private float getPitch(float pitch, float prevPitch, float tickDelta) {
        return tickDelta == 1.0F ? pitch : MathHelper.lerp(tickDelta, prevPitch, pitch);
    }

    private float getYaw(float yaw, float prevYaw, float tickDelta) {
        return tickDelta == 1.0F ? yaw : MathHelper.lerp(tickDelta, prevYaw, yaw);
    }
}
