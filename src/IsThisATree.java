import java.util.HashSet;
import java.util.Set;

/**
 * HackerRank Test Question 'Is This A Tree?'
 * Return the lexicographically smallest s-expression of the tree input represented by node pairs.
 * Report any errors if input is not a tree.
 * If there are multiple errors, return the lexicographically smallest error code.
 * Error   Type
 * E1      More than 2 children
 * E2      Duplicate edges
 * E3      Cyclic
 * E4      Multiple roots
 * E5      Any other error
 *
 * @author Trisha Funtanilla
 */
public class IsThisATree {

    public static void main(String[] args) {
        String input1 = "(B,D) (D,E) (A,B) (C,F) (E,G) (A,C)";
        String output1 = sExpression(input1);
        String expectedOutput1 = "(A(B(D(E(G))))(C(F)))";
        System.out.println("Output: " + output1);
        System.out.println("Expected Output: " + expectedOutput1);
        System.out.println("Same? " + output1.equals(expectedOutput1));

        System.out.println();

        String input2 = "(A,B) (A,C) (B,D) (D,C)";
        String output2 = sExpression(input2);
        String expectedOutput2 = "E3";
        System.out.println("Output: " + output2);
        System.out.println("Expected Output: " + expectedOutput2);
        System.out.println("Same? " + output2.equals(expectedOutput2));
    }

    private static String sExpression(String nodes) {
        try {
            boolean[][] graph = new boolean[26][26]; // adjacency matrix representation of the tree
            Set<Character> setOfNodes = new HashSet<>(); // set of all node names in the tree

            boolean E2 = false;
            // Parse nodes, loop through each parent-child pair
            for (int i = 1; i < nodes.length(); i += 6) {
                int parent = nodes.charAt(i) - 'A';
                int child = nodes.charAt(i + 2) - 'A'; // i + 2 skips the comma
                // Check for duplicate edges
                if (graph[parent][child]) {
                    E2 = true;
                }
                graph[parent][child] = true;
                setOfNodes.add(nodes.charAt(i));
                setOfNodes.add(nodes.charAt(i + 2));
            }

            for (char node : setOfNodes) {
                int childCount = 0;
                for (int i = 0; i < 26; i++) {
                    // Check if any node has more than two children
                    if (graph[node - 'A'][i]) {
                        childCount++;
                    }
                }
                if (childCount > 2) {
                    return "E1"; //
                }
            }

            if (E2) {
                return "E2";
            }

            int rootCount = 0;
            char root = Character.MIN_VALUE;
            for (char node : setOfNodes) {
                for (int i = 0; i < 26; i++) {
                    if (graph[i][node - 'A']) { // parent exists
                        break;
                    }
                    if (i == 25) { // parent does not exist, meaning it is a root
                        rootCount++;
                        root = node;
                        // Check for any cycles
                        boolean[] visited = new boolean[26];
                        if (isCycle(node, visited, graph)) {
                            return "E3";
                        }
                    }
                }
            }

            if (rootCount == 0) { // if there is no root, there must be a cycle
                return "E3";
            }
            if (rootCount > 1) { // more than one root
                return "E4";
            }

            return generateOutput(root, graph);
        } catch(Exception e) {
            // Any other input errors will cause some type exception (ex. ArrayIndexOutOfBoundsException)
            return "E5";
        }
    }

    private static String generateOutput(char root, boolean[][] graph) {
        // Recursive DFS to build the output s-expression
        String left = "";
        String right = "";
        for (int i = 0; i < 26; i++) {
            if (graph[root - 'A'][i]) {
                left = generateOutput((char) (i + 'A'), graph);
                for (int j = i + 1; j < 26; j++) {
                    if (graph[root - 'A'][j]) {
                        right = generateOutput((char) (j + 'A'), graph);
                        break;
                    }
                }
                break;
            }
        }
        return "(" + root + left + right + ")";
    }

    private static boolean isCycle(char node, boolean[] visited, boolean[][] graph) {
        // Recursive DFS to check for cycle
        // If node has been visited, there must be a cycle
        if (visited[node - 'A']) {
            return true;
        }
        visited[node - 'A'] = true;
        for (int i = 0; i < 26; i++) {
            // Check the children of this node
            if (graph[node - 'A'][i]) {
                if (isCycle((char) (i + 'A'), visited, graph)) {
                    return true;
                }
            }
        }
        return false;
    }
}
