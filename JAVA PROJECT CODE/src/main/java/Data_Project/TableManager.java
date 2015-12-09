/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Project;

import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class TableManager {
    final static int amountCars = 50;

    public TableManager() {

    }

    public Connection generateConnectionTable(int counter) {
        Timestamp t = new Timestamp((new Date().getTime() + counter * 1000));
        boolean value = (counter % 2 == 0);

        return new Connection(t, counter, "con", value);
    }

    public Event generateEventTable(int counter) {
        Timestamp t = new Timestamp((new Date().getTime() + counter * 1000));
        boolean value = (counter % 2 == 0);
        return new Event(t, counter, "evt", value);
    }

    public Monitoring generateMonitoringTable(int counter) {
        Timestamp tBegin = new Timestamp((new Date().getTime() + counter * 1000));
        Timestamp tEnd = new Timestamp((new Date().getTime() + counter * 1000));
        double rDouble = new Random().nextDouble() * 1000;
        return new Monitoring(counter, tBegin, tEnd, "mon", rDouble, rDouble, rDouble);
    }

    public Position generatePositionTable(int counter) {
        Timestamp t = new Timestamp((new Date().getTime() + counter * 1000));
        Random r = new Random();
        double rDouble = r.nextDouble() * 1000;
        int rInt = r.nextInt() * 1000;
        return new Position(t, counter, rDouble, rDouble, rInt, rInt, rInt, rInt, "pos");
    }
    
    public class Connection {
        private Timestamp datetime;
        private int unitId;
        private String port;
        private boolean value;

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
        final Timestamp datetime;
        final int unitId;
        final String port;
        final boolean value;

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
        final int unitId;
        final Timestamp begintime;
        final Timestamp endtime;
        final String type;
        final double min;
        final double max;
        final double sum;

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
        final Timestamp datetime;
        final int unitid;
        final double rdx;
        final double rdy;
        final int speed;
        final int course;
        final int numsatellites;
        final int hdop;
        final String quality;
        
        public Position(){
            
        }

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
    public Timestamp randomDate(){
        //creates a random date between 2015-2016
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        java.util.Date dateFrom = null;
        try {
            dateFrom = dateFormat.parse("2015");
        } catch (ParseException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        long timestampFrom = dateFrom.getTime();
        java.util.Date dateTo = null;
        try {
            dateTo = dateFormat.parse("2016");
        } catch (ParseException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        long timestampTo = dateTo.getTime();
        Random random = new Random();
        long timeRange = timestampTo - timestampFrom;
        long randomTimestamp = timestampFrom + (long) (random.nextDouble() * timeRange);
        return new Timestamp(randomTimestamp);
    }
    
    public int randomUnit(){
        Random rand = null;
        int randomNum = rand.nextInt((99999999 - 15000000) + 1) + 15000000;
        return randomNum;
    }
    
    public boolean randomBoolean(){
        return Math.random() < 0.5;
    }
    
}
