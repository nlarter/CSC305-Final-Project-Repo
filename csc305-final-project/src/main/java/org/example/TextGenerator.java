package org.example;

import java.util.ArrayList;

public class TextGenerator {

    private String text;

    private String code;

    public TextGenerator(){
        text = "src\n";
        code = "";
    }

    public void updateText(){
        text = "src\n";
        for (Box box : Repository.getInstance().getBoxes()){
            String className = box.getName();
            text += "|--";
            text += className;
            text += ".java\n";
        }
    }

    public String getText(){
        return text;
    }

    public void updateCode(){
        StringBuilder sb = new StringBuilder();
        for (Box box : Repository.getInstance().getBoxes()){
            for (String part : box.getCode()){
                sb.append(part + "\n");
                if (part.equals("}")){
                    sb.append("\n");
                }
            }
        }
        code = sb.toString();
    }

    public String getCode(){
        return code;
    }
}
