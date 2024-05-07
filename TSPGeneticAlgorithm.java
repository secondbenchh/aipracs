import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSPGeneticAlgorithm {
    
    static class City {
        int x, y;

        public City(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Calculate distance between two cities
        public double distanceTo(City city) {
            int dx = x - city.x;
            int dy = y - city.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    static class Route {
        ArrayList<City> route;
        double fitness;

        public Route(ArrayList<City> route) {
            this.route = route;
            this.fitness = calculateFitness();
        }

        // Calculate total distance of the route
        private double calculateFitness() {
            double totalDistance = 0;
            for (int i = 0; i < route.size() - 1; i++) {
                totalDistance += route.get(i).distanceTo(route.get(i + 1));
            }
            totalDistance += route.get(route.size() - 1).distanceTo(route.get(0)); // Return to starting city
            return totalDistance;
        }
    }

    static class Population {
        ArrayList<Route> routes;

        public Population(int populationSize, ArrayList<City> cities) {
            routes = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                ArrayList<City> route = new ArrayList<>(cities);
                Collections.shuffle(route); // Randomly shuffle the route
                routes.add(new Route(route));
            }
        }
    }

    static Random random = new Random();

    // Tournament selection
    static Route selectParent(Population population) {
        int tournamentSize = 5;
        Population tournament = new Population(tournamentSize, population.routes);
        return Collections.min(tournament.routes, (a, b) -> Double.compare(a.fitness, b.fitness));
    }

    // Order crossover
    static ArrayList<City> crossover(Route parent1, Route parent2) {
        int startPos = random.nextInt(parent1.route.size());
        int endPos = random.nextInt(parent1.route.size());

        ArrayList<City> childRoute = new ArrayList<>();

        for (int i = 0; i < parent1.route.size(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                childRoute.add(parent1.route.get(i));
            } else if (startPos > endPos && !(i < startPos && i > endPos)) {
                childRoute.add(parent1.route.get(i));
            }
        }

        for (City city : parent2.route) {
            if (!childRoute.contains(city)) {
                childRoute.add(city);
            }
        }
        return childRoute;
    }

    // Swap mutation
    static void mutate(Route route) {
        int pos1 = random.nextInt(route.route.size());
        int pos2 = random.nextInt(route.route.size());
        Collections.swap(route.route, pos1, pos2);
        route.fitness = route.calculateFitness(); // Recalculate fitness after mutation
    }

    // Genetic algorithm
    static Route geneticAlgorithm(ArrayList<City> cities, int populationSize, int generations) {
        Population population = new Population(populationSize, cities);

        for (int gen = 0; gen < generations; gen++) {
            ArrayList<Route> newPopulation = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                Route parent1 = selectParent(population);
                Route parent2 = selectParent(population);
                ArrayList<City> childRoute = crossover(parent1, parent2);
                Route child = new Route(childRoute);
                if (random.nextDouble() < 0.1) { // Mutation rate
                    mutate(child);
                }
                newPopulation.add(child);
            }
            population.routes = newPopulation;
        }
        
        return Collections.min(population.routes, (a, b) -> Double.compare(a.fitness, b.fitness));
    }

    public static void main(String[] args) {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(0, 0));
        cities.add(new City(1, 2));
        cities.add(new City(3, 1));
        cities.add(new City(2, 3));
        cities.add(new City(4, 4));

        int populationSize = 50;
        int generations = 100;

        Route bestRoute = geneticAlgorithm(cities, populationSize, generations);
        System.out.println("Best Route: " + bestRoute.route);
        System.out.println("Shortest Distance: " + bestRoute.fitness);
    }
}
