package org.petrovic.cfa;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Entry point for analyzing a set of .class files.
 */
public class Driver {
    private final String CLASS = ".class";
    private final List<File> directoriesToScan;
    private final IOFileFilter classFileFilter = new IOFileFilter() {
        @Override
        public boolean accept(File file) {
            return file.getName().endsWith(CLASS);
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(CLASS);
        }
    };
    private final IOFileFilter directoryFilter = new IOFileFilter() {
        @Override
        public boolean accept(File file) {
            return true;
        }

        @Override
        public boolean accept(File dir, String name) {
            return true;
        }
    };
    public final Map<File, Collection<File>> scanFiles;

    public Driver(List<File> directoriesToScan) {
        this.directoriesToScan = directoriesToScan;
        scanFiles = filesToScan();
    }

    protected Map<File, Collection<File>> filesToScan() {
        Map<File, Collection<File>> fileMapByDirectory = new HashMap<File, Collection<File>>();
        for (File dir : directoriesToScan) {
            Collection<File> files = FileUtils.listFiles(dir, classFileFilter, directoryFilter);
            fileMapByDirectory.put(dir, files);
        }
        return Collections.unmodifiableMap(fileMapByDirectory);
    }

    /**
     * Call after construction.
     *
     * @return Collection of ClassFileReport
     * @throws IOException
     */
    public Collection<ClassClassViz> analyze(Boolean debug) throws IOException {
        Collection<ClassClassViz> reports = new LinkedList<ClassClassViz>();
        for (File dir : scanFiles.keySet()) {
            for (File classFile : scanFiles.get(dir)) {
                ClassReader classReader = new ClassReader(new FileInputStream(classFile));
                if (debug) {
                    TraceClassVisitor traceClassVisitor = new TraceClassVisitor(new PrintWriter(System.out));
                    classReader.accept(traceClassVisitor, ClassReader.EXPAND_FRAMES);
                } else {
                    ClassClassViz classViz = new ClassClassViz(classFile);
                    reports.add(classViz);
                }
            }
        }
        return reports;
    }

    public Collection<ClassClassViz> analyze() throws IOException {
        return analyze(Boolean.FALSE);
    }
}
