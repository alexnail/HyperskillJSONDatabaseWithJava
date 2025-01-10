package server.data;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JsonDB {

    private static final String PATH = System.getProperty("user.dir") + "/src/server/data/db.json";

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static JsonObject root = new JsonObject();

    public void save(Object key, JsonElement element) {
        load();
        save(getPath(key), element);
        flush();
    }

    private void save(JsonArray path, JsonElement element) {
        JsonElement current = root, prev = null;
        String key = null;
        for (JsonElement jsonElement : path) {
            key = jsonElement.getAsString();
            if (!current.getAsJsonObject().has(key)) {
                current.getAsJsonObject().add(key, new JsonObject());
            }
            prev = current;
            current = current.getAsJsonObject().get(key);
        }

        if (prev instanceof JsonObject prevObject && prevObject.has(key)) {
            prevObject.add(key, element);
        } else {
            System.err.println("Prev element is of type " + prev.getClass() + " and its processing hasn't been implemented yet.");
        }
    }

    public Object remove(Object key) {
        load();
        var removed = remove(getPath(key));
        if (removed != null) {
            flush();
        }
        return removed;
    }

    private Object remove(JsonArray path) {
        JsonElement current = root, prev = null;
        String key = null;
        for (JsonElement jsonElement : path) {
            key = jsonElement.getAsString();
            if (!current.getAsJsonObject().has(key)) {
                return null;
            }
            prev = current;
            current = current.getAsJsonObject().get(key);
        }
        if (prev instanceof JsonObject prevObject && prevObject.has(key)) {
            prevObject.remove(key);
            if (current instanceof JsonObject currentObject) {
                return currentObject;
            } else if (current instanceof JsonPrimitive currentPrimitive) {
                return currentPrimitive.getAsString();
            } else {
                return null;
            }
        } else {
            System.err.println("Prev element is of type " + prev.getClass() + " and its processing hasn't been implemented yet.");
            return null;
        }
    }

    public Object get(Object key) {
        load();
        return get(getPath(key));
    }

    private Object get(JsonArray path) {
        JsonElement current = root;
        String key;
        for (JsonElement jsonElement : path) {
            key = jsonElement.getAsString();
            if (!current.getAsJsonObject().has(key)) {
                return null;
            }
            current = current.getAsJsonObject().get(key);
        }
        return switch (current) {
            case JsonObject obj -> obj;
            case JsonPrimitive prim -> prim.getAsString();
            default -> throw new IllegalArgumentException("Unsupported JsonElement type: " + current.getClass());
        };
    }

    private static JsonArray getPath(Object key) {
        JsonArray path = new JsonArray();
        switch (key) {
            case String strKey -> path.add(strKey);
            case JsonArray arrayKey -> path = arrayKey;
            case ArrayList arrayListKey -> {
                for (Object s : arrayListKey) {
                    path.add((String) s);
                }
            }
            case null, default ->
                    throw new IllegalArgumentException("Couldn't transform type [" + key.getClass() + "] to JsonArray");
        }
        return path;
    }

    private void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            lock.readLock().lock();
            var read = new Gson().fromJson(reader, JsonObject.class);
            root = read == null ? new JsonObject() : read;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }

    private void flush() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
            lock.writeLock().lock();
            var json = new Gson().toJson(root);
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
