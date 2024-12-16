package com.tfx;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import com.tfx.interfaces.DieNoticeHandle;
import com.tfx.mapper.Levels1;
import com.tfx.model.Master;
import com.tfx.model.Region;

import javax.swing.*;
import java.util.List;

/**
 * @author tianfx
 * @date 2024/12/4 18:05
 */
public class Subject implements DieNoticeHandle {
    
    public static int[] START_LOCAL = new int[]{0,150};
    
    public static Subject getInstance(){
        return new Subject();
    }

    JBPanel map;
    JBColor color;
    List<Region> regions;

    public JComponent getProject(JBColor color){
        this.color = color;
        map = new JBPanel(null);
        // 设置可聚焦
        map.setFocusable(true);
        
        
        Master master = Master.getInstance(START_LOCAL[0],START_LOCAL[1],this);
        master.setBackground(color);
        map.add(master);

        regions = Levels1.getInstance().intiArea();
        map.addKeyListener(master.keyAction());
        for (Region region : regions) {
            map.add(region);
        }
        master.setRegions(regions);
        return map;
    }

    @Override
    public void notice(Master dieMaster) {
        this.map.removeKeyListener(dieMaster.keyAction());
        Master master = Master.getInstance(START_LOCAL[0],START_LOCAL[1],this);
        master.setBackground(color);
        this.map.add(master);
        this.map.repaint();
        this.map.addKeyListener(master.keyAction());
        master.setRegions(regions);
    }



    public static void main(String[] args) {
        JFrame jf = new JFrame("jumpBlock");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JComponent project = Subject.getInstance().getProject(JBColor.BLACK);
        project.setBackground(JBColor.DARK_GRAY);
        jf.setBackground(JBColor.DARK_GRAY);
        jf.setContentPane(project);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
