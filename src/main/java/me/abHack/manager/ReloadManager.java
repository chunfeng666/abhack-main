/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.abHack.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.abHack.OyVey;
import me.abHack.event.events.PacketEvent;
import me.abHack.features.Feature;
import me.abHack.features.command.Command;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReloadManager
extends Feature {
    public String prefix;

    public void init(String prefix) {
        this.prefix = prefix;
        MinecraftForge.EVENT_BUS.register((Object)this);
        if (!ReloadManager.fullNullCheck()) {
            Command.sendMessage(ChatFormatting.RED + "OyVey has been unloaded. Type " + prefix + "reload to reload.");
        }
    }

    public void unload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        CPacketChatMessage packet;
        if (event.getPacket() instanceof CPacketChatMessage && (packet = (CPacketChatMessage)event.getPacket()).getMessage().startsWith(this.prefix) && packet.getMessage().contains("reload")) {
            OyVey.load();
            event.setCanceled(true);
        }
    }
}

