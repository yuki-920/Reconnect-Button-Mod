package com.yuki920.reconnectbuttonmod.mixin;

import com.yuki920.reconnectbuttonmod.ReconnectButtonMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class ConnectScreenMixin {

    // コンストラクタではなく静的メソッド startConnecting を狙う
    @Inject(method = "startConnecting", at = @At("HEAD"))
    private static void onStartConnecting(Screen screen, Minecraft minecraft, ServerAddress serverAddress, ServerData serverData, boolean bl, TransferState transferState, CallbackInfo ci) {
        if (serverData != null) {
            ReconnectButtonMod.setLastServerData(serverData);
            ReconnectButtonMod.LOGGER.info("[ReconnectButtonMod] Saved server: {}", serverData.ip);
        }
    }
}