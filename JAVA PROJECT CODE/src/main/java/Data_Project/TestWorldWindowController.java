package Data_Project;
import java.sql.*;
import javafx.fxml.FXML;

/**
 * Created by Indi on 5/27/2015.
 */
public class TestWorldWindowController extends Thread {
    
    private static int NUM_OF_THREADS = 10;
    static int testcounter = 0;
    int m_myId;
    static  int c_nextId = 1;
    static  Connection s_conn = null;
    private String[] toppings = {"1"};
    
    
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
    
    public void threadFunction(String mArray[]){
        try{
            //load driver
            Class.forName("org.postgresql.Driver");
     /*       // number of threads 
            if (mArray.length >1){
                System.out.println("Error invalid syntax");
                //System.exit(0);
            }else if (mArray.length == 1){
                NUM_OF_THREADS = Integer.parseInt(mArray[0]);
            }*/
            //make threads
            NUM_OF_THREADS = 5;
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
    public TestWorldWindowController(){
        super();
        m_myId = getNextId();
    }
    
    public void run(){
        // random functie
       connect2db();
    }
    public void connect2db(){
         Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
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
            stmt = conn.createStatement ();
            
            // executes query
             // static sql querys hier
            testcounter = (int)(Math.random() * (1000000 - 0 + 1));
             String sql = "INSERT INTO EVENTS (datetime,unitid,port,value) "
               + "VALUES ('2015-03-10 01:01:01', "+testcounter+", 'Ignition', TRUE);";
          //   testcounter++;
             System.out.println(sql);
             stmt.executeUpdate(sql);
             // Close all the resources
            rs.close();
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
