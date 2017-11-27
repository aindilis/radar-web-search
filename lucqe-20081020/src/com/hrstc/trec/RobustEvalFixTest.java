
package com.hrstc.trec;

import java.io.*;
import java.util.*;
import com.hrstc.utils.*;
import junit.framework.*;

/**
 * RobustEvalFixTest.java
 * JUnit based test
 *
 * Created on November 15, 2004, 8:44 PM
 * @author Neil O. Rouben
 */
public class RobustEvalFixTest extends TestCase
{
    RobustEvalFix fixer;
    
    public RobustEvalFixTest(java.lang.String testName)
    {
        super(testName);
        fixer = new RobustEvalFix( "j1", "j2" );
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(RobustEvalFixTest.class);
        return suite;
    }
    
    /**
     * Test of process method, of class com.hrstc.trec.RobustEvalFix.
     */
    public void testProcess()
    {
        System.out.println("testProcess");
        
        // TODO add your test code below by replacing the default call to fail.
        // fail("The test case is empty.");
    }
    
    /**
     * Test of processStr method, of class com.hrstc.trec.RobustEvalFix.
     */
    public void testProcessStr()
    {
        System.out.println("testProcessStr");
        Vector v = new Vector();
        v.add( "1" );
        v.add( "2" );
        v.add( "3" );
        String str = "[1, 2, 3, 4, 5, 6]]]";
        str = fixer.processStr( str, v );
        assertEquals( "1, 2, 3, ", str );
        
        // TODO add your test code below by replacing the default call to fail.
        // fail("The test case is empty.");
    }
    
    // TODO add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
    
}
