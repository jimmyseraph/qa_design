package vip.testops.qa_design.settings;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.QaDesignBundle;
import vip.testops.qa_design.utils.SensitiveDataUtil;

import javax.swing.*;
import java.awt.*;

public class QaDesignConfigurable implements SearchableConfigurable {

    private final QaDesignSettings settings = QaDesignSettings.getSettings();
    private final CredentialAttributes credentialAttributes = SensitiveDataUtil.createCredentialAttributes("OpenAI Key");

    private JPanel mainPanel;
    private JCheckBox enableAICheckBox;
    private JTextField openaiKeyTextField;

    @Override
    public @NotNull @NonNls String getId() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return QaDesignBundle.message("settings.qa_design.displayName");
    }

    @Override
    public @Nullable JComponent createComponent() {
        if(mainPanel == null){
            mainPanel = new JPanel(new BorderLayout());

            enableAICheckBox = new JCheckBox("Enable AI", settings.isEnableAI());
            enableAICheckBox.addChangeListener(e -> {
                openaiKeyTextField.setEnabled(enableAICheckBox.isSelected());

            });
            openaiKeyTextField = new JTextField(25);
            openaiKeyTextField.setEnabled(enableAICheckBox.isSelected());
            Credentials credentials = PasswordSafe.getInstance().get(credentialAttributes);
            String openaiKey = "";
            if(credentials != null) {
                openaiKey = credentials.getPasswordAsString() != null ? credentials.getPasswordAsString() : "";
            }
            openaiKeyTextField.setText(openaiKey);

            JPanel northPanel = new JPanel(new GridBagLayout());
            mainPanel.add(northPanel, BorderLayout.NORTH);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            northPanel.add(enableAICheckBox, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            northPanel.add(openaiKeyTextField, gbc);
        }
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        boolean result = settings.isEnableAI() != enableAICheckBox.isSelected();
        Credentials credentials = PasswordSafe.getInstance().get(credentialAttributes);
        String openaiKey = "";
        if(credentials != null) {
            openaiKey = credentials.getPasswordAsString() != null ? credentials.getPasswordAsString() : "";
        }
        result |= !openaiKey.equals(openaiKeyTextField.getText());
        return result;
    }

    @Override
    public void reset() {
        enableAICheckBox.setSelected(settings.isEnableAI());
        String openaiKey = "";
        Credentials credentials = PasswordSafe.getInstance().get(credentialAttributes);
        if(credentials != null) {
            openaiKey = credentials.getPasswordAsString() != null ? credentials.getPasswordAsString() : "";
        }
        openaiKeyTextField.setText(openaiKey);
    }

    @Override
    public void apply() throws ConfigurationException {
        settings.setEnableAI(enableAICheckBox.isSelected());
        Credentials credentials = new Credentials(null, openaiKeyTextField.getText());
        PasswordSafe.getInstance().set(credentialAttributes, credentials);
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        if (openProjects.length > 0) {
            Project project = openProjects[0];
            ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
            ToolWindow toolWindow = toolWindowManager.getToolWindow("Chat Window");
            if(toolWindow != null && !enableAICheckBox.isSelected()) {
                toolWindow.setAvailable(false);
            } else if (toolWindow != null){
                toolWindow.setAvailable(true);
            }

        }
    }
}
