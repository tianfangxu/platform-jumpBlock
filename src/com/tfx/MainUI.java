package com.tfx;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.Content;
import com.tfx.model.Master;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author tianfx
 * @date 2024/12/4 14:04
 */
public class MainUI  implements ToolWindowFactory, DumbAware {
    
    public static String EXPLAIN = "explain";
    public static String TABTITLE1 = "block1";
    public static String TABTITLE2 = "block2";
    
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        try {
            JComponent explain = getExplainText();
            Content explainContent = toolWindow.getContentManager().getFactory().createContent(explain, EXPLAIN, false);
            toolWindow.getContentManager().addContent(explainContent);
            
            JComponent jComponent1 = getJumpComponent(JBColor.YELLOW);
            Content content1 = toolWindow.getContentManager().getFactory().createContent(jComponent1, TABTITLE1, false);
            toolWindow.getContentManager().addContent(content1);

            JComponent jComponent2 = getJumpComponent(JBColor.GREEN);
            Content content2 = toolWindow.getContentManager().getFactory().createContent(jComponent2, TABTITLE2, false);
            toolWindow.getContentManager().addContent(content2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JComponent getExplainText() {
        JBPanel panel = new JBPanel(null);
        JBLabel label = new JBLabel("键位说明：方向键操作，点击右边的 tab 开始吧");
        label.setLocation(0,0);
        label.setSize(200,30);
        panel.add(label);
        return panel;
    }

    private JComponent getJumpComponent(JBColor jbColor) {
        JBPanel panel = new JBPanel(null);
        // 设置可聚焦
        panel.setFocusable(true);

        Master master = Master.getInstance(0, 150);
        master.setSize(30,30);
        master.setBackground(jbColor);
        panel.add(master);

        panel.addKeyListener(master.keyAction());

        JPanel area = new JPanel(null);
        area.setLocation(0,181);
        area.setSize(800,2);
        area.setBorder(new LineBorder(Color.BLACK));
        panel.add(area);
        
        return panel;
    }
}
