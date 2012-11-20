package org.petrovic.cfa;

import org.objectweb.asm.Label;
import org.petrovic.cfa.asm.EmptyMethodViz;

import java.util.List;

public class MethodClassViz extends EmptyMethodViz {

    private final List<Variable> localVariables;

    public MethodClassViz(List<Variable> variableList) {
        super(4);
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
