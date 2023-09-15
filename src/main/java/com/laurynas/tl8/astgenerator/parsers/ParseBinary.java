package com.laurynas.tl8.astgenerator.parsers;

import com.laurynas.tl8.astgenerator.Exp;
import com.laurynas.tl8.astgenerator.Parser;
import com.laurynas.tl8.astgenerator.expressions.BinaryExp;
import com.laurynas.tl8.tokenizer.Token;
import com.laurynas.tl8.tokenizer.TokenType;
import com.laurynas.tl8.tokenizer.Tokenizer;

import java.util.HashMap;

public class ParseBinary extends Parser {
    private final HashMap<String, Integer> precedence = new HashMap<>();
    public ParseBinary() {
        precedence.put("&&", 1);
        precedence.put("||", 2);
        precedence.put("<", 5);
        precedence.put(">", 5);
        precedence.put("<=", 5);
        precedence.put(">=", 5);
        precedence.put("==", 5);
        precedence.put("!=", 5);
        precedence.put("+", 10);
        precedence.put("-", 10);
        precedence.put("*", 20);
        precedence.put("/", 20);
    }
    @Override
    public Exp parse(Tokenizer tokenizer) {
        return doBinary(tokenizer, new ParseAtom().parse(tokenizer), 0);
    }

    private Exp doBinary(Tokenizer tokenizer, Exp left, int pre) {
        Token operator = tokenizer.peek();
        if (operator.getType().equals(TokenType.OPERATOR)) {
            int hisPre = precedence.get(operator.getValue());
            if (hisPre > pre) {
                tokenizer.next();
                Exp right = doBinary(tokenizer, new ParseAtom().parse(tokenizer), hisPre);
                BinaryExp exp = BinaryExp.builder()
                        .left(left)
                        .right(right)
                        .op(operator)
                        .build();
                return doBinary(tokenizer, exp, pre);
            }
        }
        return left;
    }
}
