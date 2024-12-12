package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawAreaListener implements MouseListener, MouseMotionListener {

    private int selected = -1;
    private int preX, preY;
    private boolean connecting = false;
    private String connType = "";
    private Box firstSelected = null;
    private final int BOX_DIMENSIONS = 100;
    private TextGenerator tg = new TextGenerator();
    private TextArea scroll, code;

    public DrawAreaListener(TextArea t1, TextArea t2){
        scroll = t1;
        code = t2;
    }

    public void setConnecting(String connType){
        connecting = true;
        this.connType = connType;
    }
    private int boxSelected(MouseEvent e){
        int boxSelected = -1;
        for (int i = 0; i < Repository.getInstance().getBoxes().size(); i++){
            Box box = Repository.getInstance().getBox(i);
            if (e.getX() >= box.position.x && e.getX() <= box.position.x + BOX_DIMENSIONS &&
                    e.getY() >= box.position.y && e.getY() <= box.position.y + BOX_DIMENSIONS){
                boxSelected = i;
            }
        }
        return boxSelected;
    }

    private Box findclicked(MouseEvent e){
        Box clickedBox = null;
        for (Box box : Repository.getInstance().getBoxes()) {
            if (e.getX() >= box.position.x && e.getX() <= box.position.x +BOX_DIMENSIONS &&
                    e.getY() >= box.position.y && e.getY() <= box.position.y + BOX_DIMENSIONS) {
                clickedBox = box;
                break;
            }
        }
        return clickedBox;
    }

    public void update(){
        tg.updateText();
        scroll.setText(tg.getText());
        tg.updateCode();
        code.setText(tg.getCode());
        Repository.getInstance().setCode(tg.getCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        DrawArea canvas = (DrawArea) e.getSource();

        Box clickedBox = findclicked(e);

        if (connecting){
            if (clickedBox != null){
                if (firstSelected == null){
                    firstSelected = clickedBox;
                }
                else{
                    Box secondSelected = clickedBox;
                    Graphics g = canvas.getGraphics();

                    switch (connType){
                        case "Inheritance":
                            Repository.getInstance().updateConnectionIn(firstSelected, secondSelected);
                            break;
                        case "Realization":
                            Repository.getInstance().updateConnectionRe(firstSelected, secondSelected);
                            break;
                        case "Association":
                            Repository.getInstance().updateConnectionAs(firstSelected, secondSelected);
                            break;
                        case "Composition":
                            Repository.getInstance().updateConnectionsCo(firstSelected, secondSelected);
                            break;
                        case "Aggregation":
                            Repository.getInstance().updateConnectionAg(firstSelected, secondSelected);
                            break;
                    }

                    update();
                    Repository.getInstance().updateConnection(firstSelected, secondSelected);

                    firstSelected = null;
                    connecting = false;
                }
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3 && clickedBox != null){
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem observerItem = new JMenuItem("observer");
            JMenuItem observabletem = new JMenuItem("observable");
            JMenuItem singletonItem = new JMenuItem("singleton");
            JMenuItem decorationItem = new JMenuItem("decoration");
            JMenuItem decoratableItem = new JMenuItem("decoratable");
            JMenuItem chainItem = new JMenuItem("chain member");
            JMenuItem strategyItem = new JMenuItem("strategy");
            JMenuItem factoryItem = new JMenuItem("factory");
            JMenuItem productItem = new JMenuItem("product");


            popupMenu.add(observerItem);
            observerItem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new ObserverDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });
            popupMenu.add(observabletem);
            observabletem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new ObservableDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });
            popupMenu.add(singletonItem);
            singletonItem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new SingletonDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });
            popupMenu.add(decorationItem);
            decorationItem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new DecorationDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });
            popupMenu.add(decoratableItem);
            decoratableItem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new DecoratableDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });
            popupMenu.add(chainItem);
            chainItem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new ChainDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });
            popupMenu.add(strategyItem);
            strategyItem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new StrategyDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });
            popupMenu.add(factoryItem);
            factoryItem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new FactoryDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });
            popupMenu.add(productItem);
            productItem.addActionListener(ev -> {
                final Box toDecorate = clickedBox;
                Box modifiedBox = new ProductDecorator(toDecorate);
                Repository.getInstance().updateBox(modifiedBox, clickedBox);
                update();
                canvas.repaint();
            });

            popupMenu.show(canvas, e.getX(), e.getY());
        }
        else {

            if (clickedBox != null) {
                String name = clickedBox.getName();
                String newName = (String) JOptionPane.showInputDialog(
                        e.getComponent(), "Enter new name", "Name",
                        JOptionPane.PLAIN_MESSAGE, null, null, name);
                if (newName != null && !newName.isEmpty()) {
                    clickedBox.setName(newName);
                }
                Repository.getInstance().repaint();
            } else {
                Box toAdd = new Box(new Point(e.getX(), e.getY()));
                toAdd.setName("Box" + Repository.getInstance().getBoxes().size());
                Repository.getInstance().addBox(toAdd);
            }
            update();
            canvas.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        DrawArea canvas = (DrawArea) e.getSource();
        selected = boxSelected(e);
        if (selected == -1){
            return;
        }
        Box box = Repository.getInstance().getBox(selected);
        preX = box.position.x - e.getX();
        preY = box.position.y - e.getY();
        box.move(preX + e.getX(), preY + e.getY());
        Repository.getInstance().repaint();
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        DrawArea canvas = (DrawArea) e.getSource();
        if (selected == -1){
            return;
        }
        Box box = Repository.getInstance().getBox(selected);
        box.move(preX + e.getX(), preY + e.getY());
        Repository.getInstance().repaint();
        canvas.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        DrawArea canvas = (DrawArea) e.getSource();
        if (selected == -1) {
            return;
        }
        Box box = Repository.getInstance().getBox(selected);
        box.move(preX + e.getX(), preY + e.getY());
        Repository.getInstance().repaint();
        canvas.repaint();
        selected = boxSelected(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
