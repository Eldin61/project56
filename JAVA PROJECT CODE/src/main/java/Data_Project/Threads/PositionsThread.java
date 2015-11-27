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
public class PositionsThread extends Thread {
    final BlockingQueue<TableManager.Position> positionsQueue;
    Connection con;

    public PositionsThread(BlockingQueue queue) {
        positionsQueue = queue;
        try {
            this.con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "root");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionsThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            List<TableManager.Position> list = new ArrayList<>();
            try {
                while (list.size() < 10) {
                    list.add(positionsQueue.take());
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

    public void saveWithBatchPreparedStatement(List<TableManager.Position> names) throws UnexpectedException {
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
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
