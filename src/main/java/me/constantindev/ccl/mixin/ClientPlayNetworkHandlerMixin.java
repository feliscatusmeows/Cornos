package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.external.OreSim;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    OreSim oresim;

    @Inject(method = "sendPacket", cancellable = true, at = @At("HEAD"))
    public void callEventQueue(Packet<?> packet, CallbackInfo ci) {
        boolean flag = EventHelper.BUS.invokeEventCall(EventType.ONPACKETSEND, new PacketEvent(packet));
        if (!flag) ci.cancel();
    }

    @Inject(method = "onChunkData", at = @At(value = "TAIL"))
    private void onChunkData(ChunkDataS2CPacket packet, CallbackInfo ci) {
        if (oresim == null) oresim = (OreSim) ModuleRegistry.search(OreSim.class);
        if (oresim.isEnabled()) {
            oresim.doMathOnChunk(packet.getX(), packet.getZ());
        }
    }
}
