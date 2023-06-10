package vip.testops.qa_design.toolWindow.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.actions.IncrementalFindAction;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.EditorTextFieldProvider;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.util.concurrency.annotations.RequiresBackgroundThread;
import com.intellij.util.containers.ContainerUtil;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import org.intellij.plugins.markdown.settings.MarkdownSettings;
import org.intellij.plugins.markdown.ui.preview.MarkdownHtmlPanel;
import org.intellij.plugins.markdown.ui.preview.MarkdownHtmlPanelProvider;
import org.intellij.plugins.markdown.ui.preview.html.MarkdownUtil;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignNotifier;
import vip.testops.qa_design.services.QaDesignChatService;
import vip.testops.qa_design.toolWindow.ui.entities.Message;
import vip.testops.qa_design.toolWindow.ui.entities.Sender;
import vip.testops.qa_design.utils.CycledList;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import static com.theokanning.openai.service.OpenAiService.defaultObjectMapper;

public class ChatToolWindow {
    private final JPanel contentPanel = new JPanel();
    private Project project;
    private final EditorColorsScheme scheme = EditorColorsManager.getInstance().getSchemeForCurrentUITheme();

    private CycledList<ChatMessage> chatMessages = new CycledList<>(7,3);

    public ChatToolWindow(ToolWindow toolWindow, Project project) {
        this.project = project;
        contentPanel.setLayout(new BorderLayout(0, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JBScrollPane chatHistoryPanel = createChatHistoryPanel();
        contentPanel.add(chatHistoryPanel, BorderLayout.CENTER);
        contentPanel.add(createChatInputPanel(chatHistoryPanel), BorderLayout.SOUTH);
        chatHistoryPanel.revalidate();
        chatHistoryPanel.repaint();
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = chatHistoryPanel.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });
        chatMessages.add(new ChatMessage(
                ChatMessageRole.SYSTEM.value(),
                "If need to answer about test points, please reply them with order list."
        ));
        chatMessages.add(new ChatMessage(
                ChatMessageRole.SYSTEM.value(),
                "If need to answer about test cases design, put each test case like below, and replace every bracket \"{}\" with real content in single line:\n" +
                        "```\n" +
                        "TestCase: {case name}\n" +
                        "TestCaseDesc: {case description}\n" +
                        "TestCaseData: {test case data}\n" +
                        "TestCaseStep: {steps of this test case}\n" +
                        "TestCaseExpect: {expected result of this test case}\n" +
                        "```\n" +
                        "do not use index number after testcase."
        ));
        chatMessages.add(new ChatMessage(
                ChatMessageRole.SYSTEM.value(),
                "put all the testcases into Markdown code segment, language is qa_design."
        ));
    }

    @NotNull
    private JBScrollPane createChatHistoryPanel() {
        JPanel panel = new JPanel(new VerticalLayout(5));
        JBScrollPane chatHistoryPanel = new JBScrollPane(
                panel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        return chatHistoryPanel;
    }

    @NotNull
    private JPanel createChatInputPanel(JBScrollPane chatHistoryPanel) {
        JPanel chatInputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        EditorTextField inputField = EditorTextFieldProvider
                .getInstance()
                .getEditorField(
                        Objects.requireNonNull(Language.findLanguageByID("Markdown")),
                        this.project, List.of((editorEx) -> {
                            EditorSettings editorSettings = editorEx.getSettings();
                            editorSettings.setLineNumbersShown(false);
                            editorSettings.setLineMarkerAreaShown(false);
                            editorSettings.setUseSoftWraps(true);
                            editorSettings.setAnimatedScrolling(true);
                            editorEx.setBorder(BorderFactory.createLineBorder(scheme.getColor(EditorColors.TEARLINE_COLOR)));
                        })
                );
        Dimension preferredSize = inputField.getPreferredSize();
        inputField.setPreferredSize(new Dimension(preferredSize.width, 60));
        chatInputPanel.add(inputField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            String text = inputField.getText();
            JPanel history = (JPanel)chatHistoryPanel.getViewport().getView();
            history.add(createMessagePane(new Message(text, Sender.ME)));
            history.revalidate();
            history.repaint();
            SwingUtilities.invokeLater(() -> {
                JScrollBar verticalScrollBar = chatHistoryPanel.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            });
            inputField.setText("");
            chatMessages.add(new ChatMessage(ChatMessageRole.USER.value(), text));
            String currentHtml = "<html><head></head></html>";
            MarkdownSettings settings = MarkdownSettings.getInstance(project);
            settings.setAutoScrollEnabled(false);
            MarkdownHtmlPanelProvider provider = retrievePanelProvider(settings);
            MarkdownHtmlPanel htmlPanel = provider.createHtmlPanel(project, project.getProjectFile());
            htmlPanel.setHtml(currentHtml, 0);
            history.add(htmlPanel.getComponent());
            new Thread(() -> handleChatCompletion(htmlPanel, history)).start();
        });
        chatInputPanel.add(sendButton, gbc);
        return chatInputPanel;
    }

