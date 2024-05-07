import java.util.Stack;

public class TowerOfHanoiDFS {

    static int moves = 0;

    // Recursive function to solve Tower of Hanoi using Depth-First Search
    static void towerOfHanoiDFS(int n, char source, char auxiliary, char destination) {
        if (n == 1) {
            System.out.println("Move disk 1 from rod " + source + " to rod " + destination);
            moves++;
            return;
        }
        towerOfHanoiDFS(n - 1, source, destination, auxiliary);
        System.out.println("Move disk " + n + " from rod " + source + " to rod " + destination);
        moves++;
        towerOfHanoiDFS(n - 1, auxiliary, source, destination);
    }

    // Iterative function to solve Tower of Hanoi using Depth-First Search
    static void towerOfHanoiDFSIterative(int n) {
        Stack<Integer> stack = new Stack<>();
        Stack<Character> source = new Stack<>();
        Stack<Character> auxiliary = new Stack<>();
        Stack<Character> destination = new Stack<>();
        char temp;
        int move = 0;

        // Initialize stacks
        for (int i = n; i >= 1; i--)
            stack.push(i);
        source.push('A');
        auxiliary.push('B');
        destination.push('C');

        while (!stack.isEmpty()) {
            if (move % 2 == 0) {
                if (n % 2 == 0) {
                    if (!source.isEmpty())
                        towerOfHanoiDFS(stack.pop(), source.pop(), auxiliary.pop(), destination.pop());
                    else
                        break;
                } else {
                    if (!source.isEmpty())
                        towerOfHanoiDFS(stack.pop(), source.pop(), destination.pop(), auxiliary.pop());
                    else
                        break;
                }
            } else {
                if (n % 2 == 0) {
                    if (!destination.isEmpty())
                        towerOfHanoiDFS(stack.pop(), destination.pop(), auxiliary.pop(), source.pop());
                    else
                        break;
                } else {
                    if (!destination.isEmpty())
                        towerOfHanoiDFS(stack.pop(), destination.pop(), source.pop(), auxiliary.pop());
                    else
                        break;
                }
            }
            move++;
        }
    }

    public static void main(String[] args) {
        int n = 3; // Number of disks
        System.out.println("Recursive solution:");
        towerOfHanoiDFS(n, 'A', 'B', 'C');
        System.out.println("Total moves: " + moves);
        System.out.println("\nIterative solution:");
        towerOfHanoiDFSIterative(n);
    }
}
