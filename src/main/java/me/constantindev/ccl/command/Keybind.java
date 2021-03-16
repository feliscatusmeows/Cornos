package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.helper.KeyBindManager;
import me.constantindev.ccl.etc.ms.KeyBind;
import me.constantindev.ccl.etc.reg.ModuleRegistry;

public class Keybind extends Command {
    public Keybind() {
        super("Keybind", "Manages keybinds for a module", new String[]{"keybinds", "keybind", "kb", "bind"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length == 0) {
            ClientHelper.sendChat("All binds:");
            KeyBindManager.binds.forEach((s, keyBind) -> ClientHelper.sendChat("  " + s + " -> " + ((char) keyBind.keycode)));
        } else if (args.length == 1) {
            String modname = args[0];
            Module mod = ModuleRegistry.getByName(modname);
            if (mod == null) {
                ClientHelper.sendChat("Im not sure if I know a module called \"" + modname + "\"");
                return;
            }
            KeyBind kb = KeyBindManager.binds.get(mod.name);
            if (kb == null) {
                ClientHelper.sendChat(mod.name + " does not have a bind set");
                return;
            }
            ClientHelper.sendChat(mod.name + " has " + ((char) kb.keycode) + " as bind");
        } else {
            String modname = args[0];
            Module mod = ModuleRegistry.getByName(modname);
            if (mod == null) {
                ClientHelper.sendChat("Im not sure if I know a module called \"" + modname + "\"");
                return;
            }
            mod.mconf.getByName("keybind").setValue(((int) args[1].toUpperCase().charAt(0)) + "");
            ClientHelper.sendChat("Set " + mod.name + "'s keybind to " + args[1].toUpperCase().charAt(0));
            ClientHelper.sendChat("Reloading keybinds");
            KeyBindManager.reload();
        }
        super.onExecute(args);
    }
}
