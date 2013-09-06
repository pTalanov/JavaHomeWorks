package ru.spbau.talanov.task2;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * A regular expression filename filter.
 */
public class PatternFilter implements FilenameFilter {

    @NotNull
    private final Pattern pattern;

    /**
     * Constructs a new PatternFilter using specified pattern.
     *
     * @param pattern a pattern to use as a filter.
     */
    public PatternFilter(@NotNull Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Tests if passed file name does not match the regular expression.
     *
     * @param dir  a directory which contains passed filename.
     * @param name a name of a file to test.
     * @return <code>true</code> if passed name matches pattern else <code>false</code>.
     */
    @Override
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }
}
