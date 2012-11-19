package org.petrovic.cfa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class DriverTest {
    private File main;
    private File test;
    private List<File> dirs;

    @Before
    public void setUp() throws Exception {
        main = new File("testdata/main");
        test = new File("testdata/test");
        dirs = new LinkedList<File>();
        dirs.add(main);
        dirs.add(test);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFileCollection() throws IOException {
        Driver driver = new Driver(dirs);
        Map<File, Collection<File>> fileCollectionMap = driver.filesToScan();

        Collection<File> mainFiles = fileCollectionMap.get(main);
        assertEquals(1, mainFiles.size());
        Collection<File> testFiles = fileCollectionMap.get(test);
        assertEquals(1, testFiles.size());
    }

    @Test
    public void testAnalyze() throws IOException {
        Driver driver = new Driver(dirs);
        Collection<ClassViz> classFileReports = driver.analyze();
        assertEquals(1 + 1, classFileReports.size());

        for (ClassViz s : classFileReports) {
            Map<Method, List<Variable>> methodLocalMap = s.getMethodLocalMap();
            Collection<Variable> fields = s.getFields();
            Set<Method> methods = methodLocalMap.keySet();
            for (Method m : methods) {
                List<Variable> variables = methodLocalMap.get(m);
                assertEquals("Method should have no object locals not in the JDK", 0, variables.size());
            }

            if (s.getClassName().equals("org/apache/commons/io/filefilter/DirectoryFileFilter")) {
                assertEquals(2, fields.size());
                assertEquals(3, methodLocalMap.size());
                Object[] objects = fields.toArray();
                Variable v = (Variable) objects[0];
                assertEquals(v.type, "org/apache/commons/io/filefilter/IOFileFilter");
            }
            if (s.getClassName().equals("org.apache.commons.io.output.BrokenOutputStream")) {
                assertEquals(1, fields.size());
                Object[] o = fields.toArray();
                Variable v = (Variable) o[0];
                assertEquals(v.type, "java/io/IOException");
            }
        }
    }
}
