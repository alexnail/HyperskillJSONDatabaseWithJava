package server.command;

import server.data.JsonDB;

import java.io.DataOutputStream;

public abstract class DataAwareCommand extends BaseCommand {
    private final static JsonDB DB = new JsonDB();

    public DataAwareCommand(DataOutputStream outputStream) {
        super(outputStream);
    }

    public void save(Object key, Object value) {
        DB.save(key, gson.toJsonTree(value));
    }

    public Object remove(Object key) {
        return DB.remove(key);
    }

    public Object get(Object key) {
        return DB.get(key);
    }
}

