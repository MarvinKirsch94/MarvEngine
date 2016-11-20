package com.marvinkirsch.core;

import java.awt.event.*;

/**
 * @author Marvin Kirsch
 * @version 0.0
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener {

    private GameContainer gc;

    private static boolean[] keys = new boolean[256];
    private static boolean[] keysLast = new boolean[256];

    private static boolean[] buttons = new boolean[256];
    private static boolean[] buttonsLast = new boolean[256];

    private static int mouseX, mouseY;

    public Input(GameContainer gc) {
        this.gc = gc;
        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
    }

    public void update() {
        keysLast = keys.clone();
        buttons = buttons.clone();
    }

    public static boolean isKey(int keyCode) {
        return keys[keyCode];
    }

    public static boolean isKeyPressed(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }

    public static boolean isKeyReleased(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }

    public static boolean isButton(int button) {
        return buttons[button];
    }

    public static boolean isButtonPressed(int button) {
        return buttons[button] && !buttonsLast[button];
    }

    public static boolean isButtonReleased(int button) {
        return !buttons[button] && buttonsLast[button];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int)(e.getX() / gc.getScale());
        mouseY = (int)(e.getY() / gc.getScale());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX() / gc.getScale());
        mouseY = (int)(e.getY() / gc.getScale());
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static void setMouseX(int mouseX) {
        Input.mouseX = mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static void setMouseY(int mouseY) {
        Input.mouseY = mouseY;
    }
}
