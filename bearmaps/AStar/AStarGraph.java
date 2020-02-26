package bearmaps.AStar;

import java.util.List;


public interface AStarGraph<Vertex> {
    List<WeightedEdge<Vertex>> neighbors(Vertex v);
    double estimatedDistanceToGoal(Vertex s, Vertex goal);
}
