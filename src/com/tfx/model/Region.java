package com.tfx.model;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import com.tfx.Subject;

import javax.swing.*;
import java.awt.*;

/**
 * @author tianfx
 * @date 2024/12/4 17:07
 */
public class Region extends JBPanel {
    
    public static int FIX_HEIGHT = 600;
    
    public int startX;
    public int endX;
    public int heightY;

    public Region(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        setBorder();
    }

    public Region(LayoutManager layout) {
        super(layout);
        setBorder();
    }

    public Region(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        setBorder();
    }

    public Region() {
        super(null);
        setBorder();
    }

    public static Region getInstance(){
        return new Region();
    }
    
    public void setBorder(){
        this.setBorder(BorderFactory.createMatteBorder(2,2,0,2, JBColor.BLACK));
        this.setBackground(JBColor.LIGHT_GRAY);
    }

    @Override
    public void setLocation(int x, int y) {
        startX = x;
        heightY = y;
        super.setLocation(x, y);
    }

    public void setSize(int width) {
        endX = startX+width;
        super.setSize(width, FIX_HEIGHT);
    }

    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public int getHeightY() {
        return heightY;
    }

    public static Region getStartRegion(int width){
        Region region = getInstance();
        region.setLocation(Subject.START_LOCAL[0],Subject.START_LOCAL[1]+Master.HEIGHT);
        region.setSize(width);
        return region;
    }
}
