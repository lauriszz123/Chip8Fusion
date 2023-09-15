package com.laurynas.tl8.compiler.codegenerators;

import com.laurynas.tl8.astgenerator.Exp;
import com.laurynas.tl8.astgenerator.expressions.IntegerExp;
import com.laurynas.tl8.astgenerator.expressions.VarDefExp;
import com.laurynas.tl8.compiler.AssemblyBuilder;
import com.laurynas.tl8.compiler.AssemblyObject;

import static com.laurynas.tl8.compiler.Compiler.walker;

public class VariableDefinitionGenerator {
    private static IntegerExp checkInteger(Exp expression) {
        if (expression == null) return null;
        if (expression.getClass().equals(IntegerExp.class)) {
            return (IntegerExp) expression;
        }
        return null;
    }
    public static void generate(VarDefExp varDefExp) {
        IntegerExp isInteger = checkInteger(varDefExp.getValue());
        if (isInteger != null) {
            AssemblyObject.LOAD_IMMEDIATE(
                    AssemblyObject
                            .REG_A,
                    isInteger.getValue());
        } else {
            walker(varDefExp.getValue());
        }
        AssemblyObject.STORE_STATIC_VARIABLE(varDefExp.getVarName());
    }
}
