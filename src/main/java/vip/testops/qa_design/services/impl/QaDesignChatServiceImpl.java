package vip.testops.qa_design.services.impl;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.util.net.HttpConfigurable;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import vip.testops.qa_design.QaDesignNotifier;
import vip.testops.qa_design.services.QaDesignChatService;
import vip.testops.qa_design.utils.CycledList;
import vip.testops.qa_design.utils.SensitiveDataUtil;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.theokanning.openai.service.OpenAiService.*;

public class QaDesignChatServiceImpl implements QaDesignChatService {

    private final CredentialAttributes credentialAttributes = SensitiveDataUtil.createCredentialAttributes("OpenAI Key");
    private OpenAiService openAiService;

    @Override
    public void init() {
        Credentials credentials = PasswordSafe.getInstance().get(credentialAttributes);
        if(credentials == null) {
            QaDesignNotifier.notifyError(null, "Invalid OpenAI APIKEY");
            return;
        }
        String openaiKey = credentials.getPasswordAsString() != null ? credentials.getPasswordAsString() : "";

        // Get the proxy settings from IntelliJ IDEA
        HttpConfigurable httpConfigurable = HttpConfigurable.getInstance();
        Proxy proxy = null;

        if (httpConfigurable.USE_HTTP_PROXY) {
            proxy = new Proxy(
                    Proxy.Type.HTTP,
                    new InetSocketAddress(httpConfigurable.PROXY_HOST, httpConfigurable.PROXY_PORT)
            );
        }

        OkHttpClient client = defaultClient(openaiKey, Duration.ofSeconds(20))
                .newBuilder()
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, defaultObjectMapper());
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        openAiService = new OpenAiService(api, executorService);
    }

    @Override
    public void update() {

    }

    @Override
    public void sendMessage(CycledList<ChatMessage> chatMessages, Consumer<ChatCompletionChunk> blockingForEach, Consumer<? super Throwable> onError) {

        if(chatMessages.getStartCycleIndex() == chatMessages.size()) {
            return;
        }

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(chatMessages.get())
                .n(1)
                .maxTokens(1024)
                .logitBias(new HashMap<>())
                .build();

        openAiService.streamChatCompletion(chatCompletionRequest)
                .doOnError(onError)
                .blockingForEach(blockingForEach);

        if(openAiService != null) {
            openAiService.shutdownExecutor();
        }

    }
}
