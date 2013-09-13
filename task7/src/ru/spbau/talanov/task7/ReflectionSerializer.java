package ru.spbau.talanov.task7;

import org.jetbrains.annotations.NotNull;

import java.util.Properties;
import java.util.Set;

/**
 * A serializer which can be used to serialize objects to .properties files.
 * A serializable object should have a no-argument constructor.
 */
public final class ReflectionSerializer {

    private ReflectionSerializer() {
    }

    /**
     * Serialize passed object to passed properties file.
     *
     * @param object     object to serialize.
     * @param properties properties to serialize to.
     * @param <T>        type of object being serialized.
     * @throws SerializerException if an error occurs during serialization.
     */
    public static <T> void serialize(@NotNull T object, @NotNull Properties properties) throws SerializerException {
        Set<String> propertyNames = PropertyUtils.getPropertyNames(object.getClass());
        for (String propertyName : propertyNames) {
            String propertyValue = PropertyUtils.getPropertyValue(object, propertyName);
            if (propertyValue != null) {
                properties.put(propertyName, propertyValue);
            }
        }
    }

}
