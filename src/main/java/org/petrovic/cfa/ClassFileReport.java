package org.petrovic.cfa;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClassFileReport {
    public final File file;
    public final String className;
    public final Collection<Variable> fields;
    public final Map<String, List<Variable>> locals;

    public ClassFileReport(File classFile, String className, List<Variable> fields, Map<String, List<Variable>> locals) {
        this.file = classFile;
        this.className = className;
        this.fields = Collections.unmodifiableCollection(fields);
        this.locals = Collections.unmodifiableMap(locals);
    }

    @Override
    public String toString() {
        return "ClassFileReport{" +
                "type='" + className + '\'' +
                ", file=" + file +
                ", fields=" + fields +
                ", locals=" + locals +
                '}';
    }
}
