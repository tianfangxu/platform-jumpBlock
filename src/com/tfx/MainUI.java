package com.tfx;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author tianfx
 * @date 2024/12/4 14:04
 */
public class MainUI  implements ToolWindowFactory, DumbAware{
    
    public static String EXPLAIN = "setting";
    public static String TABTITLE1 = "block1";
    
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        try {
            JComponent setting = Setting.getSettingUI();
            Content explainContent = toolWindow.getContentManager().getFactory().createContent(setting, EXPLAIN, false);
            toolWindow.getContentManager().addContent(explainContent);
            
            JComponent jComponent = Subject.getInstance().getProject(JBColor.YELLOW);
            Content content1 = toolWindow.getContentManager().getFactory().createContent(jComponent, TABTITLE1, false);
            toolWindow.getContentManager().addContent(content1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
