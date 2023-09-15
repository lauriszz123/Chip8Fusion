package com.laurynas.tl8;

import com.laurynas.tl8.astgenerator.AST;
import com.laurynas.tl8.astgenerator.AstGenerator;
import com.laurynas.tl8.compiler.Compiler;
import com.laurynas.tl8.scanner.Scanner;
import com.laurynas.tl8.tokenizer.Tokenizer;

import java.util.List;

public class Tiny {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer(new Scanner(args[0]));
        AstGenerator generator = new AstGenerator(tokenizer);
        AST ast = generator.generate();
        Compiler compiler = new Compiler(ast);
        System.out.println(compiler.toAssembly());
    }
}
