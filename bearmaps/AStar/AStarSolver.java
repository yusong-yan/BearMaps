package bearmaps.AStar;

import bearmaps.KDtree_PQ.ArrayHeapMinPQ;
import bearmaps.KDtree_PQ.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    Vertex end;
    int explored = 0;
    ExtrinsicMinPQ<Vertex> queue;
    SolverOutcome outcome;
    double totaltime;
    Map<Vertex, Double> map;
    Map<Vertex, Vertex> getParent;
    LinkedList<Vertex> solution;



    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout){
        this.end = end;
        Stopwatch sw = new Stopwatch();
        solution = new LinkedList<>();
        getParent = new HashMap<>();
        queue = new ArrayHeapMinPQ<>();
        map = new HashMap<>();
        map.put(start, 0.0);
        queue.add(start, 0.0);
        totaltime = 0.0;
        while(true){
            Vertex p = queue.removeSmallest();
            explored++;
            relaxYourNeighbors(p, input, end);
            totaltime = sw.elapsedTime();
            if(queue.size()==0){
                outcome = SolverOutcome.UNSOLVABLE;
                break;
            }
            if((queue.getSmallest().equals(end))){
                outcome = SolverOutcome.SOLVED;
                Vertex element = queue.getSmallest();
                while(element!=null){
                    solution.addFirst(element);
                    element = getParent.get(element);
                }
                break;
            }
            if(totaltime>timeout){
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
        }
    }

    private void relaxYourNeighbors(Vertex p, AStarGraph<Vertex> input, Vertex end) {
        for(WeightedEdge<Vertex> a:input.neighbors(p)){
            Vertex q = a.to();
            double w = a.weight();
            if (!map.containsKey(q) || map.get(p) + w < map.get(q)) {
                map.put(q, map.get(p) + w);
                getParent.put(q, p);
                if (queue.contains(q)) {
                    queue.changePriority(q, map.get(q) + input.estimatedDistanceToGoal(q, end));
                } else {
                    queue.add(q, map.get(q) + input.estimatedDistanceToGoal(q, end));
                }
            }
        }
    }

    public SolverOutcome outcome(){
        return outcome;
    }
    public List<Vertex> solution(){
        return solution;
    }

    public double solutionWeight(){
        if(outcome()==SolverOutcome.SOLVED){
            return map.get(end);
        }else{
            return 0;
        }
    }
    public int numStatesExplored(){
        return explored;
    }
    public double explorationTime(){
        return totaltime;
    }
}

