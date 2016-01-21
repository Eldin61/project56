package main.java.com;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import main.java.com.TableManager.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import static org.testng.Assert.*;

public class TableManagerTest {
    private int testCounter;

    @BeforeMethod
    public void setUp() throws Exception {
        testCounter = new Random().nextInt() * 1000;
    }

    @AfterMethod
    public void tearDown() throws Exception {
        testCounter = 0;
    }

    @Test
    public void testGenerateConnectionTable() throws Exception {
        ConnectionTable generatedTable = new TableManager().generateConnectionTable(testCounter);
        ConnectionTable expectedTable = new TableManager().new ConnectionTable(new Timestamp(new Date().getTime() + testCounter * 1000), testCounter, "con", (testCounter % 2 == 0));
        Assert.assertEquals(generatedTable.toString(), expectedTable.toString(), "Tested generateConnectionTable.");
    }

    @Test
    public void testGenerateEventTable() throws Exception {
        EventTable generatedTable = new TableManager().generateEventTable(testCounter);
        Assert.assertEquals(generatedTable, generatedTable, "Tested generateEventTable.");
    }

    @Test
    public void testGenerateMonitoringTable() throws Exception {
        MonitoringTable generatedTable = new TableManager().generateMonitoringTable(testCounter);
        Assert.assertEquals(generatedTable, generatedTable, "Tested generateMonitoringTable.");
    }

    @Test
    public void testGeneratePositionTable() throws Exception {
        PositionTable generatedTable = new TableManager().generatePositionTable(testCounter);
        Assert.assertEquals(generatedTable, generatedTable, "Tested generatePositionTable.");
    }
}