package com.mashiro.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class WebSocketImageClient extends TextWebSocketHandler {

    @Resource
    private WebSocketClient webSocketClient;
    //    @Resource
//    private RedisIdWord redisIdWord;
    private WebSocketSession session;
    private final String serverAddress = "127.0.0.1:8188";
    //    private final String clientId = String.valueOf(redisIdWord.nextId("draw"));
    private final String clientId = UUID.randomUUID().toString();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void connect() throws Exception {
        this.session = webSocketClient.doHandshake(this, "ws://" + serverAddress + "/ws?clientId=" + clientId).get();
    }

    private Map<String, Object> queuePrompt(Map<String, Object> prompt) throws IOException {
        prompt.put("client_id", clientId);
        String data = objectMapper.writeValueAsString(prompt);
        HttpURLConnection connection = (HttpURLConnection) new URL("http://" + serverAddress + "/prompt").openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        try (OutputStream os = connection.getOutputStream()) {
            os.write(data.getBytes());
            os.flush();
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return objectMapper.readValue(br, Map.class);
        }
    }

    private byte[] getImage(String filename, String subfolder, String folderType) throws IOException {
        String query = String.format("filename=%s&subfolder=%s&type=%s", filename, subfolder, folderType);
        HttpURLConnection connection = (HttpURLConnection) new URL("http://" + serverAddress + "/view?" + query).openConnection();
        connection.setRequestMethod("GET");
        try (InputStream in = connection.getInputStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return out.toByteArray();
        }
    }

    private Map<String, Object> getHistory(String promptId) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://" + serverAddress + "/history/" + promptId).openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return objectMapper.readValue(br, Map.class);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 解析JSON消息，插入相应的处理逻辑  
        Map<String, Object> data = objectMapper.readValue(message.getPayload(), Map.class);
    }

    public Map<String, Map<String, Object>> getImages(Map<String, Object> prompt) throws Exception {
        String promptId = (String) queuePrompt(prompt).get("prompt_id");
        sendTextMessage("Some Message If Required"); // Adjust if needed, or remove  
        Map<String, Map<String, Object>> outputImages = new HashMap<>();

//        while (true) {
//            // Implement a way to handle and process messages from WebSocket
//            // This could potentially wait for specific messages
//        }
//
//        Map<String, Object> history = getHistory(promptId);
//        for (Object nodeId : history.keySet()) {
//            // Implement image retrieval similar to the Python logic
//        }

        return outputImages;
    }

    private void sendTextMessage(String message) throws Exception {
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}