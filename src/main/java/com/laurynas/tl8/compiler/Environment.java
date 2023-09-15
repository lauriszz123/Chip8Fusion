        package com.laurynas.tl8.compiler;

import java.util.HashMap;

public class Environment {
    private Environment parent;
    private HashMap<String, EnvironmentVariable> variables;
    public Environment(Environment parent) {
        this.parent = parent;
    }
    public Environment extend() {
        return new Environment(this);
    }
    public EnvironmentVariable get(String name) {
        EnvironmentVariable var = lookup(name);
        if (var != null) {
            return var;
        } else {
            throw new RuntimeException("Undefined variable.");
        }
    }
    public void set(String type, String name) {
        EnvironmentVariable variable = lookup(name);
        if (variable != null) {
            variable.setType(type);
        } else {
            throw new RuntimeException("Undefined variable.");
        }
    }

    public void def(String type, String name) {
        variables.put(name, EnvironmentVariable.builder()
                .type(type)
                .build());
    }
    private EnvironmentVariable lookup(String name) {
        var scope = this;
        while (scope != null) {
            if (variables.containsKey(name)) {
                return variables.get(name);
            }
            scope = scope.parent;
        }
        return null;
    }
}
