package com.tfx.model;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author tianfx
 * @date 2024/12/11 17:46
 */
public class Protag extends JPanel {

    private KeyListener keyListener;


    public KeyListener keyAction(){
        if (keyListener != null){
            return keyListener;
        }
        keyListener =  new KeyListener() {
            @Override// 当按下并释放键时调用
            public void keyTyped(KeyEvent e) {}
            @Override// 当释放键时调用
            public void keyReleased(KeyEvent e) {
                System.out.println(System.currentTimeMillis());
            }
            @Override// 当按下键时调用
            public void keyPressed(KeyEvent e) {
                System.out.println(System.currentTimeMillis());
            }
        };
        return keyListener;
    }

    public static void main1(String[] args) {
        JFrame jf = new JFrame("test");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JBPanel map = new JBPanel(null);
        // 设置可聚焦
        map.setFocusable(true);
        Protag protag = new Protag();
        map.add(protag);
        map.addKeyListener(protag.keyAction());
        jf.setBackground(JBColor.DARK_GRAY);
        jf.setContentPane(map);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
