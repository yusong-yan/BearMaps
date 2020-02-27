package bearmaps.Map;

import bearmaps.AStar.streetmap.Node;
import bearmaps.AStar.streetmap.StreetMapGraph;
import bearmaps.KDtree_PQ.KDTree;
import bearmaps.KDtree_PQ.MyTrieSet;
import bearmaps.KDtree_PQ.Point;
import bearmaps.KDtree_PQ.PointSet;
//import bearmaps.proj2ab.WeirdPointSet;

import java.util.*;


public class AugmentedStreetMapGraph extends StreetMapGraph {
    List<Point> Points;
    List<Node> nodes;
    Map<Point, Node> pointToNode;
    Map<String, List<Node>> nameToNode;
    Map<Long, Node>longToNode;;
    MyTrieSet tries;
    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        nodes = this.getNodes();
        Points = new ArrayList<>();
        pointToNode = new HashMap<>();
        nameToNode=new HashMap<>();
        longToNode = new HashMap<>();
        tries = new MyTrieSet();
        for(int i = 0; i<nodes.size(); i++){
            Point a = new Point(nodes.get(i).lon(), nodes.get(i).lat());
            Points.add(a);
            pointToNode.put(a, nodes.get(i));
            longToNode.put(nodes.get(i).id(), nodes.get(i));
            if(nodes.get(i).name()!=null) {
                //System.out.println(nodes.get(i).name());
                String currentName = cleanString(nodes.get(i).name());
                tries.add(currentName);
                if (!nameToNode.containsKey(currentName)) {
                    nameToNode.put(currentName, new ArrayList<>());
                }
                nameToNode.get(currentName).add(nodes.get(i));
            }
        }

    }



    public long closest(double lon, double lat) {
        long result = 0;
        PointSet a = new KDTree(Points);
        List<Point> removedPoints = new ArrayList<>();
        Point nearest = a.nearest(lon,lat);
        Node nearestNode = pointToNode.get(nearest);
        result = nearestNode.id();
//        while(true){
//            Point nearest = a.nearest(lon,lat);
//            Node nearestNode = pointToNode.get(nearest);
//            if(neighbors(nearestNode.id()).size()==0){
//                removedPoints.add(nearest);
//                if(Points.size()>0) {
//                    Points.remove(Points.indexOf(nearest));
//                    result = nearestNode.id();
//                    break;
//                }
//            }else{
//                result = nearestNode.id();
//                break;
//            }
//        }
//        for(int i = 0; i<removedPoints.size(); i++){
//            Points.add(removedPoints.get(i));
//        }
        return result;
    }



    public List<String> getLocationsByPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        List<String> triesResult = tries.keysWithPrefix(cleanedName(prefix));
        for(int i = 0; i<triesResult.size(); i++){
            if(nameToNode.containsKey(triesResult.get(i))){
                for(Node element : nameToNode.get(triesResult.get(i))){
                    result.add(element.name());
                }
            }
        }
        return result;
    }

    private String cleanedName(String currentName) {
        String newName="";
        for(int j = 0; j<currentName.length(); j++){
            if(Character.isAlphabetic(currentName.charAt(j))){
                newName+=currentName.charAt(j);
            }
        }
        return newName;
    }

    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> result = new ArrayList<>();
        String currentName = cleanString(locationName);
        if(nameToNode.containsKey(currentName)){
            for(Node n:nameToNode.get(currentName)){
                Map<String, Object> map = new HashMap<>();
                map.put("lon", n.lon());
                map.put("lat", n.lat());
                map.put("name", n.name());
                map.put("id", n.id());
                result.add(map);
            }
        }
        return result;
    }


    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
