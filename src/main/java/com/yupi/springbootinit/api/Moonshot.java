package com.yupi.springbootinit.api;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


enum ChatMessageRole {
    SYSTEM, USER, ASSISTANT;

    public String value() {
        return this.name().toLowerCase();
    }
}

class ChatCompletionMessage {
    public String role;
    public String name;
    public String content;
    public Boolean partial;

    public ChatCompletionMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public ChatCompletionMessage(String role, String name, String content, Boolean partial) {
        this.role = role;
        this.name = name;
        this.content = content;
        this.partial = partial;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Boolean getPartial() {
        return partial;
    }
}

class ChatCompletionStreamChoiceDelta {
    private String content;
    private String role;

    public String getContent() {
        return content;
    }

    public String getRole() {
        return role;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ChatCompletionStreamChoiceDelta(String content, String role) {
        this.content = content;
        this.role = role;
    }
}

class Usage {
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;

    public int getPromptTokens() {
        return promptTokens;
    }

    public int getCompletionTokens() {
        return completionTokens;
    }

    public int getTotalTokens() {
        return totalTokens;
    }
}

class ChatCompletionStreamChoice {
    private int index;
    private ChatCompletionStreamChoiceDelta delta;

    @SerializedName("finish_reason")
    private String finishReason;
    private Usage usage;

    public int getIndex() {
        return index;
    }

