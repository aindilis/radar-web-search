/*
 * HashtableUtilsTest.java
 * JUnit based test
 *
 * Created on October 17, 2004, 5:12 PM
 */

package com.hrstc.utils;

import java.util.*;
import junit.framework.*;

/**
 *
 * @author wani
 */
public class HashtableUtilsTest extends TestCase
{
    
    public HashtableUtilsTest(java.lang.String testName)
    {
        super(testName);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(HashtableUtilsTest.class);
        return suite;
    }
    
    /**
     * Test of hashtableToString method, of class com.hrstc.utils.HashtableUtils.
     */
    public void testHashtableToString()
    {
        Hashtable h = new Hashtable();
        h.put( "key1",  "val1" );
        h.put( "key2",  "val2" );
        h.put( "key3",  "val3" );
        assertEquals( "val1 val3", HashtableUtils.hashtableToString( h, "key1 key3" ) );        
    }
    
    // TODO add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
    
}
