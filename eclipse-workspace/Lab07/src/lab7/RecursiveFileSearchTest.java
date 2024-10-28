package lab7;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RecursiveFileSearchTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    // Test Case 1: Path to an empty directory
    @Test
    public void testEmptyDirectory() throws IOException {
        File emptyDir = tempFolder.newFolder("emptyDir");

        List<String> fileNames = new ArrayList<>();
        fileNames.add("testFile.txt");
        Map<String, Integer> occurrences = new HashMap<>();
        occurrences.put("testFile.txt", 0);

        RecursiveFileSearch.searchFiles(emptyDir, fileNames, false, occurrences);

        assertEquals("File should not be found in an empty directory", 0, occurrences.get("testFile.txt").intValue());
    }

    // Test Case 2: Directory path that does not exist
    @Test
    public void testNonExistentDirectory() {
        File nonExistentDir = new File("nonExistentDir");

        List<String> fileNames = new ArrayList<>();
        fileNames.add("testFile.txt");
        Map<String, Integer> occurrences = new HashMap<>();
        occurrences.put("testFile.txt", 0);

        RecursiveFileSearch.searchFiles(nonExistentDir, fileNames, false, occurrences);

        assertEquals("Non-existent directory should return zero occurrences",
                0, occurrences.get("testFile.txt").intValue());
    }

    // Test Case 3: Valid directory with nested subdirectories and the file being present
    @Test
    public void testNestedDirectoryFileFound() throws IOException {
        File rootDir = tempFolder.newFolder("rootDir");
        File subDir = new File(rootDir, "subDir");
        subDir.mkdir();
        File targetFile = new File(subDir, "testFile.txt");
        assertTrue("File creation failed", targetFile.createNewFile());

        List<String> fileNames = new ArrayList<>();
        fileNames.add("testFile.txt");
        Map<String, Integer> occurrences = new HashMap<>();
        occurrences.put("testFile.txt", 0);

        RecursiveFileSearch.searchFiles(rootDir, fileNames, false, occurrences);
        
        assertEquals("File should be found in the nested directory", 
                1, occurrences.get("testFile.txt").intValue());
    }
}
