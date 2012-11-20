package org.petrovic.cfa;

public class Interface {
    public final String className;

    public Interface(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Interface{" +
                "className='" + className + '\'' +
                '}';
    }
}
