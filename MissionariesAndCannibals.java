import java.util.*;

public class MissionariesAndCannibals {

    static class State {
        int missionariesLeft;
        int cannibalsLeft;
        int missionariesRight;
        int cannibalsRight;
        boolean boatLeft;

        State(int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, boolean boatLeft) {
            this.missionariesLeft = missionariesLeft;
            this.cannibalsLeft = cannibalsLeft;
            this.missionariesRight = missionariesRight;
            this.cannibalsRight = cannibalsRight;
            this.boatLeft = boatLeft;
        }

        boolean isValid() {
            if (missionariesLeft < 0 || missionariesRight < 0 || cannibalsLeft < 0 || cannibalsRight < 0) {
                return false;
            }
            return (missionariesLeft == 0 || missionariesLeft >= cannibalsLeft) &&
                    (missionariesRight == 0 || missionariesRight >= cannibalsRight);
        }

        boolean isGoal() {
            return missionariesLeft == 0 && cannibalsLeft == 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof State)) return false;
            State s = (State) obj;
            return s.missionariesLeft == missionariesLeft &&
                    s.cannibalsLeft == cannibalsLeft &&
                    s.missionariesRight == missionariesRight &&
                    s.cannibalsRight == cannibalsRight &&
                    s.boatLeft == boatLeft;
        }

        @Override
        public int hashCode() {
            return Objects.hash(missionariesLeft, cannibalsLeft, missionariesRight, cannibalsRight, boatLeft);
        }
    }

    static class Node {
        State state;
        Node parent;
        int cost;
        int heuristic;

        Node(State state, Node parent, int cost, int heuristic) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }
    }

    // Heuristic function: returns the number of missionaries and cannibals on the left bank
    static int heuristic(State state) {
        return state.missionariesLeft + state.cannibalsLeft;
    }

    // A* search algorithm
    static List<State> aStarSearch(State initialState) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost + a.heuristic));
        Set<State> closedSet = new HashSet<>();
        Map<State, Integer> costs = new HashMap<>();
        Map<State, Node> parents = new HashMap<>();

        Node startNode = new Node(initialState, null, 0, heuristic(initialState));
        openSet.add(startNode);
        costs.put(initialState, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.state.isGoal()) {
                List<State> path = new ArrayList<>();
                Node node = current;
                while (node != null) {
                    path.add(node.state);
                    node = node.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(current.state);

            for (State neighbor : generateNeighbors(current.state)) {
                int tentativeCost = costs.get(current.state) + 1;
                if (!closedSet.contains(neighbor) || tentativeCost < costs.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    costs.put(neighbor, tentativeCost);
                    Node neighborNode = new Node(neighbor, current, tentativeCost, heuristic(neighbor));
                    openSet.add(neighborNode);
                    parents.put(neighbor, current);
                }
            }
        }

        return null; // No solution found
    }

    // Generate valid neighbors for a given state
    static List<State> generateNeighbors(State state) {
        List<State> neighbors = new ArrayList<>();

        if (state.boatLeft) {
            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2; c++) {
                    if (m + c >= 1 && m + c <= 2) {
                        State neighbor = new State(
                                state.missionariesLeft - m,
                                state.cannibalsLeft - c,
                                state.missionariesRight + m,
                                state.cannibalsRight + c,
                                false
                        );
                        if (neighbor.isValid()) {
                            neighbors.add(neighbor);
                        }
                    }
                }
            }
        } else {
            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2; c++) {
                    if (m + c >= 1 && m + c <= 2) {
                        State neighbor = new State(
                                state.missionariesLeft + m,
                                state.cannibalsLeft + c,
                                state.missionariesRight - m,
                                state.cannibalsRight - c,
                                true
                        );
                        if (neighbor.isValid()) {
                            neighbors.add(neighbor);
                        }
                    }
                }
            }
        }

        return neighbors;
    }

    // Print the path
    static void printPath(List<State> path) {
        for (State state : path) {
            System.out.println(state.missionariesLeft + "M " + state.cannibalsLeft + "C | " +
                    (state.boatLeft ? " BOAT " : "        ") +
                    state.missionariesRight + "M " + state.cannibalsRight + "C");
        }
    }

    public static void main(String[] args) {
        State initialState = new State(3, 3, 0, 0, true); // Initial state: 3 missionaries and 3 cannibals on the left bank

        List<State> path = aStarSearch(initialState);

        if (path != null) {
            printPath(path);
        } else {
            System.out.println("No solution found.");
        }
    }
}
