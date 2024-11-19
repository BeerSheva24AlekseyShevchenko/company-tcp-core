package telran.employees;

import telran.io.Persistable;

public class DataManager implements Runnable {
    private final static int DEFAULT_SAVE_INTERVAL = 60000;
    private final static String DEFAULT_FILE_NAME = "employees.data";

    private int saveInterval;
    private String fileName;
    private Persistable data;

    public DataManager(Persistable data) {
        this(data, DEFAULT_FILE_NAME, DEFAULT_SAVE_INTERVAL);
    }

    public DataManager(Persistable data, String fileName, int saveInterval) {
        this.fileName = fileName;
        this.saveInterval = saveInterval;
        this.data = data;
    }

    @Override
    public void run() {
        data.restoreFromFile(fileName);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> data.saveToFile(fileName)));

        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(saveInterval);
                    data.saveToFile(fileName);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
