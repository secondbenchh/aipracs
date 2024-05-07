import java.util.ArrayDeque;
import java.util.Queue;

public class TowerOfHanoiBFS {

    static class State {
        int n;
        char source, auxiliary, destination;

        public State(int n, char source, char auxiliary, char destination) {
            this.n = n;
            this.source = source;
            this.auxiliary = auxiliary;
            this.destination = destination;
        }
    }

    // Function to perform BFS for Tower of Hanoi
    static void towerOfHanoiBFS(int n) {
        Queue<State> queue = new ArrayDeque<>();
        queue.offer(new State(n, 'A', 'B', 'C'));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.n == 1) {
                System.out.println("Move disk 1 from rod " + currentState.source + " to rod " + currentState.destination);
            } else {
                queue.offer(new State(currentState.n - 1, currentState.source, currentState.destination, currentState.auxiliary));
                queue.offer(new State(1, currentState.source, currentState.auxiliary, currentState.destination));
                queue.offer(new State(currentState.n - 1, currentState.auxiliary, currentState.source, currentState.destination));
            }
        }
    }

    public static void main(String[] args) {
        int n = 3; // Number of disks
        System.out.println("BFS solution:");
        towerOfHanoiBFS(n);
    }
}
