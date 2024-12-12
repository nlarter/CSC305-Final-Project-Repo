package org.example;

import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.io.Serializable;

public class StrategyDrawLine implements Strategy, Serializable {

    private static final long serialVersionUID = 1L;
    @Override
    public void drawConnect(Box b1, Box b2, Graphics g) {
        if (b1 == null || b2 == null || b1 == b2){
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        int startX = b1.position.x;
        int startY = b1.position.y + 25;
        int endX = b2.position.x;
        int endY = b2.position.y + 25;
        int midX = (startX + endX) / 2;
        int midY = (startY + endY) / 2;
        int displacement = 50;
        int deltaX = endX - startX;
        int deltaY = endY - startY;
        double controlX = midX - deltaY * displacement / Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double controlY = midY + deltaX * displacement / Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        QuadCurve2D q = new QuadCurve2D.Float(startX, startY, (int)controlX, (int)controlY, endX, endY);
        g2d.draw(q);
    }
}
