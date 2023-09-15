package com.laurynas.tl8.tokenizer;

import com.laurynas.tl8.scanner.Scanner;

public class Tokenizer {
    private final Scanner scanner;
    private Token currentToken = null;

    public Tokenizer(Scanner scanner) {
        this.scanner = scanner;
    }

    public Token next() {
        if (currentToken != null) {
            Token swap = currentToken;
            currentToken = null;
            return swap;
        } else return nextHelper();
    }

    public Token peek() {
        if (currentToken == null)
            currentToken = nextHelper();

        return currentToken;
    }

    public void error(String message) {
        scanner.error(message);
    }

    private String readWhile(TokenType tokenType) {
        StringBuilder s = new StringBuilder();
        while (!scanner.eof()) {
            if (TypeCheck.accepts(scanner.peek(), tokenType)) {
                s.insert(s.length(), scanner.next());
            } else break;
        }
        return s.toString();
    }

    private Token nextHelper() {
        if (scanner.eof())
            return Token.builder()
                    .type(TokenType.EOF)
                    .value("EOF")
                    .build();
        readWhile(TokenType.WHITESPACE);
        if (TypeCheck.isPrec(scanner.peek())) {
            return Token.builder()
                    .type(TokenType.PREC)
                    .value(Character.toString(scanner.next()))
                    .build();
        }
        if (TypeCheck.isNumber(scanner.peek())) {
            return Token.builder()
                    .type(TokenType.NUMBER)
                    .value(readWhile(TokenType.NUMBER))
                    .build();
        }
        if (TypeCheck.isOperator(scanner.peek())) {
            return Token.builder()
                    .type(TokenType.OPERATOR)
                    .value(readWhile(TokenType.OPERATOR))
                    .build();
        }
        if (TypeCheck.isIdentStart(scanner.peek())) {
            return Token.builder()
                    .type(TokenType.IDENTIFIER)
                    .value(readWhile(TokenType.IDENTIFIER))
                    .build();
        }
        return Token.builder()
                .type(TokenType.EOF)
                .value("EOF")
                .build();
    }
}
