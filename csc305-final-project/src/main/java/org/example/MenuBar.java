package org.example;

import javax.swing.*;
import java.io.*;

public class MenuBar extends JMenuBar {
    private DrawAreaListener drawAreaListener;
    private DrawArea canvas;
    private void setDrawAreaListener(DrawAreaListener drawAreaListener){
        this.drawAreaListener = drawAreaListener;
    }
    private void setCanvas(DrawArea da){
        canvas = da;
    }
    public MenuBar(DrawAreaListener drawAreaListener, DrawArea da){
        setCanvas(da);
        setDrawAreaListener(drawAreaListener);

        JMenu fileMenu = new JMenu("File");
        JMenu boxMenu = new JMenu("Box Connector");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(ev -> {
            newCode(drawAreaListener);
        });
        fileMenu.add(newItem);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(ev -> {
            String filePath = JOptionPane.showInputDialog(null, "Enter File Path: ", "Enter File", JOptionPane.PLAIN_MESSAGE);
            openCode(filePath);
        });
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(ev -> {
            String filePath = JOptionPane.showInputDialog(null, "Enter File Path: ", "Enter File", JOptionPane.PLAIN_MESSAGE);
            saveCode(filePath);
        });
        fileMenu.add(saveItem);

        JMenuItem aggregation = new JMenuItem("Aggregation");
        aggregation.addActionListener(ev -> {
            drawAreaListener.setConnecting("Aggregation");
        });
        JMenuItem composition = new JMenuItem("Composition");
        composition.addActionListener(ev -> {
            drawAreaListener.setConnecting("Composition");
        });
        JMenuItem association = new JMenuItem("Association");
        association.addActionListener(ev -> {
            drawAreaListener.setConnecting("Association");
        });
        JMenuItem inheritance = new JMenuItem("Inheritance");
        inheritance.addActionListener(ev -> {
            drawAreaListener.setConnecting("Inheritance");
        });
        JMenuItem realization = new JMenuItem("Realization");
        realization.addActionListener(ev -> {
            drawAreaListener.setConnecting("Realization");
        });

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(ev -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("About Window");
            dialog.setVisible(true);
            dialog.setSize(300, 200);
            JTextArea desc = new TextArea();
            desc.setText("Final Project\n" +
                    "2024\n" +
                    "Nico Larter");
            dialog.add(desc);
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(da);
        });
        helpMenu.add(about);

        boxMenu.add(aggregation);
        boxMenu.add(composition);
        boxMenu.add(association);
        boxMenu.add(inheritance);
        boxMenu.add(realization);

        add(fileMenu);
        add(boxMenu);
        add(helpMenu);
    }

    public void saveCode(String fileName){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(Repository.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newCode(DrawAreaListener da){
        Repository.getInstance().clear(da);
        Repository.getInstance().repaint();
        canvas.repaint();
    }

    public void openCode(String filePath){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))){
            Repository newRepo = (Repository) ois.readObject();
            Repository.setInstance(newRepo);
            newRepo.repaint();
            drawAreaListener.update();
            canvas.repaint();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
