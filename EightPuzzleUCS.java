import java.util.*;

public class EightPuzzleUCS {

    static final int[][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up

    static class Node {
        int[][] state;
        int cost;
        String move;
        Node parent;

        public Node(int[][] state, int cost, String move, Node parent) {
            this.state = state;
            this.cost = cost;
            this.move = move;
            this.parent = parent;
        }
    }

    // Check if the given coordinates are valid
    static boolean isValid(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    // Generate child nodes by moving the empty tile
    static ArrayList<Node> generateChildren(Node node) {
        ArrayList<Node> children = new ArrayList<>();
        int emptyRow = 0, emptyCol = 0;

        // Find the position of the empty tile
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (node.state[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }

        // Generate child nodes by moving the empty tile
        for (int[] dir : dirs) {
            int newRow = emptyRow + dir[0];
            int newCol = emptyCol + dir[1];
            if (isValid(newRow, newCol)) {
                int[][] newState = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        newState[i][j] = node.state[i][j];
                    }
                }
                // Swap empty tile with neighboring tile
                int temp = newState[newRow][newCol];
                newState[newRow][newCol] = newState[emptyRow][emptyCol];
                newState[emptyRow][emptyCol] = temp;
                children.add(new Node(newState, node.cost + 1, dirToString(dir), node));
            }
        }

        return children;
    }

    // Convert direction to string
    static String dirToString(int[] dir) {
        if (Arrays.equals(dir, new int[]{0, 1})) return "Right";
        if (Arrays.equals(dir, new int[]{1, 0})) return "Down";
        if (Arrays.equals(dir, new int[]{0, -1})) return "Left";
        if (Arrays.equals(dir, new int[]{-1, 0})) return "Up";
        return "";
    }

    // Check if the given state is the goal state
    static boolean isGoalState(int[][] state) {
        return Arrays.deepEquals(state, goalState);
    }

    // Uniform Cost Search (UCS) algorithm
    static ArrayList<String> uniformCostSearch(int[][] initialState) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        HashSet<String> closedSet = new HashSet<>();

        Node startNode = new Node(initialState, 0, "", null);
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (isGoalState(current.state)) {
                return reconstructPath(current);
            }

            closedSet.add(Arrays.deepToString(current.state));

            for (Node child : generateChildren(current)) {
                String childStateString = Arrays.deepToString(child.state);
                if (!closedSet.contains(childStateString)) {
                    openSet.add(child);
                }
            }
        }

        return null; // No solution found
    }

    // Reconstruct path from the goal node to the start node
    static ArrayList<String> reconstructPath(Node goalNode) {
        ArrayList<String> path = new ArrayList<>();
        Node current = goalNode;
        while (current != null) {
            path.add(current.move);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    // Print the state of the puzzle
    static void printPuzzleState(int[][] state) {
        for (int[] row : state) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] initialState = {{1, 2, 3}, {0, 4, 6}, {7, 5, 8}}; // Initial state of the puzzle

        System.out.println("Initial state:");
        printPuzzleState(initialState);
        System.out.println();

        ArrayList<String> solution = uniformCostSearch(initialState);

        if (solution != null) {
            System.out.println("Solution moves:");
            for (String move : solution) {
                System.out.println(move);
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
