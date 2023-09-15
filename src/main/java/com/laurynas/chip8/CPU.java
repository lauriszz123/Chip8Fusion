package com.laurynas.chip8;

import java.util.Random;

public class CPU {
    private int pc;
    private int sp;
    private int[] V;
    private int I;
    private final Memory memory;
    private final int[] stack;
    private int soundTimer;
    private int delayTimer;

    private final Keyboard keyboard;
    public CPU(Memory memory, Keyboard keyboard) {
        this.memory = memory;
        pc = 0x200;
        sp = 0;
        V = new int[0x10];
        I = 0;
        stack = new int[0x100];
        soundTimer = 0;
        delayTimer = 0;
        this.keyboard = keyboard;
    }
    public int fetch() {
        return (memory.getRaw(pc) << 8) | memory.getRaw(pc + 1);
    }
    public void execute(int opcode) {
        int nnn = opcode & 0x0FFF;
        int n = opcode & 0x000F;
        int x = (opcode & 0x0F00) >> 8;
        int y = (opcode & 0x00F0) >> 4;
        int kk = opcode & 0x00FF;

        int leadByte = (opcode & 0xF000) >> 12;

        switch (leadByte) {
            case 0 -> systemInstructions(opcode, nnn);
            case 1 -> {
                System.out.println("Jumping to location 0x" + Integer.toHexString(nnn));
                pc = nnn;
            }
            case 2 -> {
                System.out.println("Calling subroutine at 0x" + Integer.toHexString(nnn));
                stack[sp++] = pc;
                pc = nnn;
            }
            case 3 -> {
                System.out.println("Skipping next instruction if V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(kk));
                if (V[x] == kk) {
                    System.out.println("> True");
                    pc += 2;
                } else System.out.println("> False");
                pc += 2;
            }
            case 4 -> {
                System.out.println("Skipping next instruction if V" + Integer.toHexString(x) + " != 0x" + Integer.toHexString(kk));
                if (V[x] != kk) {
                    System.out.println("> True");
                    pc += 2;
                } else System.out.println("> False");
                pc += 2;
            }
            case 5 -> {
                System.out.println("Skipping next instruction if V" + Integer.toHexString(x) + " = V" + Integer.toHexString(y));
                if (V[x] == V[y]) {
                    System.out.println("> True");
                    pc += 2;
                } else System.out.println("> False");
                pc += 2;
            }
            case 6 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(kk));
                V[x] = kk;
                pc += 2;
            }
            case 7 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[x]) + " + 0x" + Integer.toHexString(kk));
                V[x] = (V[x] + kk) & 0xFF;
                System.out.println("> 0x" + Integer.toHexString(V[x]));
                pc += 2;
            }
            case 8 -> {
                mathInstructions(opcode, x, y, n);
                pc += 2;
            }
            case 9 -> {
                System.out.println("Skipping next instruction if V" + Integer.toHexString(x) + " != 0x" + Integer.toHexString(V[y]));
                if (V[x] != V[y]) {
                    System.out.println("> True");
                    pc += 2;
                } else {
                    System.out.println("> False");
                }
                pc += 2;
            }
            case 0xA -> {
                System.out.println("Setting I = 0x" + Integer.toHexString(nnn));
                I = nnn;
                pc += 2;
            }
            case 0xB -> {
                System.out.println("Jumping to location 0x" + Integer.toHexString(nnn) + " + 0x" + Integer.toHexString(V[0]));
                pc = nnn + V[0];
            }
            case 0xC -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = RANDOM_BYTE AND 0x" + Integer.toHexString(kk));
                Random rand = new Random();
                V[x] = rand.nextInt(256) & kk;
                pc += 2;
            }
            case 0xD -> {
                drawInstruction(x, y, n);
                pc += 2;
            }
            case 0xE -> {
                controllerInstructions(opcode, x, kk);
                pc += 2;
            }
            case 0xF -> {
                miscIntructions(opcode, x, kk);
                pc += 2;
            }
            default -> {
                System.err.println("Invalid Opcode: " + Integer.toHexString(opcode));
                pc += 2;
            }
        }
    }

    private void systemInstructions(int opcode, int nnn) {
        switch (opcode) {
            case 0x00E0 -> {
                System.out.println("Clearing the display.");
                Screen.getInstance().clearCanvas();
                pc += 2;
            }
            case 0x00EE -> {
                System.out.println("Returning from a subroutine");
                pc = stack[--sp];
                pc += 2;
            }
            default -> {
                System.err.println("Unsupported Opcode: 0x" + Integer.toHexString(nnn));
                pc += 2;
            }
        }
    }
    private void writeCarry(int dest, int value, boolean flag) {
        V[dest] = value & 0xFF;
        V[0xF] = flag ? 1 : 0;
    }
    private void mathInstructions(int opcode, int x, int y, int n) {
        switch (n) {
            case 0x0 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[y]));
                V[x] = V[y];
            }
            case 0x1 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[x]) + " OR 0x" + Integer.toHexString(V[y]));
                V[x] |= V[y];
                V[0xF] = 0;
                System.out.println("> 0x" + Integer.toHexString(V[x]));
            }
            case 0x2 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[x]) + " AND 0x" + Integer.toHexString(V[y]));
                V[x] &= V[y];
                V[0xF] = 0;
                System.out.println("> 0x" + Integer.toHexString(V[x]));
            }
            case 0x3 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[x]) + " XOR 0x" + Integer.toHexString(V[y]));
                V[x] ^= V[y];
                V[0xF] = 0;
                System.out.println("> 0x" + Integer.toHexString(V[x]));
            }
            case 0x4 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[x]) + " + 0x" + Integer.toHexString(V[y]));
                int t = V[x] + V[y];
                writeCarry(x, t, t > 0xFF);
                System.out.println("> 0x" + Integer.toHexString(V[x]));
            }
            case 0x5 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[x]) + " - 0x" + Integer.toHexString(V[y]));
                int t = V[x] - V[y];
                writeCarry(x, t, V[x] >= V[y]);
                System.out.println("> 0x" + Integer.toHexString(V[x]));
            }
            case 0x6 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[x]) + " SHR 1");
                y = x;
                int t = V[y] >> 1;
                writeCarry(x, t, (V[y] & 0x1) == 1);
                System.out.println("> 0x" + Integer.toHexString(V[x]));
            }
            case 0x7 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[y]) + " - 0x" + Integer.toHexString(V[x]));
                int t = V[y] - V[x];
                writeCarry(x, t, V[y] >= V[x]);
                System.out.println("> 0x" + Integer.toHexString(V[x]));
            }
            case 0x8 -> {
                System.out.println("Setting I = V" + Integer.toHexString(x) + " & " + Integer.toHexString(y));
                I = (V[x] << 8) | V[y];
                System.out.println("> 0x" + Integer.toHexString(I));
            }
            case 0x9 -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " & " + Integer.toHexString(V[y]) + " converted from I");
                V[x] = (I >> 8) & 0xFF;
                V[y] = I & 0xFF;
                System.out.println("> V" + Integer.toHexString(x) + " = " + Integer.toHexString(V[x]));
                System.out.println("> V" + Integer.toHexString(y) + " = " + Integer.toHexString(V[y]));
            }
            case 0xE -> {
                System.out.println("Setting V" + Integer.toHexString(x) + " = 0x" + Integer.toHexString(V[x]) + " SHL 1");
                y = x;
                int t = V[y] << 1;
                writeCarry(x, t, ((V[y] >> 0x7) & 0x1) == 1);
                System.out.println("> 0x" + Integer.toHexString(V[x]));
            }
            default -> System.err.println("Invalid Opcode: " + Integer.toHexString(opcode));
        }
    }
    private void drawInstruction(int x, int y, int n) {
        System.out.println("Displaying " + Integer.toHexString(n) + "-byte sprite starting at memory location 0x" + Integer.toHexString(I) + " at (" + Integer.toHexString(V[x]) + ", " + V[y] + ")");
        V[0xF] = 0;
        Screen screen = Screen.getInstance();
        for (int yLine = 0; yLine < n; yLine++) {
            int pixel = memory.get(I + yLine);

            for (int xLine = 0; xLine < 8; xLine++) {
                if ((pixel & (0x80 >> xLine)) != 0) {
                    int xCoord = V[x]+xLine;
                    int yCoord = V[y]+yLine;
                    if (screen.getPixel(xCoord % 64, yCoord % 64) == 1) {
                        V[0xF] = 1;
                    }
                    screen.setPixel(xCoord % 64, yCoord % 64);
                }
            }
        }
    }
    private void controllerInstructions(int opcode, int x, int kk) {
        switch (kk) {
            case 0x9E -> {
                System.out.println("Skipping next instruction if key with the value of 0x" + Integer.toHexString(V[x]) + " is pressed");
                if (keyboard.getKey(V[x])) {
                    System.out.println("> Key is Pressed");
                    pc += 2;
                }
            }
            case 0xA1 -> {
                System.out.println("Skipping next instruction if key with the value of 0x" + Integer.toHexString(V[x]) + " is not pressed");
                if (!keyboard.getKey(V[x])) {
                    System.out.println("> Key is not pressed");
                    pc += 2;
                }
            }
            default -> System.err.println("Invalid Opcode: " + Integer.toHexString(opcode));
        }
    }
    private void miscIntructions(int opcode, int x, int kk) {
        switch (kk) {
            case 0x00:
                pc = I;
                break;
            case 0x01:
                stack[sp++] = pc;
                pc = I;
                break;
            case 0x07:
                System.out.println("Setting V" + Integer.toHexString(x) + " = delayTimer timer value");
                V[x] = delayTimer;
                break;
            case 0x0A:
                System.out.println("Waiting for key press");
                //boolean looping = true;
                //mainLoop:
                //while(looping) {
                //Keep looping until a key is pressed
                //    for(int i = 0; i < 16; i++) {
                Screen.getInstance().requestFocusInWindow();
                //if(frame.controller.keyTracker[i] == true) {
                //    V[x] = (char) i;
                //    break mainLoop;
                //}
                //    }
                //}
                System.out.println("> Storing value of key in V" + Integer.toHexString(x));
                break;
            case 0x15:
                System.out.println("Setting delayTimer timer = 0x" + Integer.toHexString(V[x]));
                delayTimer = V[x];
                break;
            case 0x18:
                System.out.println("Setting soundTimer timer = 0x" + Integer.toHexString(V[x]));
                soundTimer = V[x];
                break;
            case 0x1E:
                System.out.println("Setting I = 0x" + Integer.toHexString(I) + " + 0x" + Integer.toHexString(V[x]));
                I += V[x];
                I %= 0x1000;
                System.out.println("> 0x" + Integer.toHexString(I));
                break;
            case 0x29:
                System.out.println("Setting I = location of sprite for digit 0x" + Integer.toHexString(V[x]));
                I = (V[x] & 0xF) * 5;
                break;
            case 0x33:
                System.out.println("Storing BCD representation of 0x" + Integer.toHexString(V[x]) + " in memory locations 0x" + Integer.toHexString(I) + ", I + 1, and I + 2");
                memory.set(I, (V[x] / 100) % 10);
                memory.set(I + 1, (V[x] / 10) % 10);
                memory.set(I + 2, V[x] % 10);
                break;
            case 0x55:
                System.out.println("Storing registers V0 through V" + Integer.toHexString(x) + " in memory starting at location 0x" + Integer.toHexString(I));
                for (int i = 0; i <= x; i++) {
                    System.out.println("> V" + Integer.toHexString(i) + " = 0x" + Integer.toHexString(V[i]));
                    memory.set(I + i, V[i]);
                }
                // I = (I+x+1)&0xFFFF;
                break;
            case 0x65:
                System.out.println("Reading registers V0 through V" + Integer.toHexString(x) + " from memory starting at location 0x" + Integer.toHexString(I));
                for (int i = 0; i <= x; i++) {
                    V[i] = memory.getRaw(I + i);
                    System.out.println("> V" + Integer.toHexString(i) + " = 0x" + Integer.toHexString(V[i]));
                }
                break;
            case 0x70:
                System.out.println("Setting memory BANK to V" + Integer.toHexString(x));
                memory.setBank(V[x]);
                break;
            case 0x71:
                System.out.println("Reading memory BANK to V" + Integer.toHexString(x));
                V[x] = memory.getBank();
                break;
            default:
                System.err.println("Invalid Opcode: " + Integer.toHexString(opcode));
                break;
        }
    }

    public int getSoundTimer() {
        return soundTimer;
    }

    public int getDelayTimer() {
        return delayTimer;
    }

    public void setDelayTimer(int delayTimer) {
        this.delayTimer = delayTimer;
    }

    public void setSoundTimer(int soundTimer) {
        this.soundTimer = soundTimer;
    }
}
