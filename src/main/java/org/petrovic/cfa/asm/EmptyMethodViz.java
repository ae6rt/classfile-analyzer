package org.petrovic.cfa.asm;

import org.objectweb.asm.MethodVisitor;

public class EmptyMethodViz extends MethodVisitor {
    public EmptyMethodViz(int api) {
        super(api);
    }
}
