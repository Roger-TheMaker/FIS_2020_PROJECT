package Encryption;

import Exceptions.Empty_String;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MD5_StringTest {

    MD5 test;
    @Before
    public void setUp() throws Exception {
        test=new MD5();
    }

    @After
    public void tearDown() throws Exception {
        test=null;
    }

    @Test(expected = Empty_String.class)
    public void getMd5() {
        MD5.getMd5("");
    }
}