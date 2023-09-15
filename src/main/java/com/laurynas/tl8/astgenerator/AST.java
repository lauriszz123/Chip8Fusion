package com.laurynas.tl8.astgenerator;

import com.laurynas.tl8.astgenerator.expressions.VarDefExp;
import com.laurynas.tl8.tokenizer.Tokenizer;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AST {
    private final List<Exp> expressions;
    private final Tokenizer tokenizer;
    public AST(Tokenizer tokenizer) {
        expressions = new ArrayList<>();
        this.tokenizer = tokenizer;
    }
    public void add(Parser parser) {
        expressions.add(parser.parse(tokenizer));
    }

    public Stream<Exp> getStream() {
        return expressions.stream();
    }

    public void printList() {
        expressions.forEach(System.out::println);
    }
}
