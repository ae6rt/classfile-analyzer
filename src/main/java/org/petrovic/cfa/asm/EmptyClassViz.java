package org.petrovic.cfa.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class EmptyClassViz extends ClassVisitor {

    public EmptyClassViz() {
        super(4);
    }

    public EmptyClassViz(int api) {
        super(api);
    }

    @Override
    public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visitSource(String s, String s1) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visitOuterClass(String s, String s1, String s2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visitInnerClass(String s, String s1, String s2, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visitEnd() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
