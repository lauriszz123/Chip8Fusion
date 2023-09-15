package com.laurynas.tl8.compiler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class EnvironmentVariable {
    private String type;
}
