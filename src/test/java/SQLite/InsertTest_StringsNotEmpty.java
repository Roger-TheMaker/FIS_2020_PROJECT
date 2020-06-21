package SQLite;

import Exceptions.Empty_String;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsertTest_StringsNotEmpty {

    Insert test;
    @Before
    public void setUp() throws Exception {
        test =new Insert();
    }

    @After
    public void tearDown() throws Exception {
        test=null;
    }

    @Test(expected = Empty_String.class)
    public void insert() {
            Insert.Insert("test.db","","","");
    }
}