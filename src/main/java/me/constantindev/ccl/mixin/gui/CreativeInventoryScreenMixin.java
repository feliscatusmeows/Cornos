/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: CreativeInventoryMixin
# Created by constantin at 12:24, Mär 19 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.mixin.gui;

import me.constantindev.ccl.gui.screen.FunnyItemsScreen;
import me.constantindev.ccl.gui.screen.HeadsScreen;
import me.constantindev.ccl.gui.widget.CustomButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin extends Screen {
    public CreativeInventoryScreenMixin() {
        super(Text.of(""));
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        this.addButton(new CustomButtonWidget(1, 1, 120, 20, 5, Text.of("Funny items"), () -> {
            if (this.client == null) return;
            this.client.openScreen(new FunnyItemsScreen());
        }));
        this.addButton(new CustomButtonWidget(1, 22, 120, 20, 5, Text.of("Heads"), () -> {
            if (this.client == null) return;
            this.client.openScreen(new HeadsScreen());
        }));
    }
}
