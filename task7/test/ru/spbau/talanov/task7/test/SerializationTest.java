package ru.spbau.talanov.task7.test;

import org.junit.Test;
import ru.spbau.talanov.task7.ReflectionDeSerializer;
import ru.spbau.talanov.task7.ReflectionSerializer;
import ru.spbau.talanov.task7.SerializerException;
import ru.spbau.talanov.task7.Student;

import java.util.Properties;

import static junit.framework.Assert.assertEquals;

@SuppressWarnings("UnusedDeclaration")
public final class SerializationTest {
    @org.junit.Test
    public void testSerializingStudent() throws Exception {
        Student student = new Student("a", "b", 3, 3.1);
        Properties properties = new Properties();
        ReflectionSerializer.serialize(student, properties);
        assertEquals(properties.getProperty("name"), "a");
        assertEquals(properties.getProperty("surname"), "b");
        assertEquals(properties.getProperty("age"), "3");
        assertEquals(properties.getProperty("avgGrade"), "3.1");
    }

    @org.junit.Test
    public void testDeserializingStudent() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("name", "a");
        properties.setProperty("surname", "b");
        properties.setProperty("age", "300");
        properties.setProperty("avgGrade", "33.5");
        Student student = ReflectionDeSerializer.deserialize(properties, Student.class);
        assertEquals(student.getName(), "a");
        assertEquals(student.getSurname(), "b");
        assertEquals(student.getAge(), 300);
        assertEquals(student.getAvgGrade(), 33.5);
    }


    public static class WrappedInt {
        private Integer a = 3;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }
    }

    @Test
    public void testDeserializingWrappedTypes() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("a", "33");
        WrappedInt wrappedInt = ReflectionDeSerializer.deserialize(properties, WrappedInt.class);
        assertEquals(wrappedInt.getA(), (Object) 33);
    }


    public static class NoSetter {
        private Integer a = 3;

        public Integer getA() {
            return a;
        }
    }

    @Test
    public void testNothingHappensWithoutSetter() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("a", "33");
        NoSetter noSetter = ReflectionDeSerializer.deserialize(properties, NoSetter.class);
        assertEquals(noSetter.getA(), (Object) 3);
    }

    public static class Primitive {
        private int a = 3;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    @Test(expected = SerializerException.class)
    public void testPrimitiveIsSetToNull() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("a", "null");
        ReflectionDeSerializer.deserialize(properties, Primitive.class);
    }

    public static class Nulls {
        private Short a = 1;
        private String s = "a";

        public Short getA() {
            return a;
        }

        public void setA(Short a) {
            this.a = a;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Nulls nulls = (Nulls) o;

            return !(a != null ? !a.equals(nulls.a) : nulls.a != null) && !(s != null ? !s.equals(nulls.s) : nulls.s != null);

        }

        @Override
        public int hashCode() {
            int result = a != null ? a.hashCode() : 0;
            result = 31 * result + (s != null ? s.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Nulls{" +
                    "a=" + a +
                    ", s='" + s + '\'' +
                    '}';
        }
    }

    @Test
    public void testNulls() throws Exception {
        Nulls original = new Nulls();
        original.setA(null);
        original.setS(null);
        Properties properties = new Properties();
        ReflectionSerializer.serialize(original, properties);
        Nulls deserialized = ReflectionDeSerializer.deserialize(properties, Nulls.class);
        assertEquals(original, deserialized);
    }
}
