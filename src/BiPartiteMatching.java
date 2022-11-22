
// A Java program to find the Maximal Bipartite matching.
import java.util.*;
import java.lang.*;
import java.io.*;

class BiPartiteMatching {

    // A DFS based recursive function that returns true if
    // a matching for vertex u is possible
    boolean dfs(boolean bpGraph[][], int u,
            boolean seen[], int matchR[], int N) {

        for (int v = 0; v < N; v++) {
            // If applicant u is interested in job v and v is not visited
            if (bpGraph[u][v] && !seen[v]) {

                seen[v] = true;

                if (matchR[v] < 0 || dfs(bpGraph, matchR[v],
                        seen, matchR, N)) {
                    matchR[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    // Logs the maximum number of matching
    void maxBPM(boolean bpGraph[][], String[] arrayOfItems, int N) {
        // An array to keep track of the applicants assigned to jobs.
        int matchR[] = new int[N];
        int arr[] = new int[N];

        // Initially all jobs are available
        for (int i = 0; i < N; ++i) {
            matchR[i] = -1;
            arr[i] = 0;
        }

        // Count of jobs assigned to applicants
        int result = 0;
        for (int u = 0; u < N; u++) {
            // Mark all jobs as not seen for next applicant.
            boolean seen[] = new boolean[N];
            for (int i = 0; i < N; ++i)
                seen[i] = false;

            // Find if the applicant 'u' can get a job
            if (dfs(bpGraph, u, seen, matchR, N)) {
                System.out.print(arrayOfItems[u] + " / ");
                for (int k = 0; k < N; k++) {
                    if (bpGraph[u][k] && arr[k] == 0) {
                        System.out.print(arrayOfItems[k] + "\n");
                        arr[k] = 1;
                        break;
                    }
                }
                result++;
            }
        }
        System.out.print(result + " total matches");
    }

    public static void main(String[] args)
            throws java.lang.Exception {

        try (BufferedReader fileReader = new BufferedReader(new FileReader("src/program3data.txt"))) {
            String currentLine = "";

            // Reading Data File Line by Line
            int numberOfItems = Integer.parseInt(fileReader.readLine());

            // Creating Adjacendy Matrix
            boolean bpGraph[][] = new boolean[numberOfItems][numberOfItems];
            for (int i = 0; i < numberOfItems; i++) {
                boolean row[] = new boolean[numberOfItems];
                for (int j = 0; j < numberOfItems; j++) {
                    row[j] = false;
                }
                bpGraph[i] = row;
            }

            // Storing names of items in array
            String arrayOfItems[] = new String[numberOfItems];
            int lineIndex = 0;
            while (lineIndex < numberOfItems && (currentLine = fileReader.readLine()) != null) {
                arrayOfItems[lineIndex] = currentLine;
                lineIndex++;
            }

            // Reading number of Edges
            int E = Integer.parseInt(fileReader.readLine());
            lineIndex = 0;
            while (lineIndex < E && (currentLine = fileReader.readLine()) != null) {
                String[] numArr = currentLine.split(" ");
                int E1 = Integer.parseInt(numArr[0]);
                int E2 = Integer.parseInt(numArr[1]);
                bpGraph[E1 - 1][E2 - 1] = true;
                lineIndex++;
            }

            // Initialize a grapgh object
            BiPartiteMatching graph = new BiPartiteMatching();

            // Log the maximum number of matching
            graph.maxBPM(bpGraph, arrayOfItems, numberOfItems);
        }
    }
}
