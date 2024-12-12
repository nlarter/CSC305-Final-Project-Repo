package org.example;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Repository implements Serializable {

    private static final long serialVersionUID = 1L;
    private final ArrayList<Box> boxes = new ArrayList<>();
    private transient PropertyChangeSupport propertyChangeSupport;
    private String code;
    private static Repository instance;


    private Repository() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public static Repository getInstance(){
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public static void setInstance(Repository newRepo){
        instance = newRepo;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void clear(DrawAreaListener da){
        boxes.clear();
        code = "";
        da.update();
        repaint();
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
    public void addBox(Box box){
        boxes.add(box);
    }

    public Box getBox(int index){
        return boxes.get(index);
    }

    public ArrayList<Box> getBoxes(){
        return boxes;
    }

    public boolean updateBox(Box newBox, Box oldBox){
        int index = boxes.indexOf(oldBox);
        if (index != -1){
            boxes.set(index, newBox);
            return true;
        }
        return false;
    }

    public void updateConnection(Box first, Box second){
        first.getConnections().add(second);
    }

    public void updateConnectionIn(Box b1, Box b2){
        b1.getCode().add(1, "extends " + b2.getName());
        b1.getConnections().add(b2);
    }

    public void updateConnectionRe(Box b1, Box b2){
        b1.getCode().add(1, "implements " + b2.getName());
        b1.getConnections().add(b2);
    }

    public void updateConnectionAs(Box b1, Box b2){
        int index = 0;
        for (String part : b1.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }

        b1.getCode().add(index + 1, "   public void foo(){\n" +
                "       " + b2.getName() + " name = new " + b2.getName() + "();\n" +
                "       " + "name.doSomething();\n" +
                "   }");
        index = 0;
        for (String part : b2.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }
        b2.getCode().add(index + 1, "   public void doSomething(){...}");
        b1.getConnections().add(b2);
    }

    public void updateConnectionsCo(Box b1, Box b2){
        int index = 0;
        for (String part : b1.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }
        b1.getCode().add(index + 1, "   private " + b2.getName() + " name;\n" +
                "\n" +
                "   public " + b1.getName() + "(String nameName){\n" +
                "       this.name = new " + b2.getName() + "(nameName);\n" +
                "   }");
        index = 0;
        for (String part : b2.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }
        b2.getCode().add(index + 1, "   private String name;\n" +
                "\n" +
                "   public " + b2.getName() + "(String name){\n" +
                "       this.name = name;\n" +
                "   }");
    }

    public void updateConnectionAg(Box b1, Box b2){
        int index = getIndex(b1);
        b1.getCode().add(index + 1, "   private " + b2.getName() + " typeName;\n" +
                "\n" +
                "   public " + b1.getName() + "(" + b2.getName() + " typeName){\n" +
                "       this.typeName = typeName;\n" +
                "   }");
        index = getIndex(b2);
        b2.getCode().add(index + 1, "   private String name;\n" +
                "\n" +
                "   public " + b2.getName() + "(String name){\n" +
                "       this.name = name;\n" +
                "   }");
    }

    private static int getIndex(Box box){
        int index = 0;
        for (String part : box.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }
        return index;
    }

    public void repaint() {
        propertyChangeSupport.firePropertyChange("repaint", 0, 1);
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

}
