package ru.spbau.talanov.task7;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.text.ParseException;
import java.util.Properties;

/**
 * Task 7 entry point.
 */
public final class Main {

    @NotNull
    private static final String HELP_MESSAGE = "Usage: Main <.properties file>";

    /**
     * task#7 entry point.
     * Parses a Student object from passed .properties file and writes it back with an average grade increased.
     *
     * @param args first argument - a .properties file containing serialized Student object.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(HELP_MESSAGE);
            return;
        }

        Student student = parseStudent(args[0]);

        if (student == null) {
            System.out.println("Failed to parse student.");
            return;
        }

        student.setAvgGrade(1 + student.getAvgGrade());

        if (!writeStudent(args[0], student)) {
            System.out.println("Failed to write student.");
        }
    }

    private static boolean writeStudent(@NotNull String file, @NotNull Student student) {
        Properties studentData = new Properties();

        try {
            ReflectionSerializer.serialize(student, studentData);
        } catch (SerializerException e) {
            e.printStackTrace();
            return false;
        }

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            studentData.store(writer, null);
            return true;
        } catch (IOException e) {
            System.out.println("An I/O exception occurred on attempt to write properties file.");
        }

        return false;
    }

    @Nullable
    private static Student parseStudent(@NotNull String file) {
        Properties properties = loadProperties(file);
        if (properties == null) {
            return null;
        }

        try {
            return ReflectionDeSerializer.deserialize(properties, Student.class);
        } catch (SerializerException | ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    private static Properties loadProperties(@NotNull String file) {

        try (Reader reader = new BufferedReader(new FileReader(file))) {
            Properties p = new Properties();
            p.load(reader);
            return p;
        } catch (FileNotFoundException e) {
            System.out.println("Can't open: " + file);
        } catch (IllegalArgumentException e) {
            System.out.println("Bad properties file.");
        } catch (IOException e) {
            System.out.println("An I/O exception occurred on attempt to read properties file.");
        }
        return null;
    }

}
