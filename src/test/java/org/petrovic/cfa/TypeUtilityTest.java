package org.petrovic.cfa;

import org.junit.Test;

import static junit.framework.Assert.*;

public class TypeUtilityTest {

    private String type = "com/example/Foo";
    private String object = String.format("L%s;", type);
    private String arrayOfObject = String.format("[L%s;", type);
    private String array2OfObject = String.format("[[L%s;", type);
    private String javaLangString = "Ljava/lang/String;";
    private String javaLangStringArray = "[Ljava/lang/String;";
    private String comSun = "Lcom/sun/Foo;";

    @Test
    public void testOmit() throws Exception {
        for (TypeUtility.BaseType t : TypeUtility.BaseType.values()) {
            assertTrue(TypeUtility.omit(t.name()));
        }
        assertTrue(TypeUtility.omit(javaLangString));
        assertTrue(TypeUtility.omit(javaLangStringArray));
        assertTrue(TypeUtility.omit(comSun));
        assertFalse(TypeUtility.omit(object));
        assertFalse(TypeUtility.omit(arrayOfObject));
        assertFalse(TypeUtility.omit(array2OfObject));
    }

    @Test
    public void testIsArray() throws Exception {
        assertTrue(TypeUtility.isArray(arrayOfObject));
        assertTrue(TypeUtility.isArray(array2OfObject));
        assertFalse(TypeUtility.isArray(object));
    }

    @Test
    public void testNotObjectOrArrayOfObject() throws Exception {
        assertTrue(TypeUtility.notObjectOrArrayOfObject(TypeUtility.BaseType.B.name()));
        assertFalse(TypeUtility.notObjectOrArrayOfObject(object));
        assertFalse(TypeUtility.notObjectOrArrayOfObject(arrayOfObject));
        assertFalse(TypeUtility.notObjectOrArrayOfObject(arrayOfObject));
    }

    @Test
    public void testIsJdkProvided() throws Exception {
        for (String s : new String[]{javaLangString, javaLangStringArray, comSun}) {
            assertTrue(s, TypeUtility.isJdkProvided(s));
        }
        for (String s : new String[]{object, arrayOfObject, array2OfObject}) {
            assertFalse(s, TypeUtility.isJdkProvided(s));
        }
    }

    @Test
    public void testNormalize() throws Exception {
        assertEquals(type, TypeUtility.normalize(object));
        assertEquals(type, TypeUtility.normalize(arrayOfObject));
        assertEquals(type, TypeUtility.normalize(array2OfObject));
    }

    @Test
    public void testToObjectReference() {
        assertEquals(String.format("L%s;", type), TypeUtility.toObjectReference(type));
    }
}
