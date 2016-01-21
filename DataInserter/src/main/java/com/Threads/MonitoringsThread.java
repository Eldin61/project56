package main.java.com.Threads;

import main.java.com.TableManager.MonitoringTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MonitoringsThread extends TableThread {
    public MonitoringsThread(BlockingQueue<MonitoringTable> queryQueue) {
        super(queryQueue);
    }

    @Override
    void checkExistingTable() {
        try {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS monitoring (" +
                    "unitid INTEGER NOT NULL, " +
                    "begintime timestamp without time zone NOT NULL, " +
                    "endtime timestamp without time zone NOT NULL, " +
                    "type CHARACTER VARYING NOT NULL , " +
                    "min INTEGER, " +
                    "max INTEGER, " +
                    "sum INTEGER, " +
                    "PRIMARY KEY(unitid, begintime, endtime, type))");
            ps.executeUpdate();
            ps.closeOnCompletion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    void insertPreparedStatementBatch(List list) {
        Long curDate = new Date().getTime();

        try (PreparedStatement ps = con.prepareStatement("INSERT INTO monitoring (unitid,begintime,endtime,type,min,max,sum) VALUES (?,?,?,?,?,?,?)")) {
            con.setAutoCommit(false);
            for (Object m : list) {
                MonitoringTable mTable = (MonitoringTable) m;

                ps.setInt(1, mTable.getUnitId());
                ps.setTimestamp(2, mTable.getBeginTime());
                ps.setTimestamp(3, mTable.getEndTime());
                ps.setString(4, mTable.getType());
                ps.setDouble(5, mTable.getMin());
                ps.setDouble(6, mTable.getMax());
                ps.setDouble(7, mTable.getSum());
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Inserted monitoring batch in: " + (new Date().getTime() - curDate) + " ms");
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
