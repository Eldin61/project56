package main.java.com.Threads;

import main.java.com.TableManager.EventTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class EventsThread extends TableThread {
    public EventsThread(BlockingQueue queryQueue) {
        super(queryQueue);
    }

    @Override
    void checkExistingTable() {
        try {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS events (" +
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

        try (PreparedStatement ps = con.prepareStatement("INSERT INTO events (datetime,unitid,port,value) VALUES (?,?,?,?)")) {
            con.setAutoCommit(false);
            for (Object c : list) {
                EventTable eTable = (EventTable) c;

                ps.setTimestamp(1, eTable.getDateTime());
                ps.setInt(2, eTable.getUnitId());
                ps.setString(3, eTable.getPort());
                ps.setBoolean(4, eTable.isValue());
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Inserted event batch in: " + (new Date().getTime() - curDate) + " ms");
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
