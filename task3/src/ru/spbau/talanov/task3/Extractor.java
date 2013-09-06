package ru.spbau.talanov.task3;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import static ru.spbau.talanov.task3.Compressor.BUFFER_SIZE;

/**
 * Extracts files from archives created by ArchiveWriter.
 */
public final class Extractor implements Closeable {

    @NotNull
    private final ZipInputStream zipInputStream;

    /**
     * Constructs a new Extractor that will be used to extract files
     * from passed archive file to current working directory.
     *
     * @param archive an archive file
     * @throws java.io.FileNotFoundException if file does not exist or it is a directory
     *                                       or some other reason the file cannot be opened for reading.
     * @throws java.io.IOException           if an I/O error occurs.
     * @throws SecurityException             if SecurityManager exists and access to the archive file is denied.
     * @throws InvalidArchiveFormatException if passed archive file is ill-formed.
     */
    public Extractor(File archive) throws IOException, SecurityException, InvalidArchiveFormatException {
        this.zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(archive)));

    }

    /**
     * Starts extraction.
     *
     * @throws InvalidArchiveFormatException if the archive is ill-formed.
     * @throws java.io.IOException           if an I/O error occurs.
     */
    public void extract() throws InvalidArchiveFormatException, IOException {
        while (extractFile()) {
        }
    }

    private boolean extractFile() throws IOException, InvalidArchiveFormatException {
        try {
            ZipEntry entry = zipInputStream.getNextEntry();
            if (entry == null) {
                return false;
            }
            String filename = entry.getName();
            File file = new File(filename);
            try {
                boolean dirCreated = file.getAbsoluteFile().getParentFile().mkdirs();
                if (!dirCreated && !file.getAbsoluteFile().getParentFile().isDirectory()) {
                    report(file, "error creating directory");
                    return true;
                }

                writeFile(file);
                return true;
            } catch (SecurityException se) {
                zipInputStream.closeEntry();
                report(file, "access denied");
                return true;
            }
        } catch (ZipException e) {
            zipInputStream.close();
            throw new InvalidArchiveFormatException("Unable to read zip entry", e);
        } catch (IOException ioe) {
            zipInputStream.close();
            throw ioe;
        }
    }

    private void writeFile(@NotNull File file) throws IOException {
        try (FileOutputStream unzippedFile = new FileOutputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;
            while ((bytesRead = zipInputStream.read(buffer)) > 0) {
                try {
                    unzippedFile.write(buffer, 0, bytesRead);
                } catch (IOException e) {
                    zipInputStream.closeEntry();
                    report(file, "error");
                    return;
                }
            }
            report(file, "successfully extracted");
        } catch (FileNotFoundException e) {
            zipInputStream.closeEntry();
            report(file, "access denied");
        }
    }

    /**
     * Closes a zip archive stream.
     *
     * @throws java.io.IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        zipInputStream.close();
    }

    private void report(@NotNull File f, @NotNull String info) {
        System.out.println(f.getPath() + "\t" + info);
    }

}
