package ru.spbau.talanov.task2;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Prints directory tree to <code>System.out</code>.
 */
public final class DirectoryTreePrinter implements WalkerCallback {

    private static final String ACCESS_DENIED_MESSAGE = " (access denied)";
    private static final String NAME_PREFIX = "|_";

    private static final char LAST_DIRECTORY_LEVEL_DELIMITER = ' ';
    private static final char NORMAL_DIRECTORY_LEVEL_DELIMITER = '|';
    private static final char FILL_SYMBOL = ' ';

    @NotNull
    private final StringBuilder prefix = new StringBuilder();

    private boolean isRootEntry = true;

    @Override
    public void directoryOpened(@NotNull File directory, boolean isLastSubDirectory) {
        String directoryName = directory.getName();

        printEntry(directoryName, false);
        extendPrefix(directoryName, isLastSubDirectory);

        isRootEntry = false;
    }

    @Override
    public void directoryClosed(@NotNull File directory) {
        reducePrefix(directory.getName());
    }

    @Override
    public void fileVisited(@NotNull File file, boolean isAccessDenied) {
        printEntry(file.getName(), isAccessDenied);
    }

    private void extendPrefix(@NotNull String directoryName, boolean isLastDirectory) {
        prefix.append(isLastDirectory ? LAST_DIRECTORY_LEVEL_DELIMITER : NORMAL_DIRECTORY_LEVEL_DELIMITER);

        int fillLength = directoryName.length() + NAME_PREFIX.length() - 1;

        for (int i = 0; i != fillLength; i++) {
            prefix.append(FILL_SYMBOL);
        }
    }

    private void reducePrefix(@NotNull String directoryName) {
        int newPrefixLength = prefix.length();

        newPrefixLength -= directoryName.length();
        newPrefixLength -= NAME_PREFIX.length();

        prefix.setLength(Math.max(0, newPrefixLength));
    }

    private void printEntry(@NotNull String name, boolean isAccessDenied) {
        System.out.println(prefix.toString() + constructEntryString(name, isAccessDenied));
    }

    private String constructEntryString(@NotNull String name, boolean isAccessDenied) {
        StringBuilder entry = new StringBuilder();
        if (!isRootEntry) {
            entry.append(NAME_PREFIX);
        }
        entry.append(name);
        if (isAccessDenied) {
            entry.append(ACCESS_DENIED_MESSAGE);
        }
        return entry.toString();
    }

}
