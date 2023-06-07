package vip.testops.qa_design.toolWindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManagerEvent;
import com.intellij.ui.content.ContentManagerListener;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.settings.QaDesignSettings;
import vip.testops.qa_design.toolWindow.ui.ChatToolWindow;

public class ChatToolWindowFactory implements ToolWindowFactory, DumbAware {

    private final QaDesignSettings settings = QaDesignSettings.getSettings();

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return settings.isEnableAI();
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ChatToolWindow chatToolWindow = new ChatToolWindow(toolWindow, project);
        Content content = ContentFactory.getInstance().createContent(chatToolWindow.getContentPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }


}
