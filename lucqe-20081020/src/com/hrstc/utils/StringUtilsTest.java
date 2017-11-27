/*
 * StringUtilsTest.java
 * JUnit based test
 *
 * Created on October 17, 2004, 3:35 PM
 *
 * @author Neil O. Rouben
 */

package com.hrstc.utils;

import java.util.*;
import junit.framework.*;

public class StringUtilsTest extends TestCase
{
    
    public StringUtilsTest(java.lang.String testName)
    {
        super(testName);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(StringUtilsTest.class);
        return suite;
    }
    
    /**
     * Test of toHashtable method, of class com.hrstc.utils.StringUtils.
     */
    public void testStringToHashtable()
    {
        System.out.println("testToHashtable");
        String str = "301 Q0 LA121990-0141 1 0.97221416 Luc";
        String flds ="qId proc docNo rank score run";
        Hashtable hash = StringUtils.stringToHashtable( str, flds );
        if ( 
                !hash.get( "qId" ).toString().equals( "301" ) 
             || !hash.get( "proc" ).toString().equals( "Q0" ) 
             || !hash.get( "docNo" ).toString().equals( "LA121990-0141" )              
             || !hash.get( "rank" ).toString().equals( "1" )              
             || !hash.get( "score" ).toString().equals( "0.97221416" )                           
             || !hash.get( "run" ).toString().equals( "Luc" )                                        
           )
        {
            fail( str + "->" + hash.toString() );
            
        }
    }
    
    /**
     * Test of stringToStringArray method, of class com.hrstc.utils.StringUtils.
     */
    public void testStringToStringArray()
    {
        System.out.println("testStringToStringArray");        
        String str = "fld0 fld1 fld2";
        String[] flds = StringUtils.stringToStringArray( str );
        assertEquals( "fld0", flds[0] );
        assertEquals( "fld1", flds[1] );        
        assertEquals( "fld2", flds[2] ); 
    }
    
    
    public void testIndexOf()    
    {
        String str = "3, 3, 3, 3";
        int idx = StringUtils.indexOf( str,  "3", 3 );
        assertEquals( 6, idx );
    }

    public void test_stringToAlphaNumeric()    
    {
        assertEquals( "this  some 123 world", StringUtils.stringToAlphaNumeric( "!@this .? some 123 world?" ) );
    }
    
    
    // TODO add test methods here, they have to start with 'test' name.
    // for example:
    // public void testHello() {}
    
    
}
