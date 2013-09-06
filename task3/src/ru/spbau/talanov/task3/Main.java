package ru.spbau.talanov.task3;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Task 3 entry point.
 */
public final class Main {

    private static final String HELP_MESSAGE = "Usage: compress <archive> [File1, ...] | decompress <archive>";

    /**
     * Entry point.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(HELP_MESSAGE);
            return;
        }

        String operation = args[0];
        String archive = args[1];

        switch (operation) {
            case "compress":
                compress(archive, getFileArgs(args));
                break;
            case "decompress":
                extract(archive);
                break;
            default:
                System.out.println(HELP_MESSAGE);
                break;
        }
    }

    private static void compress(@NotNull String archive, @NotNull Collection<String> filesOrDirs) {
        try (ArchiveWriter archiveWriter = new ArchiveWriter(new File(archive))) {
            Compressor compressor = new Compressor(archiveWriter);
            try {
                for (String root : filesOrDirs) {
                    compressor.write(new File(root));
                }
            } catch (IOException e) {
                System.out.println("An I/O error occurred while adding files to the archive: " + e.getMessage());
            }
        } catch (SecurityException | FileNotFoundException se) {
            System.out.println("Cannot open the archive file.");
        } catch (IOException e) {
            System.out.println("An I/O error occurred while initializing archive file.");
        }
    }

    private static void extract(@NotNull String archive) {
        try (Extractor extractor = new Extractor(new File(archive))) {
            try {
                extractor.extract();
            } catch (InvalidArchiveFormatException e) {
                System.out.println("Invalid archive format: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("An I/O error occurred while extracting files.");
            }
        } catch (SecurityException | FileNotFoundException e) {
            System.out.println("Cannot open the archive file");
        } catch (InvalidArchiveFormatException e) {
            System.out.println("Invalid archive format: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An I/O error occurred while initializing archive extractor.");
        }
    }

    @NotNull
    private static List<String> getFileArgs(String[] args) {
        List<String> files = new ArrayList<>();
        for (int i = 2; i != args.length; i++) {
            files.add(args[i]);
        }
        return files;
    }

}