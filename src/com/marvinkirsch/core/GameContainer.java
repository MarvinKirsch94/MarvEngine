package com.marvinkirsch.core;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public class GameContainer implements Runnable {

    private Thread thread;
    private AbstractGame game;
    private Window window;
    private Renderer renderer;
    private Input input;

    private int width = 320, height = 240;
    private float scale = 2.0f;
    private String title = "MarvEngine v1.0 by Marvin Kirsch";
    private double frameCap = 1.0 / 60.0;
    private boolean isRunning = false;

    private boolean lightEnable = false;
    private boolean dynamicLights = false;
    private boolean clearScreen = false;
    private boolean debug = false;

    public GameContainer(AbstractGame game) {
        this.game = game;
    }

    public void start() {
        if (isRunning)
            return;

        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);

        thread = new Thread(this);
        thread.run();
    }

    public void stop() {
        if (!isRunning)
            return;

        isRunning = false;
    }

    public void run() {
        isRunning = true;

        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;
        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while (isRunning) {
            boolean render = true;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= frameCap) {
                if(Input.isKeyPressed(KeyEvent.VK_F2)) debug = !debug;

                game.update(this, (float) frameCap);
                input.update();
                unprocessedTime -= frameCap;
                render = true;

                if (frameTime >= 1) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if (render) {
                if (clearScreen) renderer.clear();

                game.render(this, renderer);

                if (dynamicLights) renderer.drawLightArray();
                if(lightEnable || dynamicLights) renderer.combineMaps();
                if(debug) renderer.drawString("FPS-" + fps, 0xffffffff, 0, 0);

                window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        cleanUp();
    }

    private void cleanUp() {
        window.cleanUp();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public boolean isDynamicLights() {
        return dynamicLights;
    }

    public void setDynamicLights(boolean dynamicLights) {
        this.dynamicLights = dynamicLights;
    }
}
