package com.example.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtility {
    private static HttpURLConnection urlConnection;

    public static String doGet(String urlString) throws IOException {

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            StringBuilder content;
            try (BufferedReader webpage = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()))) {

                String webpage_line;
                content = new StringBuilder();

                while ((webpage_line = webpage.readLine()) != null) {

                    content.append(webpage_line);
                    content.append(System.lineSeparator());
                }
            }
            return content.toString();

        } finally {
            urlConnection.disconnect();
        }
    }

    public static HashMap<String, Object> doGetMap(String urlString) throws IOException {
        var resultString = doGet(urlString);
        var mapper = new ObjectMapper();
        var typeRef = new TypeReference<HashMap<String, Object>>() {
        };

        HashMap<String, Object> json = new HashMap<String, Object>();

        return mapper.readValue(resultString, typeRef);        
    }
}
