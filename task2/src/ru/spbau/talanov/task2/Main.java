package ru.spbau.talanov.task2;

import java.util.regex.Pattern;

/**
 * Task 2 entry point.
 */
public final class Main {
    /**
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: Main <path>");
            return;
        }

        PatternFilter filter = new PatternFilter(Pattern.compile("^[^\\.].*"));
        FilesystemWalker walker = new FilesystemWalker(filter, new DirectoryTreePrinter());
        walker.walk(args[0]);
    }

}
