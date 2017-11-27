package com.hrstc.trec;

import java.io.*;
import java.util.*;

import com.hrstc.utils.*;

/**
 * ColumnExtractor.java
 *
 * Created on November 3, 2004, 8:41 AM
 *
 * Extracts specified columns from a file
 * regular args: queries_even.txt.result 4 5 6 7 8 9 10
 * lucene args: queries.txt.result 4 5 6 7 8 9 10
 * desc run args: 
 *
 * @author  Neil O. Rouben
 */
public class ColumnExtractor
{
    
    /** Creates a new instance of ColumnExtractor */
    public ColumnExtractor()
    {
    }
    
    /**
     * Extracts specific columns; if there are only 2 args
     * extracts columns starting with <code> args[1] </code>
     * 
     * @param args
     * @throws IOException
     */
    public void process( String[] args ) throws IOException
    {
        BufferedReader in = new BufferedReader( new FileReader( args[0] ));
        FileWriter out = new FileWriter( args[0] + ".MatIn" );
        String outLine = null;
        
        while ( in.ready() )
        {
            String line = in.readLine();

            
            // Perform Column Extraction
            // Range Extraction 
            if ( args.length == 2 )
            {
                outLine = extractColumns( line, Integer.valueOf( args[1] ).intValue() );
            }
            // Specific columns extraction
            else
            {
                outLine = extractColumns( line, args );
            }
            
            out.write( outLine + "\n" );
        }
        
        in.close();
        out.close();
    }
    
    
    /**
     * Extracts specific columns
     * 
     * @param str 
     * @param args - from main
     * @return
     */
    private String extractColumns( String str, String[] args )
    {
        StringTokenizer tknzr = new StringTokenizer( str );
        StringBuffer buf = new StringBuffer();
        
        // Process Line
        int tokenCount = tknzr.countTokens();
        for ( int i = 0; i < tokenCount; i++ )
        {
            String iStr = String.valueOf( i );
            String token = tknzr.nextToken();
            if ( Utils.search( args, iStr ) != -1 )
            {
                buf.append( token + " " );
            }
        }
        
        return buf.toString();
    }
    
    
    
    /**
     * Extract columns range 
     * 
     * @param str
     * @param fromIdx - inclusive 1 based
     * @return
     */
    private String extractColumns( String str, int fromIdx )
    {
        StringBuffer buf = new StringBuffer();
        StringTokenizer tknzr = new StringTokenizer( str );
        
        // Extract tokens
        for ( int i = 1; tknzr.hasMoreElements(); i++ )
        {
            String token = tknzr.nextToken();
            if ( i >= fromIdx )
            {
                buf.append( token + " " );
            }
        }
        
        return buf.toString();
    }
    
    
    /**
     * Extracts specific columns; if there are only 2 args
     * extracts columns starting with <code> args[1] </code> 
     * 
     * @param args fileName col0 col2 (columns are 0 based)
     */
    public static void main(String[] args) throws IOException
    {
        ColumnExtractor extractor = new ColumnExtractor();
        extractor.process(args);
        System.out.println( "FIN" );
    }
    
}
