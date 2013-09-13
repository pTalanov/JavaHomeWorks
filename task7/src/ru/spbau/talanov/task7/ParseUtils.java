package ru.spbau.talanov.task7;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains methods for primitive types, wrappers and string parsing.
 */
public final class ParseUtils {

    @NotNull
    private static final Collection<Class<?>> SUPPORTED_TYPES = Arrays.<Class<?>>asList(
            Byte.class, Byte.TYPE, Character.class, Character.TYPE, Short.class, Short.TYPE, Integer.class, Integer.TYPE,
            Long.class, Long.TYPE, Float.class, Float.TYPE, Double.class, Double.TYPE, String.class
    );
    @NotNull
    public final static Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<>();

    static {
        PRIMITIVE_TO_WRAPPER.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_TO_WRAPPER.put(Byte.TYPE, Byte.class);
        PRIMITIVE_TO_WRAPPER.put(Short.TYPE, Short.class);
        PRIMITIVE_TO_WRAPPER.put(Character.TYPE, Character.class);
        PRIMITIVE_TO_WRAPPER.put(Integer.TYPE, Integer.class);
        PRIMITIVE_TO_WRAPPER.put(Long.TYPE, Long.class);
        PRIMITIVE_TO_WRAPPER.put(Float.TYPE, Float.class);
        PRIMITIVE_TO_WRAPPER.put(Double.TYPE, Double.class);
    }

    /**
     * Checks if passed type is supported by this parser.
     *
     * @param type a type.
     * @return true if the type is supported else false.
     */
    public static boolean isSupportedType(@NotNull Class<?> type) {
        return SUPPORTED_TYPES.contains(type);
    }

    /**
     * Parses value of passed typed from passed string.
     *
     * @param stringValue a string value.
     * @param type        a type to parse.
     * @return parsed value (boxing may occur).
     * @throws ParseException      if parse error occurs.
     * @throws SerializerException if <code>stringValue</code> is not a correct value for this <code>type</code>.
     */
    public static Object parse(@NotNull String stringValue, @NotNull Class<?> type) throws ParseException, SerializerException {
        assert isSupportedType(type) : "Should call isSupported";

        if (type.equals(String.class)) {
            return stringValue;
        }
        boolean nullProhibited = type.isPrimitive();
        if (stringValue.equals("null")) {
            if (nullProhibited) {
                throw new SerializerException("Null passed as a value for primitive type " + type.getSimpleName());
            } else {
                return null;
            }
        }
        Class<?> wrapperType = getWrapperType(type);
        try {
            Method valueOfMethod = wrapperType.getMethod("valueOf", String.class);
            return valueOfMethod.invoke(null, stringValue);

        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return new IllegalStateException("Error while calling valueOf method of class " + wrapperType.getSimpleName(), e);
        }
    }

    @NotNull
    private static Class<?> getWrapperType(Class<?> type) {
        if (type.isPrimitive()) {
            return PRIMITIVE_TO_WRAPPER.get(type);
        } else {
            return type;
        }
    }

    private ParseUtils() {
    }

}
