package org.example;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DrawArea extends JPanel implements PropertyChangeListener {
    public DrawArea(){
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (Box box : Repository.getInstance().getBoxes()){
            box.draw(g);
        }
        for (Box box : Repository.getInstance().getBoxes()){
            if (!box.getConnections().isEmpty()){
                for (Box conn : box.getConnections()){
                    box.drawConnect(conn, (Graphics2D) g);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }

}
