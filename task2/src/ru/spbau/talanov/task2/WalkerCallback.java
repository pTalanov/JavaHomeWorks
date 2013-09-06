package ru.spbau.talanov.task2;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * An interface for objects interested in FilesystemWalker's traversal state.
 */
public interface WalkerCallback {

    /**
     * Is called if a new directory was opened.
     *
     * @param directory          a directory that is opened.
     * @param isLastSubDirectory if this is the last subdirectory of the current directory
     */
    void directoryOpened(@NotNull File directory, boolean isLastSubDirectory);

    /**
     * Called if a directory's children traversal is complete.
     *
     * @param directory a directory that is closed.
     */
    void directoryClosed(@NotNull File directory);

    /**
     * Is called if access to a file is denied.
     *
     * @param file           a file visited.
     * @param isAccessDenied true if access is denied by the operating system.
     */
    void fileVisited(@NotNull File file, boolean isAccessDenied);
}
