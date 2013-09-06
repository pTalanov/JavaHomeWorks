package ru.spbau.talanov.task3;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Walks through a directory tree and adds visited files to the archive.
 */
public final class Compressor {

    /*
     * Standard buffer size.
     */
    public static final int BUFFER_SIZE = 8192;

    @NotNull
    private final ArchiveWriter writer;

    /**
     * Constructs a new ArchiveWriter object.
     * Passed archive writer is used to write visited files to the archive.
     *
     * @param archiveWriter an archive writer to use.
     */
    public Compressor(@NotNull ArchiveWriter archiveWriter) {
        writer = archiveWriter;
    }

    /**
     * Add zip entry with contents of the file.
     *
     * @param file to write to the archive.
     */
    public void write(@NotNull File file) throws IOException {
        if (!isReadable(file)) {
            System.out.println("> " + getRelativeName(file) + "\t<access_denied>");
            return;
        }

        if (file.isFile()) {
            writeFile(file);
            return;
        }

        if (file.isDirectory()) {
            writeDirectory(file);
        }
    }

    private void writeDirectory(@NotNull File dir) throws IOException {
        File[] children = dir.listFiles();
        if (children != null) {
            Arrays.sort(children);

            for (File child : children) {
                write(child);
            }
        }
    }

    private void writeFile(@NotNull File file) throws IOException {
        try {
            String relativeFileName = getRelativeName(file);
            writer.writeFile(relativeFileName, file);
            report(file, "added to archive");
        } catch (SecurityException | FileNotFoundException se) {
            report(file, "access denied");
        }
    }

    private boolean isReadable(@NotNull File directory) {
        try {
            return directory.canRead();
        } catch (SecurityException se) {
            return false;
        }
    }

    @NotNull
    private static String getRelativeName(@NotNull File file) {
        Path rootPath = new File("").getAbsoluteFile().toPath();
        Path filePath = file.getAbsoluteFile().toPath();
        Path relativePath = rootPath.relativize(filePath);
        return relativePath.toString();
    }

    private void report(@NotNull File file, @NotNull String info) {
        System.out.println(file.getPath() + "\t" + info);
    }
}
