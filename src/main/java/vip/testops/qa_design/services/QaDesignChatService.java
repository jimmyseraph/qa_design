package vip.testops.qa_design.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.util.concurrency.annotations.RequiresBackgroundThread;
import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatMessage;
import io.reactivex.functions.Consumer;
import vip.testops.qa_design.utils.CycledList;

public interface QaDesignChatService {
    static QaDesignChatService getInstance() {
        QaDesignChatService service = ApplicationManager.getApplication().getService(QaDesignChatService.class);
        service.init();
        return service;
    }

    void init();

    @RequiresBackgroundThread
    void update();


    @RequiresBackgroundThread
    void sendMessage(CycledList<ChatMessage> chatMessages, Consumer<ChatCompletionChunk> blockingForEach, Consumer<? super Throwable> onError);
}
