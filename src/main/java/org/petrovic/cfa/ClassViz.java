package org.petrovic.cfa;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClassViz extends EmptyVisitor {
    private String className;
    private final File backingFile;
    private List<Variable> fields = new LinkedList<Variable>();
    private Map<Method, List<Variable>> methodLocalMap = new HashMap<Method, List<Variable>>();

    public ClassViz(File backingFile) {
        this.backingFile = backingFile;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (!TypeUtility.omit(desc)) {
            fields.add(new Variable(name, TypeUtility.normalize(desc), TypeUtility.isArray(desc)));
        }
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        Method key = new Method(name, desc);
        List<Variable> methodLocalVariables = methodLocalMap.get(key);
        if (methodLocalVariables == null) {
            methodLocalVariables = new LinkedList<Variable>();
            methodLocalMap.put(key, methodLocalVariables);
        }
        return new MethodViz(methodLocalVariables);
    }

    public String getClassName() {
        return className;
    }

    public Collection<Variable> getFields() {
        return Collections.unmodifiableCollection(fields);
    }

    public Map<Method, List<Variable>> getMethodLocalMap() {
        return Collections.unmodifiableMap(methodLocalMap);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ClassViz");
        sb.append("{type='").append(className).append('\'');
        sb.append(", backingFile=").append(backingFile);
        sb.append(", fields=").append(fields);
        sb.append(", methodLocalMap=").append(methodLocalMap);
        sb.append('}');
        return sb.toString();
    }
}
