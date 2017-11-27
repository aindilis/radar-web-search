/*
 * StringUtils.java
 *
 * Created on October 17, 2004, 3:27 PM
 *
 * @author  Neil O. Rouben
 */

package com.hrstc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class StringUtils
{
    
    /** Creates a new instance of StringUtils */
    public StringUtils()
    {
    }
    
    public static int indexOf( String str, String searchStr, int occurence )
    {
        int idx = -1;
        
        for ( int i = 0; i < occurence; i++ )
        {
            idx = str.indexOf( searchStr, idx + 1 );
        }
        
        return idx;
    }
    
    public static Hashtable stringToHashtable( String valsStr, String fldsStr )
    {
        Hashtable hash = new Hashtable();
        String[] flds = stringToStringArray(fldsStr);
        String[] vals = stringToStringArray(valsStr);
        for ( int i = 0; i < flds.length; i++ )
        {
            hash.put( flds[i], vals[i] );
        }
        return hash;
    }
    
    public static String[] stringToStringArray( String str )
    {
        StringTokenizer tknzr = new StringTokenizer( str );
        String[] flds = new String[tknzr.countTokens()];
        for ( int i = 0; i < flds.length; i++ )
        {
            flds[i] = tknzr.nextToken();
        }
        return flds;
    }
    
    
    /**
     * Leaves only AlphaNumeric and space chars
     *
     */
    public static String stringToAlphaNumeric( String str )
    {
        StringBuffer strb = new StringBuffer();
        for ( int i = 0; i < str.length(); i++ )
        {
            char ch = str.charAt( i );
            if ( Character.isLetterOrDigit(ch) || Character.isWhitespace(ch) )
            {
                strb.append( ch );
            }
        }
        return strb.toString();
    }
    
    public static String toString( Reader reader ) throws IOException
    {
        BufferedReader in = new BufferedReader( reader );
        String str;
        StringBuffer buf = new StringBuffer();
        
        while ( (str = in.readLine()) != null )
        {
            buf.append( str + "\n" );
        }
        
        return buf.toString();
    }
    
    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        String str = "3, 3, 3, 3";
        int idx = StringUtils.indexOf( str,  "3", 3 );
        
    }
    
}
