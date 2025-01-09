package server.data;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileData {
    private static final String PATH = System.getProperty("user.dir") + "/src/server/data/db.json";

    public static Map<String, String> getData() throws IOException {
        Gson gson = new Gson();
        File file = new File(PATH);
        if (!file.exists()) {
            file.createNewFile();
            return new HashMap<>();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return gson.fromJson(reader, Map.class);
            }
        }
    }
}
