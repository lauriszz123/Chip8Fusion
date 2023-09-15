package com.laurynas.tl8.compiler;

public class AssemblyObject {
    public static int REG_A = 0;
    public static int REG_B = 1;
    public static void ADD() {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("ADD V0, V1");
        assembly.append("\n");
    }
    public static void MUL() {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("MUL V0, V1");
        assembly.append("\n");
    }
    public static void SUB() {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("SUB V0, V1");
        assembly.append("\n");
    }
    public static void DIV() {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("DIV V0, V1");
        assembly.append("\n");
    }
    public static void LOAD_IMMEDIATE(int reg, String a) {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("LD ");
        assembly.append((reg == REG_A) ? "V0" : "V1");
        assembly.append(", ");
        assembly.append(a);
        assembly.append("\n");
    }
    public static void QUICK_STORE() {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("LD V2, V0");
        assembly.append("\n");
    }
    public static void QUICK_LOAD() {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("LD V1, V2");
        assembly.append("\n");
    }
    public static void STORE_STATIC_VARIABLE(String staticVariable) {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("LD I, ");
        assembly.append(staticVariable);
        assembly.append("\n");
        assembly.append("LD [I], V0");
        assembly.append("\n");
    }
    public static void LOAD_STATIC_VARIABLE(String staticVariable) {
        var assembly = AssemblyBuilder.getInstance();
        assembly.append("LD I, ");
        assembly.append(staticVariable);
        assembly.append("\n");
        assembly.append("LD V0, [I]");
        assembly.append("\n");
    }
}
