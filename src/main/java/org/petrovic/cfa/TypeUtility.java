package org.petrovic.cfa;

public class TypeUtility {

    private static final String LEFT_BRACKET = "[";
    private static final String SEMICOLON = ";";
    private static final String L = "L";

    enum BaseType {
        B, C, D, F, I, J, S, Z
    }

    public static Boolean omit(String reference) {
        return notObjectOrArrayOfObject(reference) || isJdkProvided(reference);
    }

    public static Boolean isArray(String reference) {
        return reference.startsWith(LEFT_BRACKET);
    }

    public static Boolean notObjectOrArrayOfObject(String reference) {
        return !reference.endsWith(SEMICOLON);
    }

    public static Boolean isJdkProvided(String objectType) {
        String t = normalize(objectType);
        return t.startsWith("java/") || t.startsWith("com/sun/");
    }

    public static String normalize(String objectType) {
        return objectType.substring(isArray(objectType) ? objectType.lastIndexOf(LEFT_BRACKET) + 2 : 1, objectType.length() - 1);
    }

    public static String toObjectReference(String reference) {
        return new StringBuilder(L).append(reference).append(SEMICOLON).toString();
    }
}
