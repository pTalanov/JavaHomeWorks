package ru.spbau.talanov.task7;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Properties;
import java.util.Set;

import static ru.spbau.talanov.task7.PropertyUtils.setPropertyValue;

/**
 * A deserializer which can be used to deserialize objects from .properties files.
 * A deserializable object should have a no-argument constructor.
 * Only writable properties are deserialized.
 */
public final class ReflectionDeSerializer {

    /**
     * Deserializes an object of specified type from passed properties object.
     *
     * @param properties properties to parse from.
     * @param type       a class object of the object to deserialize.
     * @param <T>        a type of the object to deserialize.
     * @return deserialized object.
     * @throws SerializerException if any exception occurs during deserialization process.
     */
    @NotNull
    public static <T> T deserialize(@NotNull Properties properties, @NotNull Class<T> type)
            throws SerializerException, ParseException {
        T object = constructObject(type);

        Set<String> propertiesWithNotNullValue = properties.stringPropertyNames();
        for (String property : propertiesWithNotNullValue) {
            setPropertyValue(object, properties.getProperty(property), property);
        }
        Set<String> propertiesWithNullValue = PropertyUtils.getPropertyNames(type);
        propertiesWithNullValue.removeAll(propertiesWithNotNullValue);

        for (String propertyWithNullValue : propertiesWithNullValue) {
            setPropertyValue(object, null, propertyWithNullValue);
        }

        return object;
    }

    @NotNull
    private static <T> T constructObject(@NotNull Class<T> klass) throws SerializerException {
        try {
            Constructor<T> constructor = klass.getConstructor(new Class[0]);
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new SerializerException(klass + " has no default constructor.", e);
        } catch (InstantiationException e) {
            throw new SerializerException(klass + " is abstract.", e);
        } catch (IllegalAccessException e) {
            throw new SerializerException(klass + "'s default constructor is inaccessible", e);
        } catch (InvocationTargetException e) {
            throw new SerializerException(klass + "'s constructor has thrown an exception.", e);
        }
    }

    private ReflectionDeSerializer() {
    }
}
