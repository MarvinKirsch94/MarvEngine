package com.marvinkirsch.core;

import com.marvinkirsch.core.fx.*;

import java.awt.image.DataBufferInt;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public class Renderer {

    private int width, height;
    private int[] pixels;
    private int[] lm;
    private ShadowType[] lb;
    private Font font = Font.STANDARD;
    private int ambientLight = Pixel.getColor(1, 0.1f, 0.1f, 0.1f);

    public Renderer(GameContainer gc) {
        width = gc.getWidth();
        height = gc.getHeight();
        pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        lm = new int[pixels.length];
        lb = new ShadowType[pixels.length];
    }

    public void setPixel(int x, int y, int color, ShadowType lightBlock) {
        if((x < 0 || x >= width || y < 0 || y >= height) || color == 0xffff00ff)
            return;

        pixels[x + y * width] = color;
        lb[x + y * width] = lightBlock;
    }

    public void setLightMap(int x, int y, int color) {
        if((x < 0 || x >= width || y < 0 || y >= height))
            return;

        lm[x + y * width] = Pixel.getMax(color, lm[x + y * width]);
    }

    public void drawString(String text, int color, int offX, int offY) {

        text = text.toUpperCase();

        int offset = 0;
        for(int i = 0; i < text.length(); i++) {

            int unicode = text.codePointAt(i) - 32;

            for(int x = 0; x < font.widths[unicode]; x++) {
                for(int y = 1; y < font.image.height; y++) {
                    if (font.image.pixels[(x + font.offsets[unicode]) + y * font.image.width] == 0xffffffff) {
                        setPixel(x + offX + offset, y + offY - 1, color, ShadowType.NONE);
                    }
                }
            }

            offset += font.widths[unicode];
        }
    }

    public void clear() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                pixels[x + y * width] = 0xff000000;
                lm[x + y * width] = ambientLight;
            }
        }
    }

    public void combineMaps() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                setPixel(x, y, Pixel.getLightBlend(pixels[x + y * width], lm[x + y * width], ambientLight), lb[x + y * width]);
            }
        }
    }

    public void drawImage(Image image, int offX, int offY) {
        for(int x = 0; x < image.width; x++) {
            for(int y = 0; y < image.height; y++) {
                setPixel(x + offX, y + offY, image.pixels[x + y * image.width], image.shadowType);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        for(int x = 0; x < image.tileWidth; x++) {
            for(int y = 0; y < image.tileHeight; y++) {
                setPixel(x + offX, y + offY, image.pixels[(x + (tileX * image.tileWidth)) + (y + (tileY * image.tileHeight)) * image.width], image.shadowType);
            }
        }
    }

    public void drawLight(Light light, int offX, int offY) {
        for(int i = 0; i <= light.diameter; i++){
            drawLightLine(light.radius, light.radius, i, 0, light, offX, offY);
            drawLightLine(light.radius, light.radius, i, light.diameter, light, offX, offY);
            drawLightLine(light.radius, light.radius, 0, i, light, offX, offY);
            drawLightLine(light.radius, light.radius, light.diameter, i, light, offX, offY);
        }
    }

    private void drawLightLine(int x0, int y0, int x1, int y1, Light light, int offX, int offY) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while(true) {
            if(light.getLightValue(x0, y0) == 0xff000000) break;

            int screenX = x0 - light.radius + offX;
            int screenY = y0 - light.radius + offY;

            setLightMap(screenX, screenY, light.getLightValue(x0, y0));

            if(x0 == x1 && y0 == y1) break;
            if(lb[screenX + screenY * width] == ShadowType.TOTAL) break;

            e2 = 2 * err;

            if(e2 > -1 * dy) {
                err -= dy;
                x0 += sx;
            }

            if(e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }
}
