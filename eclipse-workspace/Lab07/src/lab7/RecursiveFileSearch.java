package lab7;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursiveFileSearch {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java RecursiveFileSearch <directory> <case-sensitive(true/false)> <filename1> [<filename2> ...]");
            return;
        }

        String directoryPath = args[0];
        boolean caseSensitive = Boolean.parseBoolean(args[1]);
        List<String> fileNames = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            fileNames.add(args[i]);
        }

        try {
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("The specified directory does not exist or is not a directory.");
                return;
            }

            Map<String, Integer> occurrences = new HashMap<>();
            for (String fileName : fileNames) {
                occurrences.put(fileName, 0);
            }

            searchFiles(directory, fileNames, caseSensitive, occurrences);

            // Print results
            for (String fileName : fileNames) {
                int count = occurrences.get(fileName);
                if (count > 0) {
                    System.out.println("File '" + fileName + "' found " + count + " times.");
                } else {
                    System.out.println("File '" + fileName + "' was not found.");
                }
            }

        } catch (SecurityException se) {
            System.out.println("Error: Insufficient permissions to access the directory.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static void searchFiles(File directory, List<String> fileNames, boolean caseSensitive, Map<String, Integer> occurrences) {
        if (directory == null || fileNames == null || occurrences == null) {
            throw new IllegalArgumentException("Directory, filenames, and occurrences map cannot be null.");
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            try {
                if (file.isDirectory()) {
                    searchFiles(file, fileNames, caseSensitive, occurrences);
                } else {
                    for (String fileName : fileNames) {
                        if (matchesFileName(file.getName(), fileName, caseSensitive)) {
                            System.out.println("File found at: " + file.getAbsolutePath());
                            occurrences.put(fileName, occurrences.get(fileName) + 1);
                        }
                    }
                }
            } catch (SecurityException se) {
                System.out.println("Unable to access file: " + file.getAbsolutePath());
            }
        }
    }

    private static boolean matchesFileName(String foundFileName, String targetFileName, boolean caseSensitive) {
        if (caseSensitive) {
            return foundFileName.equals(targetFileName);
        } else {
            return foundFileName.equalsIgnoreCase(targetFileName);
        }
    }
}
