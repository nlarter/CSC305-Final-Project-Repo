package org.example;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Box implements Serializable {

    private static final long serialVersionUID = 1L;
    protected Point position;
    private String name;
    private final Strategy strategy = new StrategyDrawLine();
    private final int BOX_DIMENSIONS = 100;
    private ArrayList<Box> connections = new ArrayList<>();
    private LinkedList<String> code = new LinkedList<>();

    public Box(Point position){
        this.position = position;
    }

    public void draw(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(position.x, position.y, BOX_DIMENSIONS, BOX_DIMENSIONS);
        if (name != null && !name.isEmpty()){
            g.setColor(Color.RED);
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int tWidth = metrics.stringWidth(name);
            int tHeight = metrics.getHeight();
            int tX = position.x + (BOX_DIMENSIONS - tWidth) / 2;
            int tY = position.y + (BOX_DIMENSIONS - tHeight) / 2;

            g.drawString(name, tX, tY);
        }
    }

    public void move(int x, int y){
        position.x = x;
        position.y = y;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
        if (code.size() == 0){
            code.addFirst("public class " + name);
            code.addLast("{");
            code.addLast("}");
        }
        else{
            code.set(0, "public class " + name);
        }
    }

    public LinkedList<String> getCode(){
        return code;
    }

    public void drawConnect(Box b, Graphics2D g){
        strategy.drawConnect(this, b, g);
    }

    public ArrayList<Box> getConnections(){
        return connections;
    }


}
