package org.example;

import java.awt.*;
import java.io.Serializable;

public class DecorationDecorator extends Decoratedbox implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int BOX_DIMENSIONS = 100;

    public DecorationDecorator(Box box){
        super(box);
        int index = 0;
        for (String part : box.getCode()){
            if (part.equals("{")){
                break;
            }
            index++;
        }
        box.getCode().add(index + 1, "\n    @Override\n" +
                "   public <type> <Decoratable method>(){\n" +
                "   }\n");
        box.getCode().add(index, "extends <Decoratable name>");
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.ORANGE);
        g.fillOval(position.x -15, position.y + BOX_DIMENSIONS -15, 30, 30);

        g.setColor(Color.red);
        String design = "🖼️️‍";
        g.setFont(g.getFont().deriveFont(20F));
        FontMetrics metrics = g.getFontMetrics(g.getFont());

        int tWidth = metrics.stringWidth(design);
        int tHeight = metrics.getHeight();
        int tX = position.x -10;
        int tY = position.y + BOX_DIMENSIONS + 7;

        g.drawString(design, tX, tY);
    }
}
