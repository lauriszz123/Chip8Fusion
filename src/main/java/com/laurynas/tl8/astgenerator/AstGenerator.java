package com.laurynas.tl8.astgenerator;

import com.laurynas.tl8.astgenerator.parsers.ParseVarDef;
import com.laurynas.tl8.tokenizer.Token;
import com.laurynas.tl8.tokenizer.TokenType;
import com.laurynas.tl8.tokenizer.Tokenizer;

import java.util.Objects;

public class AstGenerator {
    private final Tokenizer tokenizer;
    public AstGenerator(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public AST generate() {
        AST ast = new AST(tokenizer);
        while (!tokenizer.peek().getType().equals(TokenType.EOF)) {
            if (tokenizer.peek().getType().equals(TokenType.IDENTIFIER)) {
                ast.add(new ParseVarDef());
            } else break;
        }
        return ast;
    }

    public static Token expect(Tokenizer tokenizer, TokenType type, String errorMessage) {
        if (tokenizer.peek().getType().equals(type)) {
            return tokenizer.next();
        } else {
            tokenizer.error(errorMessage);
        }
        return null;
    }
    public static void expect(Tokenizer tokenizer, TokenType type, String value, String errorMessage) {
        Token token = expect(tokenizer, type, errorMessage);
        assert token != null;
        if (!value.equals(token.getValue())) {
            tokenizer.error(errorMessage);
        }
    }
}
