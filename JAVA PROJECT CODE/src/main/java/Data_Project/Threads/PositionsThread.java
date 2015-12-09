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
public class PositionsThread extends Thread {
    private final BlockingQueue<TableManager.Position> positionsQueue;
    private final int batchSize = 1000;
    private Connection con;

    public PositionsThread(BlockingQueue queue) {
        positionsQueue = queue;
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
            List<TableManager.Position> list = new ArrayList<>();
            try {
                while (list.size() < batchSize) {
                    list.add(positionsQueue.take());
                }
                System.out.println(positionsQueue.size());
                saveWithBatchPreparedStatement(list);
            } catch (InterruptedException e) {
                System.out.println("Error occured: " + e);
            }
        }
    }

    private void saveWithBatchPreparedStatement(List<TableManager.Position> names) {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO positions "
                + "(datetime,unitid,rdx,rdy,speed,course,numsatellites,hdop,quality) "
                + "VALUES (?,?,?,?,?,?,?,?,?)")) {
            con.setAutoCommit(false);
            for (TableManager.Position p : names) {
                ps.setTimestamp(1, p.getDatetime());
                ps.setInt(2, p.getUnitid());
                ps.setDouble(3, p.getRdx());
                ps.setDouble(4, p.getRdy());
                ps.setInt(5, p.getSpeed());
                ps.setInt(6, p.getCourse());
                ps.setInt(7, p.getNumsatellites());
                ps.setInt(8, p.getHdop());
                ps.setString(9, p.getQuality());
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Done! At: " + new Date().getTime());
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
