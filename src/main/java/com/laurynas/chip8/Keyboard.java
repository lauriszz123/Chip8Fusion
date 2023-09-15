package com.laurynas.chip8;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private static Keyboard instance = null;
    private int[] keyCodes = {
            KeyEvent.VK_X,

            KeyEvent.VK_1,
            KeyEvent.VK_2,
            KeyEvent.VK_3,

            KeyEvent.VK_Q,
            KeyEvent.VK_W,
            KeyEvent.VK_E,

            KeyEvent.VK_A,
            KeyEvent.VK_S,
            KeyEvent.VK_D,

            KeyEvent.VK_Z,
            KeyEvent.VK_C,
            KeyEvent.VK_4,
            KeyEvent.VK_R,
            KeyEvent.VK_F,
            KeyEvent.VK_V,
    };

    private boolean[] keys;

    public Keyboard() {
        keys = new boolean[16];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for(int i = 0; i < keyCodes.length; i++) {
            if (keyCode == keyCodes[i]) {
                keys[i] = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for(int i = 0; i < keyCodes.length; i++) {
            if (keyCode == keyCodes[i]) {
                keys[i] = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public boolean getKey(int i) {
        return keys[i];
    }

    public static Keyboard getInstance() {
        if (instance == null) {
            instance = new Keyboard();
        }
        return instance;
    }
}
