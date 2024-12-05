package com.tfx;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.Content;
import com.tfx.interfaces.DieNoticeHandle;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author tianfx
 * @date 2024/12/4 14:04
 */
public class MainUI  implements ToolWindowFactory, DumbAware , DieNoticeHandle {
    
    public static String EXPLAIN = "explain";
    public static String TABTITLE1 = "block1";
    public static String TABTITLE2 = "block2";
    
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        try {
            JComponent explain = getExplainText();
            Content explainContent = toolWindow.getContentManager().getFactory().createContent(explain, EXPLAIN, false);
            toolWindow.getContentManager().addContent(explainContent);
            
            JComponent jComponent1 = Subject.getInstance().getProject(JBColor.YELLOW);
            Content content1 = toolWindow.getContentManager().getFactory().createContent(jComponent1, TABTITLE1, false);
            toolWindow.getContentManager().addContent(content1);

            JComponent jComponent2 = Subject.getInstance().getProject(JBColor.GREEN);
            Content content2 = toolWindow.getContentManager().getFactory().createContent(jComponent2, TABTITLE2, false);
            toolWindow.getContentManager().addContent(content2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JComponent getExplainText() {
        JBPanel panel = new JBPanel(null);
        JBLabel label = new JBLabel("键位说明：方向键移动（ ← 左移动 ,↑ 跳跃,→ 右移动 ），双击或长按会加速移动，点击上边的 tab 开始吧");
        label.setLocation(0,0);
        label.setSize(1000,30);
        panel.add(label);
        return panel;
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("jumpBlock");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setContentPane(Subject.getInstance().getProject(JBColor.YELLOW));
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
