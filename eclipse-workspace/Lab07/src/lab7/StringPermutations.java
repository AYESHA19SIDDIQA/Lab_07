package lab7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringPermutations {

    public static void main(String[] args) {
        if (args.length < 2 || args[0] == null || args[0].isEmpty()) {
            System.out.println("Usage: java StringPermutations <string> <includeDuplicates(true/false)>");
            return;
        }

        String input = args[0];
        boolean includeDuplicates = Boolean.parseBoolean(args[1]);

        try {
            List<String> recursivePermutations = generatePermutations(input, includeDuplicates);
            List<String> iterativePermutations = generatePermutationsIteratively(input);

            System.out.println("Recursive Permutations of " + input + ":");
            for (String permutation : recursivePermutations) {
                System.out.println(permutation);
            }

            System.out.println("\nIterative Permutations of " + input + ":");
            for (String permutation : iterativePermutations) {
                System.out.println(permutation);
            }

            System.out.println("\nRecursive approach generated " + recursivePermutations.size() + " permutations.");
            System.out.println("Iterative approach generated " + iterativePermutations.size() + " permutations.");

        } catch (Exception e) {
            System.out.println("An error occurred while generating permutations: " + e.getMessage());
        }
    }

    // Recursive Permutation Generation
    public static List<String> generatePermutations(String str, boolean includeDuplicates) {
        if (str == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        if (str.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        permute(str.toCharArray(), 0, result, includeDuplicates);
        return result;
    }

    private static void permute(char[] chars, int index, List<String> result, boolean includeDuplicates) {
        if (index == chars.length - 1) {
            result.add(new String(chars));
            return;
        }

        Set<Character> seen = new HashSet<>();
        for (int i = index; i < chars.length; i++) {
            if (!includeDuplicates && seen.contains(chars[i])) {
                continue; // Skip duplicates if not allowed
            }
            seen.add(chars[i]);
            swap(chars, index, i);
            permute(chars, index + 1, result, includeDuplicates);
            swap(chars, index, i); // Backtrack
        }
    }

    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    // Non-Recursive (Iterative) Permutation Generation
    public static List<String> generatePermutationsIteratively(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        if (str.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        List<Character> chars = new ArrayList<>();
        for (char c : str.toCharArray()) {
            chars.add(c);
        }

        Collections.sort(chars);  // Start with lexicographically smallest permutation
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (char c : chars) {
                sb.append(c);
            }
            result.add(sb.toString());

            int i = chars.size() - 2;
            while (i >= 0 && chars.get(i) >= chars.get(i + 1)) {
                i--;
            }

            if (i == -1) {
                break;
            }

            int j = chars.size() - 1;
            while (chars.get(j) <= chars.get(i)) {
                j--;
            }

            // Swap characters at i and j
            Collections.swap(chars, i, j);

            // Reverse from i+1 to end to get the next lexicographically smallest permutation
            Collections.reverse(chars.subList(i + 1, chars.size()));
        }

        return result;
    }
}
