package me.constantindev.ccl.etc.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import me.constantindev.ccl.Cornos;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Renderer {
    public static void renderBlockOutline(Vec3d bpos, Vec3d dimensions, int r, int g, int b, int a) {
        Camera c = BlockEntityRenderDispatcher.INSTANCE.camera;
        Vec3d s = bpos.subtract(c.getPos());
        Vec3d e = s.add(dimensions);
        double f = s.x;
        double g1 = s.y;
        double h = s.z;
        double i = e.x;
        double j = e.y;
        double k = e.z;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(2);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glRotated(MathHelper.wrapDegrees(c.getPitch()), 1, 0, 0);
        GL11.glRotated(MathHelper.wrapDegrees(c.getYaw() + 180.0), 0, 1, 0);
        GL11.glColor4f(r / 255F, g / 255F, b / 255F, a / 255F);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(f, g1, h);
        GL11.glVertex3d(i, g1, h);
        GL11.glVertex3d(f, g1, h);
        GL11.glVertex3d(f, j, h);
        GL11.glVertex3d(f, g1, h);
        GL11.glVertex3d(f, g1, k);
        GL11.glVertex3d(i, g1, h);
        GL11.glVertex3d(i, j, h);
        GL11.glVertex3d(i, j, h);
        GL11.glVertex3d(f, j, h);
        GL11.glVertex3d(f, j, h);
        GL11.glVertex3d(f, j, k);
        GL11.glVertex3d(f, j, k);
        GL11.glVertex3d(f, g1, k);
        GL11.glVertex3d(f, g1, k);
        GL11.glVertex3d(i, g1, k);
        GL11.glVertex3d(i, g1, k);
        GL11.glVertex3d(i, g1, h);
        GL11.glVertex3d(f, j, k);
        GL11.glVertex3d(i, j, k);
        GL11.glVertex3d(i, g1, k);
        GL11.glVertex3d(i, j, k);
        GL11.glVertex3d(i, j, h);
        GL11.glVertex3d(i, j, k);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static void renderLine(Vec3d from, Vec3d to, Color col, int width) {
        Camera c = BlockEntityRenderDispatcher.INSTANCE.camera;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glRotated(MathHelper.wrapDegrees(c.getPitch()), 1, 0, 0);
        GL11.glRotated(MathHelper.wrapDegrees(c.getYaw() + 180.0), 0, 1, 0);
        GL11.glColor4f(col.getRed() / 255F, col.getGreen() / 255F, col.getBlue() / 255F, col.getAlpha() / 255F);
        GL11.glBegin(GL11.GL_LINES);
        {
            Vec3d f1 = from.subtract(c.getPos());
            Vec3d t1 = to.subtract(c.getPos());
            GL11.glVertex3d(f1.x, f1.y, f1.z);
            GL11.glVertex3d(t1.x, t1.y, t1.z);
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static void renderLineScreen(Vec3d from, Vec3d to, Color col, int width) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(col.getRed() / 255F, col.getGreen() / 255F, col.getBlue() / 255F, col.getAlpha() / 255F);
        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex3d(from.x, from.y, from.z);
            GL11.glVertex3d(to.x, to.y, to.z);
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static void renderRoundedQuad(double fromX, double fromY, double toX, double toY, int rad, Color col) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glLineWidth(3);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(col.getRed() / 255F, col.getGreen() / 255F, col.getBlue() / 255F, col.getAlpha() / 255F);
        GL11.glBegin(GL11.GL_POLYGON);

        double toX1 = toX - rad;
        double toY1 = toY - rad;
        double fromX1 = fromX + rad;
        double fromY1 = fromY + rad;
        int initial = -90;
        double[][] map = new double[][]{
                new double[]{toX1, toY1},
                new double[]{toX1, fromY1},
                new double[]{fromX1, fromY1},
                new double[]{fromX1, toY1}
        };
        for (int i = 0; i < 4; i++) {
            double[] current = map[i];
            initial += 90;
            for (int r = initial; r < (360 / 4 + initial); r++) {
                double rad1 = Math.toRadians(r);
                double sin = Math.sin(rad1) * rad;
                double cos = Math.cos(rad1) * rad;
                GL11.glVertex2d(current[0] + sin, current[1] + cos);
            }
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static Vec3d getCrosshairVector() {

        Camera camera = BlockEntityRenderDispatcher.INSTANCE.camera;

        ClientPlayerEntity player = Cornos.minecraft.player;

        float f = 0.017453292F;
        float pi = (float) Math.PI;

        assert player != null;
        float f1 = MathHelper.cos(-player.yaw * f - pi);
        float f2 = MathHelper.sin(-player.yaw * f - pi);
        float f3 = -MathHelper.cos(-player.pitch * f);
        float f4 = MathHelper.sin(-player.pitch * f);

        return new Vec3d(f2 * f3, f4, f1 * f3).add(camera.getPos());
    }

    public static void drawImage(MatrixStack matrices, Identifier identifier, int x, int y, int imageWidth, int imageHeight, float r, float g, float b) {

        RenderSystem.enableAlphaTest();
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 771);
        GL11.glColor3f(r, g, b);

        Cornos.minecraft.getTextureManager().bindTexture(identifier);
        Screen.drawTexture(matrices, x, y, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        GL11.glColor3f(1, 1, 1);
        RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();

    }
}
