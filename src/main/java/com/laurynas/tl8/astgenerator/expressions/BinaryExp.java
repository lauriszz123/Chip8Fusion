package com.laurynas.tl8.astgenerator.expressions;

import com.laurynas.tl8.astgenerator.Exp;
import com.laurynas.tl8.tokenizer.Token;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class BinaryExp extends Exp {
    private final Exp left;
    private final Exp right;
    private final Token op;
}
