package org.petrovic.cfa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * A jar file representation with "contains-class" semantics.
 */
public class JarFile {
    private final String CLASS_SUFFIX = ".class";
    public final File jarFile;
    private List<String> classFileEntries = new ArrayList<String>();

    public JarFile(File jarFile) {
        this.jarFile = jarFile;
        try {
            JarInputStream jarInputStream = new JarInputStream(new FileInputStream(jarFile));
            ZipEntry zipEntry;
            while ((zipEntry = jarInputStream.getNextEntry()) != null) {
                String name = zipEntry.getName();
                if (name.endsWith(".class")) {
                    classFileEntries.add(name);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean containsClass(String className) {
        String target = String.format("%s%s", className, className.endsWith(CLASS_SUFFIX) ? "" : CLASS_SUFFIX);
        return classFileEntries.contains(target);
    }

    public static Collection<JarFile> classIsPresent(Collection<JarFile> inputJarFileList, String reference) {
        List<JarFile> jar = new LinkedList<JarFile>();
        for (JarFile jarFile : inputJarFileList) {
            if (jarFile.containsClass(reference)) {
                jar.add(jarFile);
            }
        }
        return jar;
    }

    @Override
    public String toString() {
        return "JarFile{" +
                "jarFile=" + jarFile +
                '}';
    }
}
