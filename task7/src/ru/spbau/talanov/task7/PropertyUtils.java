package ru.spbau.talanov.task7;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import static ru.spbau.talanov.task7.ParseUtils.parse;

/**
 * This class contains some utility methods to help accessing objects' properties
 */
public final class PropertyUtils {

    @NotNull
    private static final String GETTER_PREFIX = "get";
    @NotNull
    private static final String SETTER_PREFIX = "set";

    /**
     * Returns a set of readable property names.
     * Names of properties returned correspond to properties which have at least a 'getProperty' method
     * which returns a value of a primitive type or wrapper type or String.
     *
     * @param klass a class properties of which are to be examined.
     * @return a set of property names.
     * @throws SecurityException if security manager is installed and access to klass's public member access is denied
     */
    @NotNull
    public static Set<String> getPropertyNames(@NotNull Class<?> klass) {
        Set<String> props = new HashSet<>();
        Method[] allMethods = klass.getMethods();

        for (Method m : allMethods) {
            if (isPropertyGetter(m)) {
                props.add(getPropertyNameFromGetter(m));
            }
        }

        return props;
    }

    /**
     * Returns a getPropertyName method's return value converted to string.
     *
     * @param object       object.
     * @param propertyName name of a property.
     * @return property value converted to string.
     * @throws SerializerException if some error occured.
     */
    @Nullable
    public static String getPropertyValue(@NotNull Object object, @NotNull String propertyName) throws SerializerException {
        Method getter = getGetter(object.getClass(), propertyName);
        try {
            Object value = getter.invoke(object);
            if (value == null) {
                return null;
            }
            return String.valueOf(value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SerializerException("Could not invoke getter for property "
                    + propertyName + " in class " + object.getClass().getSimpleName());
        }
    }

    /**
     * @param object        an object whose value is subject to change.
     * @param valueAsString a property value represented as a string.
     * @param propertyName  name of a property to change.
     * @throws ParseException      if passed value could not be used for parsing.
     * @throws SerializerException if some other error occured.
     */
    public static void setPropertyValue(@NotNull Object object, @Nullable String valueAsString,
                                        @NotNull String propertyName) throws ParseException, SerializerException {
        Method getter = getGetter(object.getClass(), propertyName);
        Class<?> propertyType = getter.getReturnType();
        Method setter = getSetter(object.getClass(), propertyName, propertyType);
        if (setter == null) {
            return;
        }
        try {
            setter.invoke(object, getValue(valueAsString, propertyType));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SerializerException("Could not invoke setter for property "
                    + propertyName + " in class " + object.getClass().getSimpleName());
        }
    }

    @Nullable
    private static Object getValue(@Nullable String valueAsString, @NotNull Class<?> propertyType)
            throws ParseException, SerializerException {
        if (valueAsString == null) {
            return null;
        }
        return parse(valueAsString, propertyType);
    }

    private static Method getGetter(Class<?> klass, String propertyName) throws SerializerException {
        try {
            Method setter = klass.getMethod(getterName(propertyName));
            if (!isPropertyGetter(setter)) {
                throw new SerializerException("Setter violates contract for property "
                        + propertyName + " in class " + klass.getSimpleName());
            }
            return setter;
        } catch (NoSuchMethodException e) {
            throw new SerializerException("Setter was not found for property "
                    + propertyName + " in class " + klass.getSimpleName(), e);
        }
    }

    @Nullable
    private static Method getSetter(@NotNull Class<?> klass, @NotNull String propertyName,
                                    @NotNull Class<?> propertyType) throws SerializerException {
        try {
            Method setter = klass.getMethod(setterName(propertyName), propertyType);
            if (!isPropertySetter(setter)) {
                throw new SerializerException("Setter violates contract for property "
                        + propertyName + " in class " + klass.getSimpleName());
            }
            return setter;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @NotNull
    private static String getterName(@NotNull String propertyName) {
        return GETTER_PREFIX + capitalize(propertyName);
    }

    @NotNull
    private static String setterName(@NotNull String propertyName) {
        return SETTER_PREFIX + capitalize(propertyName);
    }

    private static boolean isPropertyGetter(@NotNull Method method) {
        String name = method.getName();

        return name.startsWith(GETTER_PREFIX)
                && name.length() > GETTER_PREFIX.length()
                && 0 == method.getParameterTypes().length
                && isValidPropertyType(method.getReturnType())
                && accessorModifiersAreValid(method.getModifiers());
    }

    private static boolean isPropertySetter(@NotNull Method method) {
        String name = method.getName();

        return name.startsWith(SETTER_PREFIX)
                && name.length() > SETTER_PREFIX.length()
                && 1 == method.getParameterTypes().length
                && isValidPropertyType(method.getParameterTypes()[0])
                && void.class == method.getReturnType()
                && accessorModifiersAreValid(method.getModifiers());
    }

    private static boolean accessorModifiersAreValid(int m) {
        return !Modifier.isStatic(m)
                && !Modifier.isPrivate(m)
                && !Modifier.isAbstract(m);
    }

    private static boolean isValidPropertyType(Class<?> c) {
        return ParseUtils.isSupportedType(c);
    }

    @NotNull
    private static String getPropertyNameFromGetter(@NotNull Method method) {
        return getPropertyName(method, GETTER_PREFIX);
    }

    @NotNull
    private static String getPropertyName(@NotNull Method method, @NotNull String prefix) {
        return decapitalize(method.getName().substring(prefix.length()));
    }

    @NotNull
    private static String decapitalize(@NotNull String s) {
        char[] chars = s.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    @NotNull
    private static String capitalize(@NotNull String s) {
        char[] chars = s.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    private PropertyUtils() {
    }

}
