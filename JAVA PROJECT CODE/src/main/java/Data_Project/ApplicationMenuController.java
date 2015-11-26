package Data_Project;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class ApplicationMenuController  {

    @FXML
    public void Function1() {
        fxmlController logout = new fxmlController();
        logout.setLogin("Log In", "/TestWorldWindow.fxml");    }

    @FXML
    public void Function2() throws SQLException {
        Connection c = null;
       Statement stmt;
       try{
           
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres","postgres", "root");
         
            stmt = c.createStatement();
            // static sql querys hier
            
             String sql = "INSERT INTO EVENTS (datetime,unitid,port,value) "
               + "VALUES ('2015-03-10 01:01:01', 123, 'Ignition', TRUE);";
             
             stmt.executeUpdate(sql);
         
           // einde static sql..
        }
       catch(Exception e){
            e.getStackTrace();
       }
       finally{
                c.close();
    }
    }
    
    
    public void data() throws SQLException{
      
        
       
        
    }
    
   
    
    
    
}
