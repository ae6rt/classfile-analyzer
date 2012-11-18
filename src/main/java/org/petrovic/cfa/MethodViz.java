package org.petrovic.cfa;

import org.objectweb.asm.Label;
import org.objectweb.asm.commons.EmptyVisitor;

import java.util.List;

public class MethodViz extends EmptyVisitor {

    private final List<Variable> localVariables;

    public MethodViz(List<Variable> variableList) {
        this.localVariables = variableList;
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        if (!name.equals("this") && !TypeUtility.omit(desc)) {
            Variable variable = new Variable(name, TypeUtility.normalize(desc), TypeUtility.isArray(desc));
            localVariables.add(variable);
        }
    }
}
