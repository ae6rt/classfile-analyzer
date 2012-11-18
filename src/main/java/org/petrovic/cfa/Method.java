package org.petrovic.cfa;

public class Method {
    public final String name;
    public final String signature;

    public Method(String name, String signature) {
        this.name = name;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Method method = (Method) o;

        if (name != null ? !name.equals(method.name) : method.name != null) return false;
        if (signature != null ? !signature.equals(method.signature) : method.signature != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Method{" +
                "name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
