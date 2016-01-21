package main.java.com.Threads;

import main.java.com.TableManager.ConnectionTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ConnectionsThread extends TableThread {

    public ConnectionsThread(BlockingQueue<ConnectionTable> queryQueue) {
        super(queryQueue);
    }

    @Override
    void checkExistingTable() {
        try {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS connections (" +
                    "datetime timestamp without time zone NOT NULL, " +
                    "unitid INTEGER NOT NULL, " +
                    "port CHARACTER VARYING, " +
                    "value boolean, " +
                    "PRIMARY KEY(datetime, unitid))");
            ps.executeUpdate();
            ps.closeOnCompletion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    void insertPreparedStatementBatch(List list) {
        Long curDate = new Date().getTime();

        try (PreparedStatement ps = con.prepareStatement("INSERT INTO connections (datetime,unitid,port,value) VALUES (?,?,?,?)")) {
            con.setAutoCommit(false);
            for (Object c : list) {
                ConnectionTable cTable = (ConnectionTable) c;

                ps.setTimestamp(1, cTable.getDateTime());
                ps.setInt(2, cTable.getUnitId());
                ps.setString(3, cTable.getPort());
                ps.setBoolean(4, cTable.isValue());
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Inserted connection batch in: " + (new Date().getTime() - curDate) + " ms");
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