    public ChatCompletionStreamChoiceDelta getDelta() {
        return delta;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setDelta(ChatCompletionStreamChoiceDelta delta) {
        this.delta = delta;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public ChatCompletionStreamChoice(int index, ChatCompletionStreamChoiceDelta delta, String finishReason, Usage usage) {
        this.index = index;
        this.delta = delta;
        this.finishReason = finishReason;
        this.usage = usage;
    }
}

class ChatCompletionStreamResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatCompletionStreamChoice> choices;

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public long getCreated() {
        return created;
    }

    public String getModel() {
        return model;
    }

    public List<ChatCompletionStreamChoice> getChoices() {
        return choices;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}

class ChatCompletionChoice {
    private int index;
    private ChatCompletionMessage message;

    @SerializedName("finish_reason")
    private String finishReason;

    public int getIndex() {
        return index;
    }

    public ChatCompletionMessage getMessage() {
        return message;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setMessage(ChatCompletionMessage message) {
        this.message = message;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

}

class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatCompletionChoice> choices;
    private Usage usage;

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public long getCreated() {
        return created;
    }

    public String getModel() {
        return model;
    }

    public List<ChatCompletionChoice> getChoices() {
        if (choices == null) {
            return Collections.emptyList();
        }
        return choices;
    }
}


class ChatCompletionRequest {
    public String model;
    public List<ChatCompletionMessage> messages;

    @SerializedName("max_tokens")
    public int maxTokens;

    @SerializedName("temperature")
    public float temperature;
    public float topP;

    public Integer n;
    public boolean stream;
    public List<String> stop;

    @SerializedName("presence_penalty")
    public float presencePenalty;

    @SerializedName("frequency_penalty")
    public float frequencyPenalty;

    public String user;

    public List<ChatCompletionMessage> getMessages() {
        return messages;
    }

    public ChatCompletionRequest(String model, List<ChatCompletionMessage> messages, int maxTokens, float temperature, int n) {
        this.model = model;
        this.messages = messages;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.n = n;
    }

}

class Model {
    private String id;
    private String object;

    @SerializedName("owner_by")
    private String ownedBy;
    private String root;
    private String parent;

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public String getRoot() {
        return root;
    }

    public String getParent() {
        return parent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Model(String id, String object, String ownedBy, String root, String parent) {
        this.id = id;
        this.object = object;
        this.ownedBy = ownedBy;
        this.root = root;
        this.parent = parent;
    }
}

class ModelsList {
    private List<Model> data;

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }

    public ModelsList(List<Model> data) {
        this.data = data;
    }
}

class Client {
    private static final String DEFAULT_BASE_URL = "https://api.moonshot.cn/v1";

    private static final String CHAT_COMPLETION_SUFFIX = "/chat/completions";
    private static final String MODELS_SUFFIX = "/models";
    private static final String FILES_SUFFIX = "/files";

    private String baseUrl;
    private String apiKey;

    public Client(String apiKey) {
        this(apiKey, DEFAULT_BASE_URL);
    }

    public Client(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        this.baseUrl = baseUrl;
    }

    public String getChatCompletionUrl() {
        return baseUrl + CHAT_COMPLETION_SUFFIX;
    }

    public String getModelsUrl() {
        return baseUrl + MODELS_SUFFIX;
    }

    public String getFilesUrl() {
        return baseUrl + FILES_SUFFIX;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ModelsList ListModels() throws IOException {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(getModelsUrl())
                .addHeader("Authorization", "Bearer " + getApiKey())
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            String body = response.body().string();
            Gson gson = new Gson();
            return gson.fromJson(body, ModelsList.class);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public ChatCompletionResponse ChatCompletion(ChatCompletionRequest request) throws IOException {
        request.stream = false;
        // 创建 OkHttpClient 实例并设置超时
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS) // 连接超时
                .readTimeout(55, TimeUnit.SECONDS)    // 读取超时
                .writeTimeout(55, TimeUnit.SECONDS)   // 写入超时
                .callTimeout(120, TimeUnit.SECONDS)    // 整个请求过程的超时
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, new Gson().toJson(request));
        okhttp3.Request httpRequest = new okhttp3.Request.Builder()
                .url(getChatCompletionUrl())
                .addHeader("Authorization", "Bearer " + getApiKey())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        try {
            okhttp3.Response response = client.newCall(httpRequest).execute();
            String responseBody = response.body().string();
            Gson gson = new Gson();
            return gson.fromJson(responseBody, ChatCompletionResponse.class);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            throw e;
        }

    }
}

public  class Moonshot {
    private static final Logger log = LoggerFactory.getLogger(Moonshot.class);

    public static String  doChat(String apiKey, String message) {

        Client client = new Client(apiKey);

        final List<ChatCompletionMessage> messages = Arrays.asList(
                new ChatCompletionMessage(ChatMessageRole.SYSTEM.value(),
                        "你是数据分析师，接下来我会给你我的分析目标和原始数据，请告诉我分析结论\n" +
                                "用户输入：\n" +
                                "分析目标：xxxx\n" +
                                "原始数据{CSV格式，以逗号隔开}\n" +
                                "要求输出格式：\n" +
                                "{Echarts 的 option 配置对象的 json 代码,要求精准 Echats 代码，json 格式带上双引号,以5个【【【【【符号分割option代码与结论.以下代码为示例，不是标准.要求有图例 legend}\n" +
                                     "{\n" +
                                     "  \"xAxis\": {\n" +
                                     "    \"type\": \"category\",\n" +
                                     "    \"data\": [\"1号\", \"2号\", \"3号\"]\n" +
                                     "  },\n" +
                                     "  \"yAxis\": {\n" +
                                     "    \"type\": \"value\"\n" +
                                     "  },\n" +
                                     "  \"series\": [\n" +
                                     "    {\n" +
                                     "      \"stack\": \"总量\",\n" +
                                     "      \"name\": \"用户数\",\n" +
                                     "      \"type\": \"bar\",\n" +
                                     "      \"data\": [10, 20, 30]\n" +
                                     "    }\n" +
                                     "  ]\n" +
                                     "}"+
                                "【【【【【\n"
                                +"确保符号内代码片段干净整洁无其它特殊字符。"+
                                "结论：{精炼总结，描述流畅，不少于50字}"),
                new ChatCompletionMessage(ChatMessageRole.USER.value(),
                       message )
        );

        try {
            ChatCompletionResponse response = client.ChatCompletion(new ChatCompletionRequest(
                    "moonshot-v1-128k",
                    messages,
                    1500,
                    0.3f,
                    1
            ));
            return response.getChoices().get(0).getMessage().getContent();
        } catch (IOException e) {
           log.error("调用 AI 错误",e);
           return "";
        }
    }
}