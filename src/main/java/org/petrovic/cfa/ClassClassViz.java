package org.petrovic.cfa;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.petrovic.cfa.asm.EmptyClassViz;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClassClassViz extends EmptyClassViz {
    private String className;
    private String superName;
    private final File backingFile;
    private List<Variable> fields = new LinkedList<Variable>();
    private Map<Method, List<Variable>> methodLocalMap = new HashMap<Method, List<Variable>>();
    private Map<Method, List<Variable>> methodExceptionsMap = new HashMap<Method, List<Variable>>();
    private List<Interface> implementedInterfaces = new LinkedList<Interface>();

    public ClassClassViz(File backingFile) {
        this.backingFile = backingFile;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
        if (!TypeUtility.omit(TypeUtility.toObjectReference(superName))) {
            this.superName = TypeUtility.toObjectReference(superName);
        }
        if (interfaces != null) {
            for (String s : interfaces) {
                if (!TypeUtility.omit(TypeUtility.toObjectReference(s))) {
                    implementedInterfaces.add(new Interface(s));
                }
            }
        }
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
        List<Variable> methodExceptions = methodExceptionsMap.get(key);
        if (methodExceptions == null) {
            methodExceptions = new LinkedList<Variable>();
            methodExceptionsMap.put(key, methodExceptions);
        }
        if (exceptions != null) {
            for (String s : exceptions) {
                if (!TypeUtility.omit(TypeUtility.toObjectReference(s))) {
                    methodExceptions.add(new Variable(name, TypeUtility.toObjectReference(s), false));
                }
            }
        }
        List<Variable> methodLocalVariables = methodLocalMap.get(key);
        if (methodLocalVariables == null) {
            methodLocalVariables = new LinkedList<Variable>();
            methodLocalMap.put(key, methodLocalVariables);
        }
        return new MethodClassViz(methodLocalVariables);
    }

    private String list(String[] exceptions) {
        if (exceptions == null) return "null";
        StringBuilder sb = new StringBuilder();
        for (String s : exceptions) {
            sb.append(String.format("%s ", s));
        }
        return sb.toString();
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

    public Map<Method, List<Variable>> getMethodExceptionsMap() {
        return Collections.unmodifiableMap(methodExceptionsMap);
    }

    public List<Interface> getImplementedInterfaces() {
        return Collections.unmodifiableList(implementedInterfaces);
    }

    public String getSuperName() {
        return superName;
    }

    @Override
    public String toString() {
        return "ClassClassViz{" +
                "className='" + className + '\'' +
                ", backingFile=" + backingFile +
                ", fields=" + fields +
                ", methodLocalMap=" + methodLocalMap +
                ", methodExceptionsMap=" + methodExceptionsMap +
                '}';
    }
}
