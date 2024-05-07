import java.util.*;

public class MissionariesAndCannibalsUCS {

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

    // Check if the given state is the goal state
    static boolean isGoalState(State state) {
        return state.missionariesLeft == 0 && state.cannibalsLeft == 0;
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

    // Perform Uniform Cost Search (UCS)
    static List<State> uniformCostSearch(State initialState) {
        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(o -> 0)); // Uniform cost search, no specific ordering
        Map<State, State> parentMap = new HashMap<>();
        Set<State> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (isGoalState(current)) {
                // Reconstruct and return the path
                List<State> path = new ArrayList<>();
                State node = current;
                while (node != null) {
                    path.add(node);
                    node = parentMap.get(node);
                }
                Collections.reverse(path);
                return path;
            }

            for (State neighbor : generateNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        return null; // No solution found
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

        List<State> path = uniformCostSearch(initialState);

        if (path != null) {
            printPath(path);
        } else {
            System.out.println("No solution found.");
        }
    }
}
