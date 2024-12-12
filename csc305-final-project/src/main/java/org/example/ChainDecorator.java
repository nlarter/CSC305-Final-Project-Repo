package org.example;

import java.awt.*;
import java.io.Serializable;

public class ChainDecorator extends Decoratedbox implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int BOX_DIMENSIONS = 100;

    public ChainDecorator(Box box){
        super(box);
        int index = 0;
        for (String part : box.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }
        box.getCode().add(index + 1, "\n    @Override\n" +
                "   public void handleRequest(){\n" +
                "       if (<condition>){\n" +
                "           //code here...\n" +
                "       } else {\n" +
                "           //code here...\n" +
                "       }\n" +
                "   }\n");
        box.getCode().add(index, "extends <Handler name>");
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.ORANGE);
        g.fillOval(position.x -15, position.y + (BOX_DIMENSIONS / 2) - 15, 30, 30);

        g.setColor(Color.darkGray);
        String design = "⛓️‍";
        g.setFont(g.getFont().deriveFont(24F));
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        int tWidth = metrics.stringWidth(design);
        int tHeight = metrics.getHeight();
        int tX = position.x - 7;
        int tY = position.y + (BOX_DIMENSIONS / 2) + 7;

        g.drawString(design, tX, tY);
    }
}
