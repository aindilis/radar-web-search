/*
 * RobustEvalParserTest.java
 * JUnit based test
 *
 * Created on January 5, 2005, 1:58 PM
 */

package com.hrstc.trec.report;

import java.io.*;
import java.util.*;
import junit.framework.*;

/**
 *
 * @author wani
 */
public class RobustEvalParserTest extends TestCase
{
    
    public RobustEvalParserTest(java.lang.String testName)
    {
        super(testName);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(RobustEvalParserTest.class);
        return suite;
    }
    
    /**
     * Test of parse method, of class com.hrstc.trec.report.RobustEvalParser.
     */
    public void testParse() throws IOException
    {
        System.out.println("testParse");
        
        RobustEvalParser parser = new RobustEvalParser();
        parser.parse( "Summary measures over 200 old topics MAP: 0.1742 P(10): 0.3440\n" +
                      "Number of topics with no relevant in top 10:  31/200 = 15.5%\n" +
                      "Area underneath MAP(X) vs. X curve for worst 50 topics: 0.0079\n" );
        
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }
    
    // TODO add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
    
}
