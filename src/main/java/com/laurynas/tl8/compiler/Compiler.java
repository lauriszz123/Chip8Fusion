package com.laurynas.tl8.compiler;

import com.laurynas.tl8.astgenerator.AST;
import com.laurynas.tl8.astgenerator.Exp;
import com.laurynas.tl8.astgenerator.expressions.BinaryExp;
import com.laurynas.tl8.astgenerator.expressions.IntegerExp;
import com.laurynas.tl8.astgenerator.expressions.VarDefExp;
import com.laurynas.tl8.astgenerator.expressions.VarFetchExp;
import com.laurynas.tl8.compiler.codegenerators.BinaryGenerator;
import com.laurynas.tl8.compiler.codegenerators.VariableDefinitionGenerator;

public class Compiler {
    private final AST ast;
    private AssemblyBuilder assembly;
    public Compiler(AST ast) {
        this.ast = ast;
        assembly = AssemblyBuilder.createInstance();
    }

    public String toAssembly() {
        ast.getStream().forEach(exp -> precheck(exp));
        ast.getStream().forEach(exp -> walker(exp));
        return assembly.toString();
    }

    public static Exp precheck(Exp expression) {
        return expression;
    }

    public static Exp walker(Exp expression) {
        if (expression.getClass().equals(VarDefExp.class)) {
            VarDefExp varDefExp = (VarDefExp) expression;
            VariableDefinitionGenerator.generate(varDefExp);
        }
        if (expression.getClass().equals(BinaryExp.class)) {
            BinaryExp binaryExp = (BinaryExp) expression;
            BinaryGenerator.generate(binaryExp);
        }
        if (expression.getClass().equals(VarFetchExp.class)) {

        }
        if (expression.getClass().equals(IntegerExp.class)) {
            IntegerExp integerExp = (IntegerExp) expression;
            return integerExp;
        }
        return null;
    }
}
