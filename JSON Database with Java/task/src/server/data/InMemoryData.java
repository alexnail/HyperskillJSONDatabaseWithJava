package server.data;

import java.util.HashMap;
import java.util.Map;

public class InMemoryData {
    private static final Map<Integer, String> data = new HashMap<>();

    public static Map<Integer, String> getData() {
        return data;
    }
}
