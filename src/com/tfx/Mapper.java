package com.tfx;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import com.tfx.interfaces.DieNoticeHandle;
import com.tfx.model.Master;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author tianfx
 * @date 2024/12/4 18:05
 */
public class Mapper implements DieNoticeHandle {
    
    public static Mapper getInstance(){
        return new Mapper();
    }

    JBPanel map;

    public JComponent getMap(JBColor color){
        System.out.println("========");
        map = new JBPanel(null);
        // 设置可聚焦
        map.setFocusable(true);

        Master master = Master.getInstance(0, 150,this);
        master.setSize(30,30);
        master.setBackground(color);
        map.add(master);

        map.addKeyListener(master.keyAction());

        JPanel area = new JPanel(null);
        area.setLocation(0,181);
        area.setSize(800,2);
        area.setBorder(new LineBorder(Color.BLACK));
        map.add(area);

        return map;
    }

    @Override
    public void notice(Master dieMaster) {
        this.map.removeKeyListener(dieMaster.keyAction());
        Master master = Master.getInstance(0, 150,this);
        master.setSize(30,30);
        master.setBackground(Color.GREEN);
        this.map.add(master);
        this.map.repaint();
        this.map.addKeyListener(master.keyAction());
    }
}
