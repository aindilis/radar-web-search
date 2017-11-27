/*
 * QueryExpansionTest.java
 * JUnit based test
 *
 * Created on February 25, 2005, 3:46 PM
 */

package com.hrstc.lucene.queryexpansion;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.lucene.index.Term;

/**
 *
 * @author Neil O. Rouben
 */
public class QueryExpansionTest extends TestCase
{
    
    public QueryExpansionTest(java.lang.String testName)
    {
        super(testName);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(QueryExpansionTest.class);
        return suite;
    }
    
    /**
     * Test of expandQuery method, of class com.hrstc.lucene.queryexpansion.QueryExpansion.
     */
    public void testExpandQuery()
    {
        System.out.println("testExpandQuery");
        
        // TODO add your test code below by replacing the default call to fail.
        //fail("The test case is empty.");
    }
    
    /**
     * Test of adjust method, of class com.hrstc.lucene.queryexpansion.QueryExpansion.
     */
    public void testAdjust()
    {
        System.out.println("testAdjust");
        
        // TODO add your test code below by replacing the default call to fail.
        //fail("The test case is empty.");
    }
    
    /**
     * Test of mergeQueries method, of class com.hrstc.lucene.queryexpansion.QueryExpansion.
     */
    public void testMergeQueries()
    {
        System.out.println("testMergeQueries");
        
        // TODO add your test code below by replacing the default call to fail.
        //fail("The test case is empty.");
    }
    
    
    public void testFind()
    {
        Term t1 = new Term( "fld", "txt" );
        Term t2 = new Term( "fld", "txt" );        
        
        assertTrue( t1.equals( t2 ) );        
    }
    
    
    // TODO add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
    
}
