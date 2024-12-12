package org.example;

import javax.swing.*;

/*
    Author: Nico Larter
    Class: CSC 305
 */

public class Main extends JFrame {

    public Main(){
        DrawArea drawPanel = new DrawArea();
        JTextArea codeScroll = new TextArea();
        JTextArea codeText = new TextArea();
        DrawAreaListener drawAreaListener = new DrawAreaListener((TextArea) codeScroll, (TextArea) codeText);
        drawPanel.addMouseListener(drawAreaListener);
        drawPanel.addMouseMotionListener(drawAreaListener);
        add(drawPanel);
        Repository.getInstance().addPropertyChangeListener(drawPanel);

        JMenuBar menu = new MenuBar(drawAreaListener, drawPanel);
        setJMenuBar(menu);

        codeText.setEditable(false);
        codeScroll.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(codeScroll);
        JScrollPane scrollPaneCode = new JScrollPane(codeText);

        JSplitPane split = new JSplitPane();


        split.setLeftComponent(scrollPane);
        split.setRightComponent(scrollPaneCode);

        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Draw Area", drawPanel);
        pane.addTab("Code", split);
        add(pane);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setTitle("CSC 305 Final Project");
        main.setSize(800, 600);
        main.setLocationRelativeTo(null);
        main.setResizable(false);
        main.setVisible(true);

    }
}