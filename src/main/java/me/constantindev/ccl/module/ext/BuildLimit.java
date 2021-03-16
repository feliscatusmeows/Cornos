package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;

public class BuildLimit extends Module {
    public BuildLimit() {
        super("BuildLimit", "Allows you to build underneath the block you target, currently does not do anything", MType.WORLD);
    }
    // Logic: PacketTryUseItemOnBlockHook.java
}
