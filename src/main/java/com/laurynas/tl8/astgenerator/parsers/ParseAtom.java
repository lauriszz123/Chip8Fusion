package com.laurynas.tl8.astgenerator.parsers;

import com.laurynas.tl8.astgenerator.AstGenerator;
import com.laurynas.tl8.astgenerator.Exp;
import com.laurynas.tl8.astgenerator.Parser;
import com.laurynas.tl8.astgenerator.expressions.BinaryExp;
import com.laurynas.tl8.astgenerator.expressions.IntegerExp;
import com.laurynas.tl8.astgenerator.expressions.VarFetchExp;
import com.laurynas.tl8.tokenizer.TokenType;
import com.laurynas.tl8.tokenizer.Tokenizer;

public class ParseAtom extends Parser {
    @Override
    public Exp parse(Tokenizer tokenizer) {
        switch (tokenizer.peek().getType()) {
            case IDENTIFIER -> {
                return VarFetchExp.builder().name(tokenizer.next().getValue()).build();
            }
            case NUMBER -> {
                return IntegerExp.builder().value(tokenizer.next().getValue()).build();
            }
            case PREC -> {
                if (tokenizer.peek().getValue().equals("(")) {
                    tokenizer.next();
                    Exp binary = new ParseBinary().parse(tokenizer);
                    AstGenerator.expect(tokenizer, TokenType.PREC, ")", "expected )");
                    return binary;
                }
            }
            default -> tokenizer.error("unexpected token");
        }
        return null;
    }
}
