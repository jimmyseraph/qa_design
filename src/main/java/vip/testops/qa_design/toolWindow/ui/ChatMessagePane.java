package vip.testops.qa_design.toolWindow.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBPanelWithEmptyText;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ChatMessagePane extends JPanel implements DataProvider {
    private final Disposable parentDisposable;

    public ChatMessagePane(Disposable parentDisposable) {
        this.parentDisposable = parentDisposable;
    }

    public void registerKeyboardAction(@NotNull ActionListener aAction, String aCommand, @Nullable KeyStroke keyStroke, int condition) {
        super.registerKeyboardAction(aAction, aCommand, keyStroke, condition);
    }

    @Override
    public @Nullable Object getData(@NotNull @NonNls String dataId) {
        return PlatformDataKeys.UI_DISPOSABLE.is(dataId) ? parentDisposable : null;
    }
}
