package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;

public class NoFireOverlay extends Module {
    public NoFireOverlay() {
        super("NoFireOverlay", "Doesn't render the fire overlay, currently doesn't do anything. ", MType.MISC);
    }
    // Logic: WorldRenderMixin.java, again
}
