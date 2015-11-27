/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Project;

import java.sql.Timestamp;

/**
 *
 * @author Administrator
 */
public class TableManager {
    final static int amountCars = 50;

    public TableManager() {
    }
    
    public class Connection {
        Timestamp datetime;
        int unitId;
        String port;
        boolean value;

        public Connection(Timestamp datetime, int unitId, String port, boolean value) {
            this.datetime = datetime;
            this.unitId = unitId;
            this.port = port;
            this.value = value;
        }

        public Timestamp getDatetime() {
            return datetime;
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
    }
    
    public class Event {
        Timestamp datetime;
        int unitId;
        String port;
        boolean value;

        public Event(Timestamp datetime, int unitId, String port, boolean value) {
            this.datetime = datetime;
            this.unitId = unitId;
            this.port = port;
            this.value = value;
        }

        public Timestamp getDatetime() {
            return datetime;
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
    }
    
    public class Monitoring {
        int unitId;
        Timestamp begintime;
        Timestamp endtime;
        String type;
        double min;
        double max;
        double sum;

        public Monitoring(int unitId, Timestamp begintime, Timestamp endtime, String type, double min, double max, double sum) {
            this.unitId = unitId;
            this.begintime = begintime;
            this.endtime = endtime;
            this.type = type;
            this.min = min;
            this.max = max;
            this.sum = sum;
        }

        public int getUnitId() {
            return unitId;
        }

        public Timestamp getBegintime() {
            return begintime;
        }

        public Timestamp getEndtime() {
            return endtime;
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
    }
    
    public class Position {
        Timestamp datetime;
        int unitid;
        double rdx;
        double rdy;
        int speed;
        int course;
        int numsatellites;
        int hdop;
        String quality;

        public Position(Timestamp datetime, int unitid, double rdx, double rdy, int speed, int course, int numsatellites, int hdop, String quality) {
            this.datetime = datetime;
            this.unitid = unitid;
            this.rdx = rdx;
            this.rdy = rdy;
            this.speed = speed;
            this.course = course;
            this.numsatellites = numsatellites;
            this.hdop = hdop;
            this.quality = quality;
        }

        public Timestamp getDatetime() {
            return datetime;
        }

        public int getUnitid() {
            return unitid;
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

        public int getNumsatellites() {
            return numsatellites;
        }

        public int getHdop() {
            return hdop;
        }

        public String getQuality() {
            return quality;
        }
    }
}
