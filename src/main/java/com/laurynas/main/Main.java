package com.laurynas.main;

import com.laurynas.assembler.Assembler;
import com.laurynas.chip8.Keyboard;
import com.laurynas.chip8.Screen;
import party.iroiro.luajava.Lua;
import party.iroiro.luajava.LuaException;
import party.iroiro.luajava.luajit.LuaJit;

import javax.swing.*;
import java.io.IOException;

public class Main {
    private static void printUsage() {
        System.out.println("Commands:");
        System.out.println("<filepath>\tRuns a ROM image.");
        System.out.println("--asm [-o <fileout>] <filein>\tCompiles assembly file to binary");
        System.out.println("--bas [-o <fileout>] <filein>\tCompiles BASIC file to assembly");
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            printUsage();
            System.exit(0);
        }

        Emulator emulator = new Emulator();

        switch (args[0]) {
            case "--asm":
                String out = null;
                String in = null;
                int i = 1;
                while (i < args.length) {
                    if (args[i].equals("-o") || args[i].equals("/o")) {
                        out = args[++i];
                    } else {
                        in = args[i];
                    }
                    i++;
                }
                Assembler assembler = new Assembler();
                assembler.assemble(in, out);
                System.exit(0);
                break;
            default:
                emulator.setRomFilePath(args[0]);
                emulator.start();
                break;
        }

        JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Chip-8 Fusion");
        frame.setVisible(true);
        frame.add(Screen.getInstance());
        frame.addKeyListener(Keyboard.getInstance());
    }

    private static String openFile(String filePath) {
        return "";
    }
}