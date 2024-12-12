package org.example;

import java.awt.*;
import java.io.Serializable;

public class DecoratableDecorator extends Decoratedbox implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int BOX_DIMENSIONS = 100;

    public DecoratableDecorator(Box box){
        super(box);
        int index = 0;
        box.getCode().set(0, "public abstract class " + box.getName());
        for (String part : box.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }
        box.getCode().add(index + 1, "  public abstract <type> <Decoratable method> (){\n" +
                "   }\n");
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.ORANGE);
        g.fillOval(position.x -15 + (BOX_DIMENSIONS / 2), position.y -15, 30, 30);

        g.setColor(Color.red);
        String design = "🎄️‍";
        g.setFont(g.getFont().deriveFont(24F));
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        int tWidth = metrics.stringWidth(design);
        int tHeight = metrics.getHeight();
        int tX = position.x -15 + (BOX_DIMENSIONS / 2) + 5;
        int tY = position.y + 7;

        g.drawString(design, tX, tY);
    }
}
