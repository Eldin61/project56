package Data_Project;

import Data_Project.Threads.ConnectionsThread;
import Data_Project.Threads.EventsThread;
import Data_Project.Threads.MonitoringThread;
import Data_Project.Threads.PositionsThread;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;

/**
 * Created by Indi on 5/27/2015.
 */
public class TestWorldWindowController extends Thread {
    final BlockingQueue<TableManager.Connection> connectionsQueue;
    final BlockingQueue<TableManager.Event> eventsQueue;
    final BlockingQueue<TableManager.Monitoring> monitoringQueue;
    final BlockingQueue<TableManager.Position> positionsQueue;
    
    private static int NUM_OF_THREADS = 10;
    static int testcounter = 0;
    int m_myId;
    static  int c_nextId = 1;
    static  Connection s_conn = null;
    private String[] toppings = {"1"};
    
    public TestWorldWindowController(){
        super();
        this.connectionsQueue = new LinkedBlockingQueue<>();
        //nonblockingqueue
        this.eventsQueue = new LinkedBlockingQueue<>();
        this.monitoringQueue = new LinkedBlockingQueue<>();
        this.positionsQueue = new LinkedBlockingQueue<>();
        m_myId = getNextId();
    }
    
    synchronized static int getNextId(){
        return c_nextId++;
    }
    
    @FXML
    public void Back() {
        fxmlController logout = new fxmlController();
        logout.setLogin("Log In", "/ApplicationMenuWindow.fxml");
    }
    @FXML
    public void threadTest(){
        threadFunction(toppings);
    }
    
    private void initThreads() {
        new ConnectionsThread(connectionsQueue).start();
        new EventsThread(eventsQueue).start();
        new MonitoringThread(monitoringQueue).start();
        new PositionsThread(positionsQueue).start();
    }
    
    /**
     * Alternative threading.
     */
    private void threadFunction() {
        
    }
    
    public void threadFunction(String mArray[]){
        try{
            //load driver
            Class.forName("org.postgresql.Driver");
            //make threads
            NUM_OF_THREADS = 1;
            Thread[] threadList = new Thread[NUM_OF_THREADS];
            
            //spawn threads 
            // i counter voor num of threads
            for (int i = 0; i < NUM_OF_THREADS; i++)
            {
                threadList[i] = new TestWorldWindowController();
                threadList[i].start();
            }
            // wacht tot alle threads aangemaakt zijn
            for(int i = 0;i< NUM_OF_THREADS;i++){
                threadList[i].join();
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void run(){
        Timestamp rDate = null;
        
       //gets random date
        try {
            rDate = randomDate();
        } catch (ParseException ex) {
            Logger.getLogger(TestWorldWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
       //gets random id
       int rValue = (int)(Math.random() * (1000000 - 0 + 1));
       connect2db(rDate, rValue);
    }
    public Timestamp randomDate() throws ParseException{
        //creates a random date between 2015-2016
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        java.util.Date dateFrom = dateFormat.parse("2015");
        long timestampFrom = dateFrom.getTime();
        java.util.Date dateTo = dateFormat.parse("2016");
        long timestampTo = dateTo.getTime();
        Random random = new Random();
        long timeRange = timestampTo - timestampFrom;
        long randomTimestamp = timestampFrom + (long) (random.nextDouble() * timeRange);
        return new Timestamp(randomTimestamp);
        
    }
    
    public void connect2db(Timestamp date, int value){
         Connection conn = null;
        try{
            // connect
            conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres","postgres", "root");
            // jdbc = driver
            // postgresql = type
            // localhost : 5432 = IPaddress:port
            //postgres (1) = database naam
            // postgres (2) = username
            // root = password
            
            // create statement
            PreparedStatement stmt = conn.prepareStatement (
            "INSERT INTO EVENTS (datetime,unitid,port,value) VALUES (?, ?, 'Ignition', TRUE)");
            stmt.setTimestamp(1, date);
            stmt.setInt(2, value);
            // executes query
            // static sql querys hier          
//             String sql = "INSERT INTO EVENTS (datetime,unitid,port,value) "
//               + "VALUES ("+date+", "+testcounter+", 'Ignition', TRUE);";
          //   testcounter++;
             System.out.println(stmt);
             stmt.executeUpdate();
             
             // Close all the resources
              stmt.close();
            if (conn != null){
                conn.close();
            System.out.println("Thread " + m_myId +  " is finished. ");
            }
        }catch(Exception e){
            System.out.println("Thread" + m_myId + "Got Exception :" + e);
            e.printStackTrace();
            return;
        }
    }
}
