package com.yuki920.reconnectbuttonmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.multiplayer.ServerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReconnectButtonMod implements ClientModInitializer {

    public static final String MOD_ID = "reconnectbuttonmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ServerData lastServerData = null;

    @Override
    public void onInitializeClient() {
        LOGGER.info("ReconnectButtonMod initialized.");
    }

    public static ServerData getLastServerData() {
        return lastServerData;
    }

    public static void setLastServerData(ServerData data) {
        lastServerData = data;
    }
}
