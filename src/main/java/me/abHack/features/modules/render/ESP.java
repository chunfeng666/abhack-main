/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.entity.item.EntityExpBottle
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.abHack.features.modules.render;

import java.awt.Color;
import me.abHack.event.events.Render3DEvent;
import me.abHack.event.events.RenderEntityModelEvent;
import me.abHack.features.modules.Module;
import me.abHack.features.modules.client.ClickGui;
import me.abHack.features.setting.Setting;
import me.abHack.util.ColorUtil;
import me.abHack.util.EntityUtil;
import me.abHack.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ESP
extends Module {
    private static ESP INSTANCE = new ESP();
    public final Setting<Boolean> others = this.register(new Setting<Boolean>("Crystals", false));
    public final Setting<Float> lineWidth = this.register(new Setting<Float>("LineWidth", Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));
    private final Setting<Mode> mode = this.register(new Setting<Mode>("Mode", Mode.OUTLINE));
    private final Setting<Boolean> players = this.register(new Setting<Boolean>("Players", true));
    private final Setting<Boolean> animals = this.register(new Setting<Boolean>("Animals", false));
    private final Setting<Boolean> mobs = this.register(new Setting<Boolean>("Mobs", false));
    private final Setting<Boolean> items = this.register(new Setting<Boolean>("Items", false));
    private final Setting<Boolean> xporbs = this.register(new Setting<Boolean>("XpOrbs", false));
    private final Setting<Boolean> xpbottles = this.register(new Setting<Boolean>("XpBottles", false));
    private final Setting<Boolean> pearl = this.register(new Setting<Boolean>("Pearls", false));
    private final Setting<Integer> red = this.register(new Setting<Integer>("Red", 255, 0, 255));
    private final Setting<Integer> green = this.register(new Setting<Integer>("Green", 255, 0, 255));
    private final Setting<Integer> blue = this.register(new Setting<Integer>("Blue", 255, 0, 255));
    private final Setting<Integer> boxAlpha = this.register(new Setting<Integer>("BoxAlpha", 120, 0, 255));
    private final Setting<Integer> alpha = this.register(new Setting<Integer>("Alpha", 255, 0, 255));
    private final Setting<Boolean> colorSync = this.register(new Setting<Boolean>("Rainbow", true));
    private final Setting<Boolean> colorFriends = this.register(new Setting<Boolean>("Friends", true));
    private final Setting<Boolean> self = this.register(new Setting<Boolean>("Self", true));
    private final Setting<Boolean> onTop = this.register(new Setting<Boolean>("onTop", true));
    private final Setting<Boolean> invisibles = this.register(new Setting<Boolean>("Invisibles", false));

    public ESP() {
        super("ESP", "Renders a nice ESP.", Module.Category.RENDER, false, false, false);
        this.setInstance();
    }

    public static ESP getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ESP();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        AxisAlignedBB bb;
        Vec3d interp;
        int i;
        if (this.items.getValue().booleanValue()) {
            i = 0;
            for (Entity entity : ESP.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityItem) || !(ESP.mc.player.getDistanceSq(entity) < 2500.0)) continue;
                interp = EntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.0f);
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() : (float)this.red.getValue().intValue() / 255.0f), (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() : (float)this.green.getValue().intValue() / 255.0f), (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() : (float)this.blue.getValue().intValue() / 255.0f), (float)((float)this.boxAlpha.getValue().intValue() / 255.0f));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                RenderUtil.drawBlockOutline(bb, this.colorSync.getValue() != false ? new Color(ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue(), this.alpha.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                if (++i < 50) continue;
            }
        }
        if (this.xporbs.getValue().booleanValue()) {
            i = 0;
            for (Entity entity : ESP.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityXPOrb) || !(ESP.mc.player.getDistanceSq(entity) < 2500.0)) continue;
                interp = EntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.0f);
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() : (float)this.red.getValue().intValue() / 255.0f), (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() : (float)this.green.getValue().intValue() / 255.0f), (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() : (float)this.blue.getValue().intValue() / 255.0f), (float)((float)this.boxAlpha.getValue().intValue() / 255.0f));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                RenderUtil.drawBlockOutline(bb, this.colorSync.getValue() != false ? new Color(ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue(), this.alpha.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                if (++i < 50) continue;
            }
        }
        if (this.pearl.getValue().booleanValue()) {
            i = 0;
            for (Entity entity : ESP.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityEnderPearl) || !(ESP.mc.player.getDistanceSq(entity) < 2500.0)) continue;
                interp = EntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.0f);
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() : (float)this.red.getValue().intValue() / 255.0f), (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() : (float)this.green.getValue().intValue() / 255.0f), (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() : (float)this.blue.getValue().intValue() / 255.0f), (float)((float)this.boxAlpha.getValue().intValue() / 255.0f));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                RenderUtil.drawBlockOutline(bb, this.colorSync.getValue() != false ? new Color(ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue(), this.alpha.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                if (++i < 50) continue;
            }
        }
        if (this.xpbottles.getValue().booleanValue()) {
            i = 0;
            for (Entity entity : ESP.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityExpBottle) || !(ESP.mc.player.getDistanceSq(entity) < 2500.0)) continue;
                interp = EntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.0f);
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() : (float)this.red.getValue().intValue() / 255.0f), (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() : (float)this.green.getValue().intValue() / 255.0f), (float)(this.colorSync.getValue() != false ? (float)ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() : (float)this.blue.getValue().intValue() / 255.0f), (float)((float)this.boxAlpha.getValue().intValue() / 255.0f));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                RenderUtil.drawBlockOutline(bb, this.colorSync.getValue() != false ? new Color(ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue(), this.alpha.getValue()) : new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.0f);
                if (++i < 50) continue;
            }
        }
    }

    public void onRenderModel(RenderEntityModelEvent event) {
        if (event.getStage() != 0 || event.entity == null || event.entity.isInvisible() && this.invisibles.getValue() == false || this.self.getValue() == false && event.entity.equals((Object)ESP.mc.player) || this.players.getValue() == false && event.entity instanceof EntityPlayer || this.animals.getValue() == false && EntityUtil.isPassive(event.entity) || !this.mobs.getValue().booleanValue() && !EntityUtil.isPassive(event.entity) && !(event.entity instanceof EntityPlayer)) {
            return;
        }
        Color color = this.colorSync.getValue() != false ? EntityUtil.getColor(event.entity, ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen(), ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue(), this.alpha.getValue(), this.colorFriends.getValue()) : EntityUtil.getColor(event.entity, this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue(), this.colorFriends.getValue());
        boolean fancyGraphics = ESP.mc.gameSettings.fancyGraphics;
        ESP.mc.gameSettings.fancyGraphics = false;
        float gamma = ESP.mc.gameSettings.gammaSetting;
        ESP.mc.gameSettings.gammaSetting = 10000.0f;
        if (this.onTop.getValue().booleanValue()) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        if (this.mode.getValue() == Mode.OUTLINE) {
            RenderUtil.renderOne(this.lineWidth.getValue().floatValue());
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.lineWidth.getValue().floatValue());
            RenderUtil.renderTwo();
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.lineWidth.getValue().floatValue());
            RenderUtil.renderThree();
            RenderUtil.renderFour(color);
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.lineWidth.getValue().floatValue());
            RenderUtil.renderFive();
        } else {
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            if (this.mode.getValue() == Mode.WIREFRAME) {
                GL11.glPolygonMode((int)1032, (int)6913);
            } else {
                GL11.glPolygonMode((int)1028, (int)6913);
            }
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)2848);
            GL11.glEnable((int)3042);
            GlStateManager.blendFunc((int)770, (int)771);
            GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            GlStateManager.glLineWidth((float)this.lineWidth.getValue().floatValue());
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        if (!this.onTop.getValue().booleanValue()) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        try {
            ESP.mc.gameSettings.fancyGraphics = fancyGraphics;
            ESP.mc.gameSettings.gammaSetting = gamma;
        }
        catch (Exception exception) {
            // empty catch block
        }
        event.setCanceled(true);
    }

    public static enum Mode {
        WIREFRAME,
        OUTLINE;

    }
}

