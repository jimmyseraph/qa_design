package vip.testops.qa_design.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "QaDesignSettings", storages = @Storage("qa_design_settings.xml"))
public final class QaDesignSettings implements PersistentStateComponent<QaDesignSettings> {

    private boolean enableAI;

    public boolean isEnableAI() {
        return enableAI;
    }

    public void setEnableAI(boolean enableAI) {
        this.enableAI = enableAI;
    }

    public static QaDesignSettings getSettings() {
        return ApplicationManager.getApplication().getService(QaDesignSettings.class);
    }

    @Override
    public @Nullable QaDesignSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull QaDesignSettings state) {
        this.enableAI = state.isEnableAI();
    }

}
