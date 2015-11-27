/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Project.Threads;

import Data_Project.TableManager;
import java.rmi.UnexpectedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class EventsThread extends Thread {
    final BlockingQueue<TableManager.Event> eventsQueue;
    Connection con;

    public EventsThread(BlockingQueue queue) {
        eventsQueue = queue;
        try {
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "root");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionsThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            List<TableManager.Event> list = new ArrayList<>();
            try {
                while (list.size() < 10) {
                    list.add(eventsQueue.take());
                }
                System.out.println(list);
                saveWithBatchPreparedStatement(list);
            } catch (InterruptedException e) {
                System.out.println("Error occured: " + e);
            } catch (UnexpectedException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveWithBatchPreparedStatement(List<TableManager.Event> names) throws UnexpectedException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO events (datetime,unitid,port,value) VALUES (?,?,?,?)")) {
            con.setAutoCommit(false);
            for (TableManager.Event e : names) {
                ps.setTimestamp(1, e.getDatetime());
                ps.setInt(2, e.getUnitId());
                ps.setString(3, e.getPort());
                ps.setBoolean(4, e.isValue());
                ps.addBatch();
            }
            ps.executeBatch();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
