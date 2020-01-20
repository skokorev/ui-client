package ru.scrumtrek.uiclient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Client {
    private String url;
    private ObjectMapper mapper;

    public Client(String url) {
        this.url = url;
        this.mapper = new ObjectMapper();
    }

    public List<String> getLinesChars() throws IOException {
        URL uri = new URL(url + "/lines");
        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
        connection.setRequestMethod("GET");
        int responceCode = connection.getResponseCode();
        if (HttpURLConnection.HTTP_OK != responceCode) throw new IOException("Can't connect");
        byte[] contents = IOUtils.toByteArray(connection.getInputStream());
        JsonNode node = mapper.readTree(contents);
        List<String> lines = new LinkedList<>();
        for(JsonNode line : node) {
            lines.add(line.get("lineChars").textValue());
        }
        return lines;
    }
}
