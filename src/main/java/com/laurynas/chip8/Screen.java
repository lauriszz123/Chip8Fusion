package com.laurynas.chip8;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen extends JPanel {

    private static Screen instance = null;

    private static final int CANVAS_WIDTH = 240;
    private static final int CANVAS_HEIGHT = 128;
    private static final Color PIXEL_ON = Color.BLACK;
    private static final Color PIXEL_OFF = Color.WHITE;
    private transient BufferedImage canvasImage;

    private Screen() {
        setBackground(Color.WHITE);
        canvasImage = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
        clearCanvas();
    }

    public static Screen getInstance() {
        if (instance == null) {
            instance = new Screen();
        }
        return instance;
    }

    public void clearCanvas() {
        Graphics2D g2d = canvasImage.createGraphics();
        g2d.setColor(PIXEL_OFF);
        g2d.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        g2d.dispose();
    }

    public void render() {
        Graphics g = this.getGraphics();
        if (g != null) {
            int width = getWidth();
            int height = getHeight();
            int x = (width - CANVAS_WIDTH * height / CANVAS_HEIGHT) / 2;
            int scaledWidth = CANVAS_WIDTH * height / CANVAS_HEIGHT;

            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(canvasImage, x, 0, scaledWidth, height, null);
            g2d.dispose();
            g.dispose();
        }
    }

    public int getPixel(int x, int y) {
        if (canvasImage.getRGB(x, y) == PIXEL_ON.getRGB()) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setPixel(int x, int y) {
        if (canvasImage.getRGB(x, y) == PIXEL_OFF.getRGB()) {
            canvasImage.setRGB(x, y, PIXEL_ON.getRGB());
        } else {
            canvasImage.setRGB(x, y, PIXEL_OFF.getRGB());
        }
    }
}