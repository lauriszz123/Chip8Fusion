package com.laurynas.tl8.tokenizer;

import java.util.regex.Pattern;

public class TypeCheck {

    private static final Pattern NUMBER = Pattern.compile("\\d");
    private static final Pattern IDENT_START = Pattern.compile("[a-zA-Z_]");
    private static final Pattern IDENTIFIER = Pattern.compile("\\w");
    private static final Pattern PREC = Pattern.compile("[;()\\[\\],{}]");
    private static final Pattern WHITESPACE = Pattern.compile("\\s");
    private static final Pattern OPERATOR = Pattern.compile("[+\\-/*=]");

    public static boolean isNumber(Character c) {
        return NUMBER.matcher(c.toString()).matches();
    }
    public static boolean isIdentStart(Character c) {
        return IDENT_START.matcher(c.toString()).matches();
    }
    public static boolean isIdent(Character c) {
        return IDENTIFIER.matcher(c.toString()).matches();
    }
    public static boolean isPrec(Character c) {
        return PREC.matcher(c.toString()).matches();
    }
    public static boolean isWhitespace(Character c) {
        return WHITESPACE.matcher(c.toString()).matches();
    }
    public static boolean isOperator(Character c) {
        return OPERATOR.matcher(c.toString()).matches();
    }

    public static boolean accepts(Character c, TokenType type) {
        switch (type) {
            case WHITESPACE -> {
                return WHITESPACE.matcher(c.toString()).matches();
            }
            case NUMBER -> {
                return NUMBER.matcher(c.toString()).matches();
            }
            case IDENT_START -> {
                return IDENT_START.matcher(c.toString()).matches();
            }
            case IDENTIFIER -> {
                return IDENTIFIER.matcher(c.toString()).matches();
            }
            case PREC -> {
                return PREC.matcher(c.toString()).matches();
            }
            case OPERATOR -> {
                return OPERATOR.matcher(c.toString()).matches();
            }
            default -> {
                return false;
            }
        }
    }
}
