package main.java.com.Threads;

import main.java.com.TableManager.PositionTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class PositionsThread extends TableThread {
    public PositionsThread(BlockingQueue<PositionTable> queryQueue) {
        super(queryQueue);
    }

    @Override
    void checkExistingTable() {
        try {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS positions (" +
                    "datetime timestamp without time zone NOT NULL, " +
                    "unitid INTEGER NOT NULL, " +
                    "rdx DOUBLE PRECISION, " +
                    "rdy DOUBLE PRECISION, " +
                    "speed INTEGER, " +
                    "course INTEGER, " +
                    "numsatalites INTEGER, " +
                    "hdop BOOLEAN, " +
                    "quality CHARACTER VARYING, " +
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

        try (PreparedStatement ps = con.prepareStatement("INSERT INTO positions "
                + "(datetime,unitid,rdx,rdy,speed,course,numsatalites,hdop,quality) "
                + "VALUES (?,?,?,?,?,?,?,?,?)")) {
            con.setAutoCommit(false);
            for (Object p : list) {
                PositionTable pTable = (PositionTable) p;

                ps.setTimestamp(1, pTable.getDateTime());
                ps.setInt(2, pTable.getUnitId());
                ps.setDouble(3, pTable.getRdx());
                ps.setDouble(4, pTable.getRdy());
                ps.setInt(5, pTable.getSpeed());
                ps.setInt(6, pTable.getCourse());
                ps.setInt(7, pTable.getNumSatalites());
                ps.setBoolean(8, pTable.getHdop());
                ps.setString(9, pTable.getQuality());
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Inserted position batch in: " + (new Date().getTime() - curDate) + " ms");
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getNextException());
        }
    }
}
