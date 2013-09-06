package ru.spbau.talanov.task3;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Writes an entry for each file in the archive.
 */
public final class ArchiveWriter implements Closeable {

    @NotNull
    private final ZipOutputStream zipOutputStream;

    /**
     * Constructs a new ArchiveWriter object.
     * The archive will be written to a file denoted by passed abstract path name.
     *
     * @param zipFileName a new archive's name.
     * @throws java.io.FileNotFoundException if file was not found or is a directory rather than a regular file.
     * @throws SecurityException             if a SecurityManager exists and
     *                                       the application has no rights to write a file denoted by zipFileName.
     */
    public ArchiveWriter(File zipFileName) throws IOException, SecurityException {
        zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
    }

    /**
     * Writes contents of a file denoted by 'file' parameter to the archive using passed relative name.
     *
     * @param relativeName relative name to be written to the archive.
     * @param file         an abstract path name of a file to be written.
     * @throws java.io.FileNotFoundException if a file does not exist or is a directory or it cannot be opened for reading.
     * @throws SecurityException             if a SecurityManager exists and access to the file is denied.
     * @throws java.io.IOException           if an I/O error occurs.
     */
    public void writeFile(@NotNull String relativeName, @NotNull File file) throws SecurityException, IOException {
        try (RandomAccessFile inputFile = new RandomAccessFile(file, "r")) {
            ZipEntry zipEntry = new ZipEntry(relativeName);
            zipOutputStream.putNextEntry(zipEntry);
            byte[] buffer = new byte[Compressor.BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputFile.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, bytesRead);
            }
            zipOutputStream.closeEntry();
        }
    }

    /**
     * Closes the underlying zip file stream.
     *
     * @throws java.io.IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        zipOutputStream.close();
    }

}
