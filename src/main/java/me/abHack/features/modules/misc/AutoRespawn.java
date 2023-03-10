/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGameOver
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.abHack.features.modules.misc;

import me.abHack.features.command.Command;
import me.abHack.features.modules.Module;
import me.abHack.features.setting.Setting;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoRespawn
extends Module {
    public Setting<Boolean> antiDeathScreen = this.register(new Setting<Boolean>("AntiDeathScreen", true));
    public Setting<Boolean> deathCoords = this.register(new Setting<Boolean>("DeathCoords", false));
    public Setting<Boolean> respawn = this.register(new Setting<Boolean>("Respawn", true));

    public AutoRespawn() {
        super("AutoRespawn", "Respawns you when you die.", Module.Category.MISC, true, false, false);
    }

    @SubscribeEvent
    public void onDisplayDeathScreen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            if (this.deathCoords.getValue().booleanValue() && event.getGui() instanceof GuiGameOver) {
                Command.sendMessage(String.format("You died at X: %d Y: %d Z: %d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ));
            }
            if (this.respawn.getValue() != false && AutoRespawn.mc.player.getHealth() <= 0.0f || this.antiDeathScreen.getValue().booleanValue() && AutoRespawn.mc.player.getHealth() > 0.0f) {
                event.setCanceled(true);
                AutoRespawn.mc.player.respawnPlayer();
            }
        }
    }
}

