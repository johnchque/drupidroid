package ch.drupalmountaincamp.android.model;

import java.util.ArrayList;

public class Node {

    private ArrayList<Title> title;
    private ArrayList<Type> type;

    public Node() {
    }

    public Node(ArrayList<Title> title, ArrayList<Type> type) {
        this.title = title;
        this.type = type;
    }

    public ArrayList<Title> getTitle() {
        return this.title;
    }

    public ArrayList<Type> getType() {
        return this.type;
    }

    public void setTitle(ArrayList<Title> title) {
        this.title = title;
    }

    public void setType(ArrayList<Type> type) {
        this.type = type;
    }
}
