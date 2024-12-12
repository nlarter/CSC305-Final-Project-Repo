package org.example;

import java.awt.*;
import java.io.Serializable;

public class ProductDecorator extends Decoratedbox implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int BOX_DIMENSIONS = 100;

    public ProductDecorator(Box box){
        super(box);
        int index = 0;
        box.getCode().set(0, "public abstract class " + box.getName());
        for (String part : box.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }
        box.getCode().add(index + 1, "\n" +
                "   void print(){\n" +
                "   }\n" +
                "   //other methods...\n");
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.ORANGE);
        g.fillOval(position.x + (BOX_DIMENSIONS / 2) - 15, position.y + (BOX_DIMENSIONS / 2) , 30, 30);

        g.setColor(Color.red);
        String design = "📦";
        g.setFont(g.getFont().deriveFont(20F));
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        int tWidth = metrics.stringWidth(design);
        int tHeight = metrics.getHeight();
        int tX = position.x + (BOX_DIMENSIONS / 2) - 10;
        int tY = position.y + (BOX_DIMENSIONS / 2) + 22;

        g.drawString(design, tX, tY);
    }
}
