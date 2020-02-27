package bearmaps.KDtree_PQ;

import java.util.ArrayList;
import java.util.List;

import static bearmaps.KDtree_PQ.Point.distance;


public class KDTree implements PointSet{

    public KDTree(List<Point> points){
        for(Point s : points){
            insert(s);
        }
    }

    private Node root;

    public enum Direction {
        Horizontal, Vertical
    }

    private class Min{
        double min;
        Point point;
    }

    private class Node{
        Direction Direction;
        Point point;
        double x;
        double y;
        Node left;
        Node right;
        Node parent;
        Node(Point point, Direction Direction){
            this.point = point;
            x = point.getX();
            y = point.getY();
            left = null;
            right = null;
            parent = null;
            this.Direction = Direction;
        }
    }

    public void insert(Point point){
        if(root == null){
            root = new Node(point, Direction.Vertical);
        }else{
            insertHelper(point, root);
        }
    }
    private void insertHelper(Point point, Node node) {
        if(node == null){
            return;
        }else{
            if(node.Direction.equals(Direction.Vertical)){
                if(Double.compare(node.x, point.getX())>0){
                    //goleft
                    if(node.left==null){
                        node.left=new Node(point, Direction.Horizontal);
                        node.left.parent = node;
                    }else{
                        insertHelper(point, node.left);
                    }
                }else{
                    //goright
                    if(node.right==null){
                        node.right=new Node(point, Direction.Horizontal);
                        node.right.parent = node;
                    }else{
                        insertHelper(point, node.right);
                    }
                }
            }else{
                if(Double.compare(node.y, point.getY())>0){
                    //goleft
                    if(node.left==null){
                        node.left=new Node(point, Direction.Vertical);
                        node.left.parent = node;
                    }else{
                        insertHelper(point, node.left);
                    }
                }else{
                    //goright
                    if(node.right==null){
                        node.right=new Node(point, Direction.Vertical);
                        node.right.parent = node;
                    }else{
                        insertHelper(point, node.right);
                    }
                }
            }
        }
    }

    public Point nearest(double x, double y){
        Point input = new Point(x,y);
        Min myMin = new Min();
        myMin.min=distance(input, root.point);
        myMin.point=root.point;
        myMin =  nearestHelper(root,input, myMin);
        return myMin.point;
    }
    private Min nearestHelper(Node node, Point point, Min myMin) {
        if(node == null){
            return myMin;
        }else{
            //reset myMin
            double newDistance = distance(point, node.point);
            if(Double.compare(newDistance, myMin.min)<0){
                myMin.min = newDistance;
                myMin.point=node.point;
            }

            //Chose first direction
            boolean anotherWayIsLeft = false;
            if(node.Direction.equals(Direction.Vertical)){
                if(Double.compare(node.x, point.getX())>0){
                    myMin=nearestHelper(node.left, point, myMin);
                }else{
                    myMin=nearestHelper(node.right, point, myMin);
                    anotherWayIsLeft=true;
                }
            }else{
                if(Double.compare(node.y, point.getY())>0){
                    myMin=nearestHelper(node.left, point, myMin);
                }else{
                    myMin=nearestHelper(node.right, point, myMin);
                    anotherWayIsLeft=true;
                }
            }

            //When you go back, determine if it is necessary to check another side of tree
            if(IfAPossiableShorterWay(node, point, myMin)){
                if(anotherWayIsLeft){
                    myMin=nearestHelper(node.left, point, myMin);
                }else{
                    myMin=nearestHelper(node.right, point, myMin);
                }
            }
            return myMin;
        }
    }
    boolean IfAPossiableShorterWay(Node node, Point point, Min myMin){
        if(node.Direction.equals(Direction.Vertical)){
            double possiableDis = Math.pow(node.x-point.getX(),2);
            if(possiableDis<myMin.min){
                return true;
            }
        }else{
            double possiableDis = Math.pow(node.y-point.getY(),2);
            if(possiableDis<myMin.min){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] arg){
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point (3.1, 4.0);
        List<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        KDTree nn = new KDTree(points);
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()+" "+ret.getY());

    }
}
