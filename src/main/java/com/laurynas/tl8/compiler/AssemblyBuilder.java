package com.laurynas.tl8.compiler;

public class AssemblyBuilder {
    private static AssemblyBuilder instasnce;
    private final StringBuilder assembly;
    private AssemblyBuilder() {
        assembly = new StringBuilder();
    }
    public static AssemblyBuilder createInstance() {
        if (instasnce != null) {
            throw new RuntimeException("Instance already exists!");
        }
        instasnce = new AssemblyBuilder();
        return instasnce;
    }

    public static AssemblyBuilder getInstance() {
        if (instasnce != null) {
            return instasnce;
        } else {
            throw new RuntimeException("No instance exists!");
        }
    }

    public void append(String asm) {
        assembly.append(asm);
    }

    public String toString() {
        return assembly.toString();
    }
}
