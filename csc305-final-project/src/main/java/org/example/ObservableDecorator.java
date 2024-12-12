package org.example;

import java.awt.*;
import java.io.Serializable;

public class ObservableDecorator extends Decoratedbox implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int BOX_DIMENSIONS = 100;

    public ObservableDecorator(Box box){
        super(box);
        box.getCode().add(1, "extends PropertyChangeSupport");
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.ORANGE);
        g.fillOval(position.x + (BOX_DIMENSIONS - 15), position.y - 15, 30, 30);

        g.setColor(Color.red);
        String design = "💡";
        g.setFont(g.getFont().deriveFont(24F));
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        int tWidth = metrics.stringWidth(design);
        int tHeight = metrics.getHeight();
        int tX = position.x + (BOX_DIMENSIONS - 15) + 3;
        int tY = position.y + 7;

        g.drawString(design, tX, tY);
    }
}
