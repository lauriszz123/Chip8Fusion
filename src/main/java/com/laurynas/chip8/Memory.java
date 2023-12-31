package com.laurynas.chip8;

public class Memory {
    private final int[] memoryCell;
    private int bank;
    public static final int[] FONT = {
            0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
            0x20, 0x60, 0x20, 0x20, 0x70, // 1
            0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
            0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
            0x90, 0x90, 0xF0, 0x10, 0x10, // 4
            0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
            0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
            0xF0, 0x10, 0x20, 0x40, 0x50, // 7
            0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
            0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
            0xF0, 0x90, 0xF0, 0x90, 0x90, // A
            0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
            0xF0, 0x80, 0x80, 0x80, 0xF0, // C
            0xE0, 0x90, 0x90, 0x90, 0xE0, // D
            0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
            0xF0, 0x80, 0xF0, 0x80, 0x80  // F
    };
    public Memory(int size) {
        memoryCell = new int[size];
        bank = 0;
        for (int i=0; i < FONT.length; i++) {
            memoryCell[i] = FONT[i];
        }
    }

    public int get(int i) {
        return memoryCell[(bank << 12) | i];
    }

    public void set(int i, int val) {
        memoryCell[(bank << 12) | i] = val;
    }

    public int getRaw(int i) {
        return memoryCell[i];
    }


    public void setBank(int bank) {
        this.bank = bank;
    }

    public int getBank() {
        return bank;
    }
}
