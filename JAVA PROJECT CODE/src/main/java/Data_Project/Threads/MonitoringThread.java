/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Project.Threads;

import Data_Project.TableManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class MonitoringThread extends Thread {
    private final BlockingQueue<TableManager.Monitoring> monitoringQueue;
    private final int batchSize = 1000;
    private Connection con;

    public MonitoringThread(BlockingQueue queue) {
        monitoringQueue = queue;
        try {
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "forthe12");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionsThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        System.out.println("Starting thread at: " + new Date().getTime());
        while (true) {
            List<TableManager.Monitoring> list = new ArrayList<>();
            try {
                while (list.size() < batchSize) {
                    list.add(monitoringQueue.take());
                }
                System.out.println(monitoringQueue.size());
                saveWithBatchPreparedStatement(list);
            } catch (InterruptedException e) {
                System.out.println("Error occured: " + e);
            }
        }
    }

    private void saveWithBatchPreparedStatement(List<TableManager.Monitoring> names) {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO monitoring (unitid,begintime,endtime,type,min,max,sum) VALUES (?,?,?,?,?,?,?)")) {
            con.setAutoCommit(false);
            for (TableManager.Monitoring m : names) {
                ps.setInt(1, m.getUnitId());
                ps.setTimestamp(2, m.getBegintime());
                ps.setTimestamp(3, m.getEndtime());
                ps.setString(4, m.getType());
                ps.setDouble(5, m.getMin());
                ps.setDouble(6, m.getMax());
                ps.setDouble(7, m.getSum());
                ps.addBatch();
            }
            ps.executeBatch();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
