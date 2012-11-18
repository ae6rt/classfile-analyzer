package org.petrovic.cfa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JarFileTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadingJarFile() {
        JarFile jarFile = new JarFile(new File("testdata/servlet-api.jar"));
        assertTrue(jarFile.containsClass("javax/servlet/http/HttpSessionEvent"));
        assertTrue(jarFile.containsClass("javax/servlet/http/HttpSessionEvent.class"));
        assertFalse(jarFile.containsClass("servlet/http/HttpSessionEvent"));
    }

    @Test
    public void testMultipleJarsContainReferenceClass() {
        JarFile jar1 = new JarFile(new File("testdata/servlet-api.jar"));
        JarFile jar2 = new JarFile(new File("testdata/servlet-api.jar"));
        List<JarFile> jarFileList = new LinkedList<JarFile>();
        jarFileList.add(jar1);
        jarFileList.add(jar2);
        JarFile.classIsPresent(jarFileList, "javax/servlet/http/HttpSessionEvent");
    }
}
