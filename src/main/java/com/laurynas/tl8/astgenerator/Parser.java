package com.laurynas.tl8.astgenerator;

import com.laurynas.tl8.astgenerator.Exp;
import com.laurynas.tl8.tokenizer.Tokenizer;

public abstract class Parser {
    public abstract Exp parse(Tokenizer tokenizer);
}
