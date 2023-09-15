package com.laurynas.tl8.astgenerator.expressions;

import com.laurynas.tl8.astgenerator.Exp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class VarDefExp extends Exp {
    private final String varType;
    private final String varName;
    private final Exp value;
}
