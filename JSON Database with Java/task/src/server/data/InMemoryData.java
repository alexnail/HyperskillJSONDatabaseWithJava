package server.data;

import java.util.HashMap;
import java.util.Map;

public class InMemoryData {
    private static final Map<String, String> data = new HashMap<>();

    public static Map<String, String> getData() {
        return data;
    }
}
