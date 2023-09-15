package com.laurynas.tl8.astgenerator.parsers;

import com.laurynas.tl8.astgenerator.AstGenerator;
import com.laurynas.tl8.astgenerator.Exp;
import com.laurynas.tl8.astgenerator.Parser;
import com.laurynas.tl8.astgenerator.expressions.VarDefExp;
import com.laurynas.tl8.tokenizer.Token;
import com.laurynas.tl8.tokenizer.TokenType;
import com.laurynas.tl8.tokenizer.Tokenizer;

public class ParseVarDef extends Parser {
    @Override
    public Exp parse(Tokenizer tokenizer) {
        Token type = AstGenerator.expect(tokenizer, TokenType.IDENTIFIER, "expected a type");
        Token name = AstGenerator.expect(tokenizer, TokenType.IDENTIFIER, "expected a variable name");
        AstGenerator.expect(tokenizer, TokenType.OPERATOR, "=", "expected equals");
        Exp value = new ParseBinary().parse(tokenizer);
        AstGenerator.expect(tokenizer, TokenType.PREC, ";", "expected semicolon, got " + tokenizer.peek());
        return VarDefExp.builder()
                .varName(name.getValue())
                .varType(type.getValue())
                .value(value)
                .build();
    }
}
