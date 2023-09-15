package com.laurynas.tl8.compiler.codegenerators;

import com.laurynas.tl8.astgenerator.Exp;
import com.laurynas.tl8.astgenerator.expressions.BinaryExp;
import com.laurynas.tl8.astgenerator.expressions.IntegerExp;
import com.laurynas.tl8.compiler.AssemblyObject;

import static com.laurynas.tl8.compiler.Compiler.walker;

public class BinaryGenerator {
    private static IntegerExp checkInteger(Exp expression) {
        if (expression == null) return null;
        if (expression.getClass().equals(IntegerExp.class)) {
            return (IntegerExp) expression;
        }
        return null;
    }
    public static void generate(BinaryExp binaryExp) {
        if (binaryExp
                .getLeft()
                .getClass()
                .equals(BinaryExp.class) && binaryExp
                .getRight()
                .getClass()
                .equals(BinaryExp.class)) {
            walker(binaryExp.getRight());
            AssemblyObject.QUICK_STORE();
            walker(binaryExp.getLeft());
            AssemblyObject.QUICK_LOAD();
        } else {
            Exp left = walker(binaryExp.getLeft());
            Exp right = walker(binaryExp.getRight());
            if (right == null && left != null) {
                IntegerExp leftInt = checkInteger(left);
                if (leftInt != null) {
                    AssemblyObject.LOAD_IMMEDIATE(
                            AssemblyObject.REG_B,
                            leftInt.getValue());
                }
            } else if (left == null && right != null) {
                IntegerExp rightInt = checkInteger(right);
                if (rightInt != null) {
                    AssemblyObject.LOAD_IMMEDIATE(
                            AssemblyObject.REG_B,
                            rightInt.getValue());
                }
            } else {
                IntegerExp leftInt = checkInteger(left);
                IntegerExp rightInt = checkInteger(right);
                if (leftInt != null) {
                    AssemblyObject.LOAD_IMMEDIATE(
                            AssemblyObject.REG_A,
                            leftInt.getValue());
                }
                if (rightInt != null) {
                    AssemblyObject.LOAD_IMMEDIATE(
                            AssemblyObject.REG_B,
                            rightInt.getValue());
                }
            }
        }
        switch (binaryExp.getOp().getValue()) {
            case "+" -> AssemblyObject.ADD();
            case "*" -> AssemblyObject.MUL();
            case "-" -> AssemblyObject.SUB();
            case "/" -> AssemblyObject.DIV();
            default -> throw new RuntimeException();
        }
    }
}
