package main.java.com;

import main.java.com.TableManager.ConnectionTable;
import main.java.com.TableManager.EventTable;
import main.java.com.TableManager.MonitoringTable;
import main.java.com.TableManager.PositionTable;
import main.java.com.Threads.ConnectionsThread;
import main.java.com.Threads.EventsThread;
import main.java.com.Threads.MonitoringsThread;
import main.java.com.Threads.PositionsThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TableThreadManager {
    private final BlockingQueue<ConnectionTable> connectionTableQueue;
    private final BlockingQueue<EventTable> eventTableQueue;
    private final BlockingQueue<MonitoringTable> monitoringTableQueue;
    private final BlockingQueue<PositionTable> positionTableQueue;

    public TableThreadManager() {
        connectionTableQueue = new LinkedBlockingQueue<>();
        eventTableQueue = new LinkedBlockingQueue<>();
        monitoringTableQueue = new LinkedBlockingQueue<>();
        positionTableQueue = new LinkedBlockingQueue<>();

        //initThreads();
    }

    private void initThreads() {
        new ConnectionsThread(connectionTableQueue).start();
        new EventsThread(eventTableQueue).start();
        new MonitoringsThread(monitoringTableQueue).start();
        new PositionsThread(positionTableQueue).start();
    }
}
