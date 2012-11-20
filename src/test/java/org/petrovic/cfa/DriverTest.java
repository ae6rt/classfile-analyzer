package org.petrovic.cfa;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DriverTest {
    private File main;
    private File test;
    private List<File> dirs;
    private Driver driver;
    Map<File, Collection<File>> fileCollectionMap;

    @Before
    public void setUp() throws Exception {
        main = new File("testdata/main");
        test = new File("testdata/test");
        dirs = new LinkedList<File>();
        dirs.add(main);
        dirs.add(test);
        driver = new Driver(dirs);
        fileCollectionMap = driver.filesToScan();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFileCollection() throws IOException {
        Collection<File> mainFiles = fileCollectionMap.get(main);
        Collection<File> testFiles = fileCollectionMap.get(test);
        assertEquals(2, mainFiles.size());
        assertEquals(1, testFiles.size());
    }

    @Test
    public void traceTest() throws IOException {
        Boolean debug = Boolean.valueOf(System.getProperty("traceDebug"));
        driver.analyze(debug);
    }

    @Test
    @Ignore
    public void testAnalyze() throws IOException {
        Collection<ClassClassViz> classFileReports = driver.analyze();
        assertEquals(fileCollectionMap.get(main).size() + fileCollectionMap.get(test).size(), classFileReports.size());

        for (ClassClassViz s : classFileReports) {
            Map<Method, List<Variable>> methodLocalMap = s.getMethodLocalMap();
            Collection<Variable> fields = s.getFields();
            Set<Method> methods = methodLocalMap.keySet();
            if (s.getClassName().equals("org/apache/commons/io/filefilter/DirectoryFileFilter")) {
                for (Method m : methods) {
                    List<Variable> variables = methodLocalMap.get(m);
                    assertEquals("Method should have no object locals not in the JDK", 0, variables.size());
                }
                assertEquals("org/apache/commons/io/filefilter/AbstractFileFilter", TypeUtility.normalize(s.getSuperName()));
                assertEquals(0, s.getImplementedInterfaces().size());
                assertEquals(3, methods.size());
                assertEquals(2, fields.size());
                for (Variable v : fields) {
                    assertEquals("org/apache/commons/io/filefilter/IOFileFilter", v.type);
                }
            }
            if (s.getClassName().equals("org/apache/commons/io/output/BrokenOutputStream")) {
                for (Method m : methods) {
                    List<Variable> variables = methodLocalMap.get(m);
                    assertEquals("Method should have no object locals not in the JDK", 0, variables.size());
                }
                assertNull(s.getSuperName());
                assertEquals(0, s.getImplementedInterfaces().size());
                assertEquals(0, fields.size());
                assertEquals(5, methods.size());
                assertEquals(0, s.getMethodExceptionsMap().get(new Method("write", "(I)V")).size());
                assertEquals(0, s.getMethodExceptionsMap().get(new Method("flush", "()V")).size());
                assertEquals(0, s.getMethodExceptionsMap().get(new Method("close", "()V")).size());
            }
            if (s.getClassName().equals("cucumber/runtime/java/JavaStepDefinition")) {
                System.out.println(s);
                assertEquals(8, methods.size());
                assertEquals(1, s.getImplementedInterfaces().size());
                assertEquals(2, fields.size());

                assertEquals(1, methodLocalMap.get(new Method("execute", "(Lgherkin/I18n;[Ljava/lang/Object;)V")).size());
                assertEquals(1, methodLocalMap.get(new Method("matchedArguments", "(Lgherkin/formatter/model/Step;)Ljava/util/List;")).size());
                System.out.println(methodLocalMap.get(new Method("getLocation", "(Z)Ljava/lang/String;")));
                assertEquals(1, methodLocalMap.get(new Method("getLocation", "(Z)Ljava/lang/String;")).size());

                /*assertNull(s.getSuperName());
                assertEquals(0, s.getImplementedInterfaces().size());
                assertEquals(0, fields.size());
                assertEquals(5, methods.size());
                assertEquals(0, s.getMethodExceptionsMap().get(new Method("write", "(I)V")).size());
                assertEquals(0, s.getMethodExceptionsMap().get(new Method("flush", "()V")).size());
                assertEquals(0, s.getMethodExceptionsMap().get(new Method("close", "()V")).size());*/
            }

        }
    }
}
