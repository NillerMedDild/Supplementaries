package net.mehvahdjukaar.supplementaries.network;


import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.configs.ConfigHandler;
import net.mehvahdjukaar.supplementaries.configs.ServerConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fmlclient.ConfigGuiHandler;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenConfigsPacket {
    public OpenConfigsPacket(FriendlyByteBuf buffer) {
    }

    public OpenConfigsPacket() {
    }

    public static void buffer(OpenConfigsPacket message, FriendlyByteBuf buf) {
    }

    public static void handler(OpenConfigsPacket message, Supplier<NetworkEvent.Context> ctx) {
        // client world
        ctx.get().enqueueWork(() -> {

            //FileConfig f = FileConfig.of(ConfigHandler.getServerConfigPath());
            //ServerConfigs.SERVER_CONFIG.getSpec().apply(ConfigHandler.getServerConfigPath().toString());
            //ServerConfigs.SERVER_CONFIG.getSpec().apply(ConfigHandler.getServerConfigPath().toString());
            //ServerConfigs.SERVER_CONFIG.save();

            ServerConfigs.loadLocal();

            //if(configured)ConfiguredCustomScreen.openScreen();

            ConfigHandler.openModConfigs();

        });
        ctx.get().setPacketHandled(true);
    }
}