package server.data;

import com.google.gson.Gson;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileData {
    private static final Gson gson = new Gson();
    private static final String PATH = System.getProperty("user.dir") + "/src/server/data/db.json";

    public static Map<String, String> getData() throws IOException {
        File file = new File(PATH);
        if (!file.exists()) {
            file.createNewFile();
            return new ConcurrentHashMap<>();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return gson.fromJson(reader, ConcurrentHashMap.class);
            }
        }
    }

    public static void flush(Map<String, String> data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            bw.write(gson.toJson(data, Map.class));
        }
    }
}
