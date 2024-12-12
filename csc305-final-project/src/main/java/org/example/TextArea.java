package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class TextArea extends JTextArea {

    private String text = "";
    public TextArea(){
        setEditable(false);
        setText(text);
    }

    @Override
    public void setText(String newText){
        text = newText;
        super.setText(text);
    }

    @Override
    public String getText(){
        return text;
    }
}
