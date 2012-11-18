package org.petrovic.cfa;

public class Variable {
    public final String name;
    public final String type;
    public final Boolean isArray;

    public Variable(String name, String type, Boolean isArray) {
        this.name = name;
        this.type = type;
        this.isArray = isArray;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", isArray=" + isArray +
                '}';
    }
}
