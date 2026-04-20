package com.yuki920.reconnectbuttonmod.mixin;

import com.yuki920.reconnectbuttonmod.ReconnectButtonMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * DisconnectedScreen (切断画面) に「Reconnect」ボタンを追加する。
 * Mojang Mappings使用 (1.21.11)
 */
@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {

    protected DisconnectedScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        ServerData serverData = ReconnectButtonMod.getLastServerData();

        // ① lastServerData が保存されているか確認
        ReconnectButtonMod.LOGGER.info("[ReconnectButtonMod] onInit fired. lastServerData={}",
                serverData != null ? serverData.ip : "null");

        if (serverData == null) return;

        Button backButton = null;
        for (var widget : this.children()) {
            // ② children() に何が入っているか全件ログ出力
            ReconnectButtonMod.LOGGER.info("[ReconnectButtonMod] widget: {}", widget.getClass().getName());
            if (widget instanceof Button btn) {
                backButton = btn;
            }
        }

        // ③ backButton が見つかったか確認
        ReconnectButtonMod.LOGGER.info("[ReconnectButtonMod] backButton={}", backButton);

        if (backButton == null) return;

        // ... ボタン追加処理

        final ServerData savedData = serverData;
        Button reconnectButton = Button.builder(
                        Component.translatable("gui.reconnectbuttonmod.reconnect"),
                        b -> ConnectScreen.startConnecting(
                                new TitleScreen(),
                                Minecraft.getInstance(),
                                ServerAddress.parseString(savedData.ip),
                                savedData,
                                false,
                                null
                        )

                )
                .bounds(backButton.getX(), backButton.getY() + backButton.getHeight() + 4,
                        backButton.getWidth(), 20)
                .build();

        this.addRenderableWidget(reconnectButton);
    }
}
