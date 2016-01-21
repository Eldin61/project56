package main.java.com;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class TableManager {
    public ConnectionTable generateConnectionTable(int counter) {
        Timestamp t = new Timestamp((new Date().getTime() + counter * 1000));
        boolean value = (counter % 2 == 0);

        return new ConnectionTable(t, counter, "con", value);
    }

    public EventTable generateEventTable(int counter) {
        Timestamp t = new Timestamp((new Date().getTime() + counter * 1000));
        boolean value = (counter % 2 == 0);
        return new EventTable(t, counter, "evt", value);
    }

    public MonitoringTable generateMonitoringTable(int counter) {
        Timestamp tBegin = new Timestamp((new Date().getTime() + counter * 1000));
        Timestamp tEnd = new Timestamp((new Date().getTime() + counter * 1000));
        double rDouble = counter * 1000.5;
        return new MonitoringTable(counter, tBegin, tEnd, "mon", rDouble, rDouble, rDouble);
    }

    public PositionTable generatePositionTable(int counter) {
        Timestamp t = new Timestamp((new Date().getTime() + counter * 1000));
        double rDouble = counter * 1000.5;
        int rInt = counter * 1000;
        boolean value = (counter % 2 == 0);
        return new PositionTable(t, counter, rDouble, rDouble, rInt, rInt, rInt, value, "pos");
    }

    public class ConnectionTable {
        private Timestamp dateTime;
        private int unitId;
        private String port;
        private boolean value;

        public ConnectionTable(Timestamp dateTime, int unitId, String port, boolean value) {
            this.dateTime = dateTime;
            this.unitId = unitId;
            this.port = port;
            this.value = value;
        }


        public Timestamp getDateTime() {
            return dateTime;
        }

        public int getUnitId() {
            return unitId;
        }

        public String getPort() {
            return port;
        }

        public boolean isValue() {
            return value;
        }

        @Override
        public String toString() {
            return dateTime + ", " + unitId + ", " + port + ", " + value;
        }
    }

    public class EventTable {
        final Timestamp dateTime;
        final int unitId;
        final String port;
        final boolean value;

        public EventTable(Timestamp dateTime, int unitId, String port, boolean value) {
            this.dateTime = dateTime;
            this.unitId = unitId;
            this.port = port;
            this.value = value;
        }

        public Timestamp getDateTime() {
            return dateTime;
        }

        public int getUnitId() {
            return unitId;
        }

        public String getPort() {
            return port;
        }

        public boolean isValue() {
            return value;
        }

        @Override
        public String toString() {
            return dateTime + ", " + unitId + ", " + port + ", " + value;
        }
    }

    public class MonitoringTable {
        final int unitId;
        final Timestamp beginTime;
        final Timestamp endTime;
        final String type;
        final double min;
        final double max;
        final double sum;

        public MonitoringTable(int unitId, Timestamp beginTime, Timestamp endTime, String type, double min, double max, double sum) {
            this.unitId = unitId;
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.type = type;
            this.min = min;
            this.max = max;
            this.sum = sum;
        }

        public int getUnitId() {
            return unitId;
        }

        public Timestamp getBeginTime() {
            return beginTime;
        }

        public Timestamp getEndTime() {
            return endTime;
        }

        public String getType() {
            return type;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }

        public double getSum() {
            return sum;
        }

        @Override
        public String toString() {
            return unitId + ", " + beginTime + ", " + endTime + ", " + type + ", " + min + ", " + max + ", " + sum;
        }
    }

    public class PositionTable {
        final Timestamp dateTime;
        final int unitId;
        final double rdx;
        final double rdy;
        final int speed;
        final int course;
        final int numSatalites;
        final boolean hdop;
        final String quality;

        public PositionTable(Timestamp dateTime, int unitId, double rdx, double rdy, int speed, int course, int numSatalites, boolean hdop, String quality) {
            this.dateTime = dateTime;
            this.unitId = unitId;
            this.rdx = rdx;
            this.rdy = rdy;
            this.speed = speed;
            this.course = course;
            this.numSatalites = numSatalites;
            this.hdop = hdop;
            this.quality = quality;
        }

        public Timestamp getDateTime() {
            return dateTime;
        }

        public int getUnitId() {
            return unitId;
        }

        public double getRdx() {
            return rdx;
        }

        public double getRdy() {
            return rdy;
        }

        public int getSpeed() {
            return speed;
        }

        public int getCourse() {
            return course;
        }

        public int getNumSatalites() {
            return numSatalites;
        }

        public boolean getHdop() {
            return hdop;
        }

        public String getQuality() {
            return quality;
        }

        @Override
        public String toString() {
            return dateTime + ", " + unitId + ", " + rdx + ", " + rdy + ", " + speed + ", " + course + ", " + numSatalites + ", " + hdop + ", " + quality;
        }
    }
}
