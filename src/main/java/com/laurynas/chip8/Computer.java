package com.laurynas.chip8;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Computer {
    private final CPU cpu;
    private final Memory memory;

    public Computer() {
        memory = new Memory(0xFFFF);
        Keyboard keyboard = Keyboard.getInstance();

        cpu = new CPU(memory, keyboard);
    }

    public void update() {
        cpu.execute(cpu.fetch());
    }
    public void updateTimers() {
        if (cpu.getDelayTimer() > 0) {
            cpu.setDelayTimer(cpu.getDelayTimer() - 1);
        }
        if (cpu.getSoundTimer() > 0) {
            cpu.setSoundTimer(cpu.getSoundTimer() - 1);
        }
    }

    public void loadROM(String path) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(path);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            int fileSize = (int) fileInputStream.getChannel().size();
            byte[] chip8Data = new byte[fileSize];
            dataInputStream.readFully(chip8Data);

            for(int i=0; i < fileSize; i++) {
                memory.set(0x200 + i, Byte.toUnsignedInt(chip8Data[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
