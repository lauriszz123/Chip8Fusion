package com.laurynas.tl8.tokenizer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class Token {
    private final TokenType type;
    private final String value;
}
