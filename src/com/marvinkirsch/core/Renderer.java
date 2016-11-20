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
    private Font font = Font.STANDARD;
    private int ambientLight = Pixel.getColor(1, 0.1f, 0.1f, 0.1f);

    public Renderer(GameContainer gc) {
        width = gc.getWidth();
        height = gc.getHeight();
        pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        lm = new int[pixels.length];
    }

    public void setPixel(int x, int y, int color) {
        if((x < 0 || x >= width || y < 0 || y >= height) || color == 0xffff00ff)
            return;

        pixels[x + y * width] = color;
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
                        setPixel(x + offX + offset, y + offY - 1, color);
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
                setPixel(x, y, Pixel.getLightBlend(pixels[x + y * width], lm[x + y * width], ambientLight));
            }
        }
    }

    public void drawImage(Image image, int offX, int offY) {
        for(int x = 0; x < image.width; x++) {
            for(int y = 0; y < image.height; y++) {
                setPixel(x + offX, y + offY, image.pixels[x + y * image.width]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        for(int x = 0; x < image.tileWidth; x++) {
            for(int y = 0; y < image.tileHeight; y++) {
                setPixel(x + offX, y + offY, image.pixels[(x + (tileX * image.tileWidth)) + (y + (tileY * image.tileHeight)) * image.width]);
            }
        }
    }

    public void drawLight(Light light, int offX, int offY) {
        for(int x = 0; x < light.diameter; x++) {
            for(int y = 0; y < light.diameter; y++) {
                setLightMap(x + offX - light.radius, y + offY - light.radius, light.lm[x + y * light.diameter]);
            }
        }
    }
}
