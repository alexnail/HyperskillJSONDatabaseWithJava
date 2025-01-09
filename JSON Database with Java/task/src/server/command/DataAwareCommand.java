package server.command;

import server.data.FileData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class DataAwareCommand extends BaseCommand {
    private final static Map<String, String> data;
    static {
        try {
            data = FileData.getData();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data", e);
        }
    }

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public DataAwareCommand(DataOutputStream outputStream) {
        super(outputStream);
    }

    public void save(String key, String value) {
        try {
            lock.writeLock().lock();
            data.put(key, value);
            FileData.flush(data);
        } catch (IOException e) {
            System.err.println("Failed to save data");
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String remove(String key) {
        String value = null;
        try {
            lock.writeLock().lock();
            value = data.remove(key);
            if (value != null) {
                FileData.flush(data);
            }
        } catch (IOException e) {
            System.err.println("Failed to save data");
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
        return value;
    }

    public String get(String key) {
        try {
            lock.readLock().lock();
            return data.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
}

