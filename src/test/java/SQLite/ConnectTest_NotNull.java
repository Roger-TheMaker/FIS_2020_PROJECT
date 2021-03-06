package SQLite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConnectTest_NotNull {

    Connect test;
    @Before
    public void setUp() throws Exception {
        test=new Connect();
    }

    @After
    public void tearDown() throws Exception {
        test = null;
    }

    @Test
    public void connect() {

        //Connection expected;
        assertNotNull(Connect.connect("test.db"));

    }
}