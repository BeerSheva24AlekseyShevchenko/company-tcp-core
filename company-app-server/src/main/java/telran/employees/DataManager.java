package telran.employees;

import telran.io.Persistable;

public class DataManager<T> implements Runnable {
    private final static int DEFAULT_SAVE_INTERVAL = 60000;
    private final static String DEFAULT_FILE_NAME = "employees.data";

    private int saveInterval;
    private String fileName;
    private T data;

    private DataManager(T data) {
        this(data, DEFAULT_FILE_NAME, DEFAULT_SAVE_INTERVAL);
    }

    private DataManager(T data, String fileName, int saveInterval) {
        this.fileName = fileName;
        this.saveInterval = saveInterval;
        this.data = data;
    }

    public static <T> DataManager<T> of(T data) {
        return new DataManager<T>(data);
    }

    public DataManager<T> setSaveInterval(int saveInterval) {
        this.saveInterval = saveInterval;
        return this;
    }

    public DataManager<T> setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public void run() {
        if (data instanceof Persistable) {
            restoreFromFile();
            saveToFilePeriodically().start();
        }
    }

    private void restoreFromFile() {
        ((Persistable) data).restoreFromFile(fileName);
    }

    private void saveToFile() {
        ((Persistable) data).saveToFile(fileName);
    }

    private Thread saveToFilePeriodically() {
        return new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(saveInterval);
                    saveToFile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
}
