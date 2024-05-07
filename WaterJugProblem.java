import java.util.*;

public class WaterJugProblem {

    static class State {
        int jug1;
        int jug2;

        State(int jug1, int jug2) {
            this.jug1 = jug1;
            this.jug2 = jug2;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof State)) return false;
            State s = (State) obj;
            return s.jug1 == jug1 && s.jug2 == jug2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(jug1, jug2);
        }
    }

    static class Rule {
        String action;
        State newState;

        Rule(String action, State newState) {
            this.action = action;
            this.newState = newState;
        }
    }

    static State initialState = new State(0, 0);
    static State goalState = new State(0, 2); // Goal state: jug2 contains 2 liters of water

    static List<Rule> rules = Arrays.asList(
            new Rule("Fill Jug 1", new State(3, 0)),
            new Rule("Fill Jug 2", new State(0, 4)),
            new Rule("Empty Jug 1", new State(0, 0)),
            new Rule("Empty Jug 2", new State(0, 0)),
            new Rule("Pour from Jug 1 to Jug 2", new State(0, 3)),
            new Rule("Pour from Jug 2 to Jug 1", new State(3, 1))
    );

    // Apply a rule to a state and return the new state
    static State applyRule(State currentState, Rule rule) {
        return rule.newState;
    }

    // Check if a state is the goal state
    static boolean isGoalState(State state) {
        return state.equals(goalState);
    }

    // Perform a production system search
    static List<Rule> productionSystemSearch(State initialState) {
        State currentState = initialState;
        List<Rule> plan = new ArrayList<>();

        while (!isGoalState(currentState)) {
            boolean ruleApplied = false;
            for (Rule rule : rules) {
                State newState = applyRule(currentState, rule);
                if (!newState.equals(currentState)) {
                    plan.add(rule);
                    currentState = newState;
                    ruleApplied = true;
                    break; // Apply only one rule at a time
                }
            }
            if (!ruleApplied) {
                // No applicable rule found
                return null;
            }
        }

        return plan;
    }

    // Print the plan
    static void printPlan(List<Rule> plan) {
        if (plan == null) {
            System.out.println("No solution found.");
        } else {
            for (Rule rule : plan) {
                System.out.println(rule.action);
            }
        }
    }

    public static void main(String[] args) {
        List<Rule> plan = productionSystemSearch(initialState);
        printPlan(plan);
    }
}
