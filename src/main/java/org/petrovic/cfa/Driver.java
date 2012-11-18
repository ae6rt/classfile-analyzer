package org.petrovic.cfa;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    public Collection<ClassViz> analyze() throws IOException {
        Collection<ClassViz> reports = new LinkedList<ClassViz>();
        for (File dir : scanFiles.keySet()) {
            for (File classFile : scanFiles.get(dir)) {
                ClassReader classReader = new ClassReader(new FileInputStream(classFile));
                ClassViz classViz = new ClassViz(classFile);
                classReader.accept(classViz, ClassReader.EXPAND_FRAMES);
                reports.add(classViz);
            }
        }
        return reports;
    }
}
