package bearmaps.KDtree_PQ;


//import java.util.*;
//
//public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
//
//    List<Node> arr;
//
//    Map<T, Integer> set;
//
//    public ArrayHeapMinPQ(){
//        arr = new ArrayList<>();
//        set = new HashMap<>();
//    }
//
//    private class Node{
//        T item;
//        double priority;
//        Node(T item, double priority){
//            this.item=item;
//            this.priority=priority;
//        }
//    }
//
//    public void popDown(Node node){
//        int currentIndex = arr.indexOf(node);
//        int leftIndex = leftChild(currentIndex);
//        int rightIndex = rightChild(currentIndex);
//        if(leftIndex>=size()){
//            return;
//        }else if(leftIndex==size()-1){
//            if(arr.get(leftIndex).priority<arr.get(currentIndex).priority){
//                swap(leftIndex, currentIndex);
//                return;
//            }
//        }else{
//            while(arr.get(currentIndex).priority>arr.get(leftIndex).priority
//                    || arr.get(currentIndex).priority>arr.get(rightIndex).priority) {
//                if (arr.get(leftIndex).priority < arr.get(rightIndex).priority) {
//                    swap(leftIndex, currentIndex);
//                    currentIndex = leftIndex;
//                } else {
//                    swap(rightIndex, currentIndex);
//                    currentIndex = rightIndex;
//                }
//                leftIndex = leftChild(currentIndex);
//                rightIndex = rightChild(currentIndex);
//                if (leftIndex > size() - 1) {
//                    leftIndex = rightIndex;
//                }
//                if (rightIndex > size() - 1) {
//                    break;
//                }
//            }
//        }
//    }
//
//    public void popUp(Node node){
//        int currentIndex = arr.indexOf(node);
//        int parentIndex = parent(currentIndex);
//        while(arr.get(parentIndex).priority>arr.get(currentIndex).priority){
//            swap(currentIndex, parentIndex );
//            currentIndex = parentIndex;
//            parentIndex = parent(currentIndex);
//            if(parentIndex<0){
//                break;
//            }
//        }
//    }
//
//    public int parent(int i){
//        return (i-1)/2;
//    }
//
//    public int leftChild(int i){
//        return (i*2+1);
//    }
//
//    public int rightChild(int i){
//        return (i*2)+2;
//    }
//
//    public void swap(int first, int second){
//        Node tem = arr.get(first);
//        arr.set(first, arr.get(second));
//        arr.set(second, tem);
//        set.put(arr.get(first).item, second);
//        set.put(arr.get(second).item, first);
//    }
//
//    @Override
//    public void add(T item, double priority) {
//        if(set.containsKey(item)){
//            throw new IllegalArgumentException("Can't add one that already exit in the arr");
//        }
//        Node node = new Node(item, priority);
//        arr.add(node);
//        popUp(node);
//        set.put(node.item, arr.indexOf(node));
//    }
//
//    @Override
//    public boolean contains(T item) {
//        return set.containsKey(item);
//    }
//
//    @Override
//    public T getSmallest() {
//        return arr.get(0).item;
//    }
//
//    @Override
//    public T removeSmallest() {
//        if(size()==0){
//            throw new NoSuchElementException("No elements in the array");
//        }
//        T result = arr.get(0).item;
//        swap(size()-1, 0);
//        set.remove(arr.remove(size()-1).item);
//        if(size()>=1) {
//            popDown(arr.get(0));
//        }
//        return result;
//    }
//
//    @Override
//    public int size() {
//        return arr.size();
//    }
//
//    @Override
//    public void changePriority(T item, double priority) {
//        if(!set.containsKey(item)){
//            throw new NoSuchElementException("No such item");
//        }else{
//            Node node = arr.get(set.get(item));
//            if(node.priority>priority){
//                node.priority=priority;
//                popUp(node);
//            }else{
//                node.priority=priority;
//                popDown(node);
//            }
//        }
//    }
//
//    //two print method
//    public void simplePrint(){
//        for(int i = 0; i<arr.size(); i++){
//            System.out.print(arr.get(i).item+" ");
//        }
//        System.out.println();
//    }
//
//    public void printSimpleHeapDrawing() {
//        int depth = ((int) (Math.log(arr.size()) / Math.log(2)));
//        int lastItem = 0;
//        for (int j = 0; j < depth; j++) {
//            System.out.print(" ");
//        }
//
//        for (int i = 0; i < arr.size(); i++) {
//            System.out.print("|"+arr.get(i).item+"|"+" ");
//            if (i == lastItem) {
//                System.out.println();
//                if(lastItem==0){
//                    lastItem=2;
//                }else {
//                    lastItem += lastItem * 2;
//                }
//                depth--;
//                for (int j = 0; j < depth; j++) {
//                    System.out.print(" ");
//                }
//            }
//        }
//        System.out.println();
//    }
//
//}

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    List<Node> arr;

    Map<T, Integer>set;

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

    private void popDown(int k){
        while(leftChild(k)<size()){
            int target = leftChild(k);
            if(rightChild(k)<size()&&greater(leftChild(k), rightChild(k))){
                target=rightChild(k);
            }
            if(!greater(k,target)){break;}
            swap(k, target);
            k = target;
        }
    }

    private void popUp(int k){
        while(k>0&&greater(parent(k), k)){
            swap(parent(k), k);
            k=parent(k);
        }
    }

    private boolean greater(int a, int b){
        return arr.get(a).priority>arr.get(b).priority;
    }

    private boolean less(int i, int j) {
        return arr.get(i).priority < arr.get(j).priority;
    }

    private int parent(int i){
        return (i-1)/2;
    }

    private int leftChild(int i){
        return (i*2+1);
    }

    private int rightChild(int i){
        return (i*2+1)+1;
    }

    private void swap(int first, int second){
        Node tem = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, tem);
        set.put(arr.get(second).item,second);
        set.put(arr.get(first).item, first);
    }

    @Override
    public void add(T item, double priority) {
        if(set.containsKey(item)){
            throw new IllegalArgumentException("Can't add one that already exit in the arr");
        }
        Node node = new Node(item, priority);
        arr.add(node);
        set.put(item, size()-1);
        popUp(arr.size()-1);
    }

    @Override
    public boolean contains(T item) {
        return set.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if(arr.isEmpty()){
            throw new NoSuchElementException();
        }
        return arr.get(0).item;
    }

    @Override
    public T removeSmallest() {
        if(arr.size()==0){
            throw new NoSuchElementException("No elements in the array");
        }
        T result = arr.get(0).item;
        swap(arr.size()-1, 0);
        arr.remove(size()-1);
        popDown(0);
        set.remove(result);
        return result;
    }

    @Override
    public int size() {
        return arr.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int i = set.get(item);
        double oldPriority = arr.get(i).priority;
        arr.get(i).priority = priority;
        if (oldPriority < priority) {
            popDown(i);
        } else {
            popUp(i);
        }
    }

    //two print method
    private void simplePrint(){
        for(int i = 0; i<arr.size(); i++){
            System.out.print(arr.get(i).item+" ");
        }
        System.out.println();
    }

    private void printSimpleHeapDrawing() {
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