    private EditorTextField createMessagePane(Message message) {
        EditorTextField messagePane = EditorTextFieldProvider.getInstance().getEditorField(Objects.requireNonNull(Language.findLanguageByID("Markdown")), this.project, List.of((editorEx) -> {
            EditorSettings editorSettings = editorEx.getSettings();
            editorSettings.setLineNumbersShown(false);
            editorSettings.setLineMarkerAreaShown(false);
            editorSettings.setFoldingOutlineShown(false);
            editorSettings.setAutoCodeFoldingEnabled(false);
            editorSettings.setShowIntentionBulb(false);
            editorSettings.setBlinkCaret(false);
            editorSettings.setAnimatedScrolling(false);
            editorSettings.setUseSoftWraps(true);
            editorEx.setViewer(true);
            editorEx.setRendererMode(true);
            editorEx.setCaretVisible(false);
            editorEx.setCaretEnabled(false);
            editorEx.putUserData(IncrementalFindAction.SEARCH_DISABLED, Boolean.TRUE);

            Color c = message.getSender() == Sender.ME ?
                    scheme.getColor(EditorColors.DOCUMENTATION_COLOR) :
                    scheme.getColor(EditorColors.READONLY_BACKGROUND_COLOR);
            editorEx.setBackgroundColor(c == null ? scheme.getDefaultBackground() : c);
            editorEx.setColorsScheme(scheme);
        }));
        messagePane.setText(message.getContent());
        return messagePane;
    }


    private @NotNull MarkdownHtmlPanelProvider retrievePanelProvider(@NotNull MarkdownSettings settings) {
        final MarkdownHtmlPanelProvider.ProviderInfo providerInfo = settings.getPreviewPanelProviderInfo();
        MarkdownHtmlPanelProvider provider = MarkdownHtmlPanelProvider.createFromInfo(providerInfo);
        if (provider.isAvailable() != MarkdownHtmlPanelProvider.AvailabilityInfo.AVAILABLE) {
            final MarkdownHtmlPanelProvider defaultProvider = MarkdownHtmlPanelProvider.createFromInfo(MarkdownSettings.getDefaultProviderInfo());
            System.out.println("MarkdownHtmlPanelProvider is not available: " + providerInfo.getName());
            MarkdownSettings.getInstance(project).setPreviewPanelProviderInfo(defaultProvider.getProviderInfo());
            provider = Objects.requireNonNull(
                    ContainerUtil.find(
                            MarkdownHtmlPanelProvider.getProviders(),
                            p -> p.isAvailable() == MarkdownHtmlPanelProvider.AvailabilityInfo.AVAILABLE
                    )
            );
        }
        return provider;
    }

    @RequiresBackgroundThread
    private void handleChatCompletion(MarkdownHtmlPanel htmlPanel, JPanel historyPanel) {
        StringBuilder sb = new StringBuilder();

        QaDesignChatService service = QaDesignChatService.getInstance();

        service.sendMessage(
                chatMessages,
                message -> {
                    ChatCompletionChoice choice = message.getChoices().get(0);
                    String msg = choice.getMessage().getContent();

                    if (null == msg) {
                        return;
                    }
                    sb.append(msg);
                    String html = MarkdownUtil.INSTANCE.generateMarkdownHtml(Objects.requireNonNull(project.getWorkspaceFile()), sb.toString(), project);

                    htmlPanel.setHtml("<html><head></head>" + html + "</html>", 0);
                    historyPanel.revalidate();
                    historyPanel.repaint();
                },
                e -> QaDesignNotifier.notifyError(this.project, e.getMessage())
        );

        chatMessages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(), sb.toString()));
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

}
