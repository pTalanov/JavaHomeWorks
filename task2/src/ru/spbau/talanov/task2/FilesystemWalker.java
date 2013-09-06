package ru.spbau.talanov.task2;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Walks through a directory tree.
 * Optionally skips files and directories that match a PatternFilter.
 * Pass WalkerCallback object to use the walker.
 */
public final class FilesystemWalker {

    @NotNull
    private final FilenameFilter filter;
    @NotNull
    private final WalkerCallback callback;

    /**
     * Constructs a new FilesystemWalker object using passed FilenameFilter to filter directory entries.
     * If FilenameFilter is null then no filtering will be applied.
     *
     * @param filter   for the files.
     * @param callback to execute user code.
     */
    public FilesystemWalker(@NotNull FilenameFilter filter, @NotNull WalkerCallback callback) {
        this.filter = filter;
        this.callback = callback;
    }

    /**
     * Starts lexicographically walking through directories using depth-first search.
     * Uses passed root as traversal's root.
     *
     * @param root traversal root.
     */
    public void walk(@NotNull String root) {
        walk(new File(root), true);
    }

    private void traverseDirectory(@NotNull File directory, boolean isLastDirectory) {
        callback.directoryOpened(directory, isLastDirectory);

        File[] children = directory.listFiles(filter);
        if (children == null) {
            return;
        }
        Arrays.sort(children, new FilenameComparator());

        for (int i = 0; i != children.length; i++) {
            boolean isLastEntry = i == children.length - 1;
            walk(children[i], isLastEntry);
        }

        callback.directoryClosed(directory);
    }

    private void walk(@NotNull File file, boolean lastEntry) {
        if (!isReadable(file)) {
            if (isFileOrDirectory(file)) {
                callback.fileVisited(file, true);
            }
            return;
        }

        if (file.isFile()) {
            callback.fileVisited(file, false);
            return;
        }

        if (file.isDirectory()) {
            traverseDirectory(file, lastEntry);
        }
    }

    private boolean isReadable(@NotNull File directory) {
        try {
            return directory.canRead();
        } catch (SecurityException se) {
            return false;
        }
    }

    private boolean isFileOrDirectory(@NotNull File file) {
        try {
            return file.isFile() || file.isDirectory();
        } catch (SecurityException se) {
            return true;
        }
    }

    /**
     * A a lexicographic order comparator for files.
     */
    private static class FilenameComparator implements Comparator<File> {

        /**
         * Compares two files using their names for comparison.
         *
         * @param f1 first file to compare.
         * @param f2 second file to compare.
         * @return 0 if file names are lexicographically equal,
         *         a positive value if f1's name is lexicographically greater than f2's name,
         *         else a negative value.
         */
        @Override
        public int compare(File f1, File f2) {
            return f1.getName().compareTo(f2.getName());
        }
    }

}
