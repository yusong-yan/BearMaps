package bearmaps.KDtree_PQ;


import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    List<Node> arr;

    Map<T, Integer> set;

    public ArrayHeapMinPQ(){
        arr = new ArrayList<>();
        set = new HashMap<>();
    }

    private class Node{
        T item;
        double priority;
        Node(T item, double priority){
            this.item=item;
            this.priority=priority;
        }
    }

    public void popDown(Node node){
        int currentIndex = arr.indexOf(node);
        int leftIndex = leftChild(currentIndex);
        int rightIndex = rightChild(currentIndex);
        if(leftIndex>=size()){
            return;
        }else if(leftIndex==size()-1){
            if(arr.get(leftIndex).priority<arr.get(currentIndex).priority){
                swap(leftIndex, currentIndex);
                return;
            }
        }else{
            while(arr.get(currentIndex).priority>arr.get(leftIndex).priority
                    || arr.get(currentIndex).priority>arr.get(rightIndex).priority) {
                if (arr.get(leftIndex).priority < arr.get(rightIndex).priority) {
                    swap(leftIndex, currentIndex);
                    currentIndex = leftIndex;
                } else {
                    swap(rightIndex, currentIndex);
                    currentIndex = rightIndex;
                }
                leftIndex = leftChild(currentIndex);
                rightIndex = rightChild(currentIndex);
                if (leftIndex > size() - 1) {
                    leftIndex = rightIndex;
                }
                if (rightIndex > size() - 1) {
                    break;
                }
            }
        }
    }

    public void popUp(Node node){
        int currentIndex = arr.indexOf(node);
        int parentIndex = parent(currentIndex);
        while(arr.get(parentIndex).priority>arr.get(currentIndex).priority){
            swap(currentIndex, parentIndex );
            currentIndex = parentIndex;
            parentIndex = parent(currentIndex);
            if(parentIndex<0){
                break;
            }
        }
    }

    public int parent(int i){
        return (i-1)/2;
    }

    public int leftChild(int i){
        return (i*2);
    }

    public int rightChild(int i){
        return (i*2)+1;
    }

    public void swap(int first, int second){
        Node tem = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, tem);
        set.replace(arr.get(first).item, second);
        set.replace(arr.get(second).item, first);
    }

    @Override
    public void add(T item, double priority) {
        if(set.containsKey(item)){
            throw new IllegalArgumentException("Can't add one that already exit in the arr");
        }
        Node node = new Node(item, priority);
        arr.add(node);
        popUp(node);
        set.put(node.item, arr.indexOf(node));
    }

    @Override
    public boolean contains(T item) {
        return set.containsKey(item);
    }

    @Override
    public T getSmallest() {
        return arr.get(0).item;
    }

    @Override
    public T removeSmallest() {
        if(size()==0){
            throw new NoSuchElementException("No elements in the array");
        }
        T result = arr.get(0).item;
        swap(size()-1, 0);
        arr.remove(size()-1);
        if(size()>=1) {
            popDown(arr.get(0));
        }
        return result;
    }

    @Override
    public int size() {
        return arr.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if(!set.containsKey(item)){
            throw new NoSuchElementException("No such item");
        }else{
            Node node = arr.get(set.get(item));
            if(node.priority>priority){
                node.priority=priority;
                popUp(node);
            }else{
                node.priority=priority;
                popDown(node);
            }
        }
    }

    //two print method
    public void simplePrint(){
        for(int i = 0; i<arr.size(); i++){
            System.out.print(arr.get(i).item+" ");
        }
        System.out.println();
    }

    public void printSimpleHeapDrawing() {
        int depth = ((int) (Math.log(arr.size()) / Math.log(2)));
        int lastItem = 0;
        for (int j = 0; j < depth; j++) {
            System.out.print(" ");
        }

        for (int i = 0; i < arr.size(); i++) {
            System.out.print("|"+arr.get(i).item+"|"+" ");
            if (i == lastItem) {
                System.out.println();
                if(lastItem==0){
                    lastItem=2;
                }else {
                    lastItem += lastItem * 2;
                }
                depth--;
                for (int j = 0; j < depth; j++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }

}
