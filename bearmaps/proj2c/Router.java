package bearmaps.proj2c;

import bearmaps.AStar.WeightedEdge;
import bearmaps.AStar.WeirdSolver;
import bearmaps.AStar.streetmap.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Router {


    public static List<Long> shortestPath(AugmentedStreetMapGraph g, double stlon, double stlat,
                                          double destlon, double destlat) {
        long src = g.closest(stlon, stlat);
        long dest = g.closest(destlon, destlat);
        return new WeirdSolver<>(g, src, dest, 20).solution();
    }


    public static List<NavigationDirection> routeDirections(AugmentedStreetMapGraph g, List<Long> route) {
        /* fill in for part IV */
        List<NavigationDirection> result = new ArrayList<>();
        List<WeightedEdge<Long>> allways = getWays(g,route);
        double Dis = 0;
        NavigationDirection preNav = new NavigationDirection();
        preNav.direction=0;
        for(int i =1; i<allways.size();i++){
            WeightedEdge<Long> prev = allways.get(i-1);
            WeightedEdge<Long> curr = allways.get(i);
            Dis+=prev.weight();
            Long prevVertex = prev.from();
            Long currVertex = prev.to();
            Long nextVertex = curr.to();
            Node first = g.longToNode.get(prevVertex);
            Node second = g.longToNode.get(currVertex);
            Node third = g.longToNode.get(nextVertex);
            if(!prev.getName().equals(curr.getName())) {
                NavigationDirection nav = new NavigationDirection();
                if(prev.getName().length()<2 && Dis<0.010){
                    System.out.println("true");
                    preNav.way="Crossroad";
                }else {
                    preNav.way = prev.getName();
                }
                preNav.distance = Dis;
                Dis = 0;
                result.add(preNav);
                double firstBearing = NavigationDirection.bearing(first.lon(), second.lon(),
                        first.lat(), second.lat());
                double secondBearing = NavigationDirection.bearing(second.lon(), third.lon(),
                        second.lat(), third.lat());
                int currentDirection = NavigationDirection.getDirection(firstBearing, secondBearing);
                nav.direction = currentDirection;
                if(curr.getName().length()<2){
                    nav.way="crossRodes";
                }else {
                    nav.way = curr.getName();
                }
                preNav = nav;
            }
        }
        preNav.distance = Dis;
        result.add(preNav);
        return result;
    }

    private static List<WeightedEdge<Long>> getWays(AugmentedStreetMapGraph g, List<Long> route) {
        List<WeightedEdge<Long>> result = new ArrayList<>();
        for(int i = 1; i<route.size();i++){
           result.add(getEdge(g, route.get(i-1), route.get(i)));
        }
        return result;
    }

    private static WeightedEdge<Long> getEdge(AugmentedStreetMapGraph g, Long aLong, Long aLong1) {
        for(WeightedEdge<Long> edge: g.neighbors(aLong)){
            if(edge.to().equals(aLong1)){
                return edge;
            }
        }
        return null;
    }


    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;


        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        /** Checks that a value is between the given ranges.*/
        private static boolean numInRange(double value, double from, double to) {
            return value >= from && value <= to;
        }

        private static int getDirection(double prevBearing, double currBearing) {
            double absDiff = Math.abs(currBearing - prevBearing);
            if (numInRange(absDiff, 0.0, 15.0)) {
                return NavigationDirection.STRAIGHT;

            }
            if ((currBearing > prevBearing && absDiff < 180.0)
                    || (currBearing < prevBearing && absDiff > 180.0)) {
                // we're going right
                if (numInRange(absDiff, 15.0, 30.0) || absDiff > 330.0) {
                    // bearmaps.proj2c.example of high abs diff is prev = 355 and curr = 2
                    return NavigationDirection.SLIGHT_RIGHT;
                } else if (numInRange(absDiff, 30.0, 100.0) || absDiff > 260.0) {
                    return NavigationDirection.RIGHT;
                } else {
                    return NavigationDirection.SHARP_RIGHT;
                }
            } else {
                // we're going left
                if (numInRange(absDiff, 15.0, 30.0) || absDiff > 330.0) {
                    return NavigationDirection.SLIGHT_LEFT;
                } else if (numInRange(absDiff, 30.0, 100.0) || absDiff > 260.0) {
                    return NavigationDirection.LEFT;
                } else {
                    return NavigationDirection.SHARP_LEFT;
                }
            }
        }


        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }

        public static double bearing(double lonV, double lonW, double latV, double latW) {
            double phi1 = Math.toRadians(latV);
            double phi2 = Math.toRadians(latW);
            double lambda1 = Math.toRadians(lonV);
            double lambda2 = Math.toRadians(lonW);

            double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
            double x = Math.cos(phi1) * Math.sin(phi2);
            x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
            return Math.toDegrees(Math.atan2(y, x));
        }
    }
}
