package com.laurynas.main;

import com.laurynas.chip8.Computer;
import com.laurynas.chip8.Screen;

import java.io.IOException;

public class Emulator extends Thread {
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private Computer computer;
    private String romFilePath;
    private boolean running;
    public Emulator() {
        running = false;
    }

    public void setRomFilePath(String romFilePath) {
        this.romFilePath = romFilePath;
    }

    public void init() throws IOException {
        running = true;
        computer = new Computer();
        if (romFilePath == null) {
            System.out.println("ROM file not specified!");
            System.exit(0);
        } else {
            computer.loadROM(romFilePath);
            System.out.println("Loaded ROM: " + romFilePath);
        }
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException e) {
            System.out.println(e);
        }

        long lastUpdateTime = System.currentTimeMillis();

        while (running) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpdateTime;

            if (elapsedTime >= FRAME_TIME) {
                update();
                render();
                lastUpdateTime = currentTime;
            } else {
                try {
                    // Sleep for the remaining time to maintain constant FPS
                    Thread.sleep(FRAME_TIME - elapsedTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void update() {
        for (int i = 0; i < 0x10; i++) {
            computer.update();
        }
        computer.updateTimers();
    }

    public void render() {
        Screen.getInstance().render();
    }
}
