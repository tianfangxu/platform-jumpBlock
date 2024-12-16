package com.tfx;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import com.tfx.common.KeyMap;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author tianfx
 * @date 2024/12/16 14:19
 */
public class Setting {

    public static int LEFT_KEY = 37;
    public static int RIGHT_KEY = 39;
    public static int JUMP_KEY = 38;

    public static void main(String[] args) {
        JFrame jf = new JFrame("jumpBlock");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBackground(JBColor.DARK_GRAY);
        jf.setContentPane(getSettingUI());
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
    
    
    public static JComponent getSettingUI(){
        JBPanel panel = new JBPanel(null);
        
        JBLabel title = new JBLabel("Key Position Description：");
        title.setLocation(10,0);
        title.setSize(200,30);

        JBLabel leftText = new JBLabel("Key Left");
        leftText.setLocation(10,35);
        leftText.setSize(65,30);
        JBTextField left = new JBTextField("LEFT ARROW键");
        left.setLocation(80,35);
        left.setSize(200,30);
        addKeyAdapter(left,"LEFT_KEY");

        JBLabel rightText = new JBLabel("Key Right");
        rightText.setLocation(10,70);
        rightText.setSize(65,30);
        JBTextField right = new JBTextField("RIGHT ARROW键");
        right.setLocation(80,70);
        right.setSize(200,30);
        addKeyAdapter(right,"RIGHT_KEY");

        JBLabel JumpText = new JBLabel("Key Jump");
        JumpText.setLocation(10,105);
        JumpText.setSize(65,30);
        JBTextField Jump = new JBTextField("UP ARROW键");
        Jump.setLocation(80,105);
        Jump.setSize(200,30);
        addKeyAdapter(Jump,"JUMP_KEY");
        
        panel.add(title);
        panel.add(leftText);
        panel.add(left);
        panel.add(rightText);
        panel.add(right);
        panel.add(JumpText);
        panel.add(Jump);
        
        return panel;
    }
    
    public static void addKeyAdapter(JBTextField field,String keyName){
        field.addKeyListener(new KeyAdapter() {
            private int key;
            @Override
            public void keyPressed(KeyEvent e) {
                key = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode != key){
                    return;
                }
                field.setText(KeyMap.keyMap.get(keyCode));
                switch (keyName){
                    case "LEFT_KEY":
                        Setting.LEFT_KEY = key;
                        break;
                    case "RIGHT_KEY":
                        Setting.RIGHT_KEY = key;
                        break;
                    case "JUMP_KEY":
                        Setting.JUMP_KEY = key;
                        break;
                }
            }
        });
    }

}
