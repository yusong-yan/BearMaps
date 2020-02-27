package bearmaps.KDtree_PQ;

import java.util.*;

public class MyTrieSet{

    Map<Character, Node> root;
    List<Character> rootList;

    private class Node{
        boolean isKey;
        Map<Character, Node> childs;
        List<Character> list;
        Node(){
            this.isKey=false;
            childs = new HashMap<>();
            list = new ArrayList<>();
        }
    }

    public MyTrieSet(){
        root = new HashMap<>();
        rootList = new ArrayList<>();
    }

    public void clear() {
        root = null;
        root = new HashMap<>();
    }

    public boolean contains(String key) {
        Map<Character,Node> current = root;
        for(int i = 0; i<key.length(); i++){
            char c = key.charAt(i);
            if(!current.containsKey(c)){
                return false;
            }
            if(i == key.length()-1){
                if(!current.get(c).isKey){
                    return false;
                }
            }else{
                current = current.get(c).childs;
            }
        }
        return true;
    }

    public void add(String key) {
        if(contains(key)){
            return;
        }
        Map<Character, Node> current = root;
        List<Character> currentList = rootList;
        for(int i =0; i<key.length(); i++){
            char c = key.charAt(i);
            if(!current.containsKey(c)){
                current.put(c, new Node());
                currentList.add(c);
            }
            if(i == key.length()-1){
                current.get(c).isKey=true;
            }
            currentList = current.get(c).list;
            current = current.get(c).childs;
        }
    }

    public List<String> collect(){
        List<String> result = new ArrayList<>();
        Map<Character, Node> current = root;
        List<Character> currentList = rootList;
        kwpHelper("", "",result , current, currentList);
        return result;
    }

    public List<String> keysWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        Map<Character, Node> current = root;
        List<Character> currentList = rootList;
        for(int i = 0; i<prefix.length(); i++){
            char c = prefix.charAt(i);
            if(!current.containsKey(c)){
                return result;
            }
            currentList = current.get(c).list;
            current = current.get(c).childs;
        }
        kwpHelper(prefix, "",result , current, currentList);
        return result;
    }

    private void kwpHelper(String prefix, String singleChar, List<String> result
            , Map<Character, Node> current, List<Character> currentList ) {
        if(current == null){
            return;
        }
        for(int i = 0; i<currentList.size(); i++){
            char c = currentList.get(i);
            if(current.get(c).isKey==true) {
                result.add(prefix+singleChar+ c);
            }
            kwpHelper(prefix, singleChar+c,result, current.get(c).childs, current.get(c).list);
        }
    }

    public String shortestPrefixOf(String key) {
        String currentKey = "";
        List<String> currentList;
        for(int i = 0; i<key.length(); i++){
            currentKey+=key.charAt(i);
            if(contains(currentKey)) {
                currentList = keysWithPrefix(currentKey);
                if (currentList.contains(key)) {
                    return currentKey;
                }
            }
        }
        return null;
    }

    public String longestPrefixOf(String key) {
        String result = "";
        String currentKey = "";
        List<String> currentList;
        for(int i = 0; i<key.length(); i++){
            currentKey+=key.charAt(i);
            if(contains(currentKey)) {
                currentList = keysWithPrefix(currentKey);
                if (currentList.contains(key)) {
                    if (currentKey.length() > result.length()) {
                        result = currentKey;
                    }
                }
            }
        }
        return result;
    }
}
