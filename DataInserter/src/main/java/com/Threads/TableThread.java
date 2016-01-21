package main.java.com.Threads;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public abstract class TableThread extends Thread {
    private final int batchSize = 100;
    private final BlockingQueue queryQueue;
    List<Object> list;
    Connection con;

    protected TableThread(BlockingQueue queryQueue) {
        this.queryQueue = queryQueue;
        list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/citygis","postgres", "forthe12");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    abstract void checkExistingTable();

    @Override
    public void run() {
        checkExistingTable();

        while (true) {
            list.clear();
            try {
                while (list.size() < batchSize) {
                    list.add(queryQueue.take());
                }
                insertPreparedStatementBatch(list);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    abstract void insertPreparedStatementBatch(List list);
}
