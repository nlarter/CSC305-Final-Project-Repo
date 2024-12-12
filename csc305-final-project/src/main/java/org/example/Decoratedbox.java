package org.example;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Decoratedbox extends Box implements Serializable {

    private static final long serialVersionUID = 1L;
    private Box box;

    public Decoratedbox(Box box){
        super(box.position);
        this.box = box;
    }

    @Override
    public void setName(String name){
        box.setName(name);
    }

    @Override
    public String getName(){
        return box.getName();
    }

    @Override
    public LinkedList<String> getCode(){
        return box.getCode();
    }

    @Override
    public ArrayList<Box> getConnections(){
        return box.getConnections();
    }

    public void draw(Graphics g){
        box.draw(g);
    }
}
