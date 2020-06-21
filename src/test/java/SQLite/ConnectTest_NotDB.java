package SQLite;

import Exceptions.Not_A_DB_File;

import static org.junit.Assert.*;

public class ConnectTest_NotDB {

    Connect test;

    @org.junit.Before
    public void setUp() throws Exception {
        test = new Connect();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        test =null;
    }

    @org.junit.Test(expected = Not_A_DB_File.class)
    public void connect() {

        Connect.connect("something.json");
    }


}