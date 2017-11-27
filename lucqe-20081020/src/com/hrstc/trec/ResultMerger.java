package com.hrstc.trec;

import java.io.*;
import java.util.*;

import com.hrstc.utils.*;

/**
 * ResultMerger.java
 *
 * Created on January 9, 2005, 11:34 AM
 *
 * Merges results from two files; one by one; 
 * Files must have equal number of docs / query
 *
 * @author  Neil O. Rouben
 */
public class ResultMerger
{
    
    BufferedReader reader1;
    BufferedReader reader2;    
    BufferedWriter writer;
    String formatIn1;
    String formatIn2;
    String formatOut;
    
    /** Creates a new instance of ResultMerger */
    public ResultMerger( String[] args ) throws IOException
    {
        reader1 = new BufferedReader( new FileReader( args[0] ) );
        reader2 = new BufferedReader( new FileReader( args[1] ) );        
        writer = new BufferedWriter( new FileWriter( args[2] ) );
        // 302 Q0 FBIS4-30637 1 0.208113185175 FIS
        formatIn1 = Defs.QUERY_ID + " iter " + Defs.DOCUMENT_NUMBER;
        // 302 Q0 FBIS4-67720 1 0.38902417 0.0 0.83408016  0.09578263 1.0  0.0 0.35804617  0 0 0.33333334
        formatIn2 = Defs.QUERY_ID + " iter " + Defs.DOCUMENT_NUMBER;
        // 302 Q0 FBIS4-30637 1 0.208113185175 FIS        
        formatOut = Defs.QUERY_ID + " iter " + Defs.DOCUMENT_NUMBER + " " + Defs.RANK + " " + Defs.SCORE + " " + Defs.RUN_ID;
    }
    
    public void process() throws IOException
    {
        Hashtable qHash = new Hashtable();
        int rank = 1;
        float score = 1;
        String qIdPrevious = "";
        String qIdCurrent;
        
        while ( reader1.ready() && reader2.ready() )
        {
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            Hashtable docHash1 = StringUtils.stringToHashtable( line1, formatIn1 );
            Hashtable docHash2 = StringUtils.stringToHashtable( line2, formatIn2 );
            qIdCurrent = (String) docHash1.get( Defs.QUERY_ID );
            // If next query reset counters
            if ( !qIdCurrent.equals( qIdPrevious ) )
            {
                qHash = new Hashtable();
                rank = 1;
                score = 1;
            }
            // Output results
            // If not already written -> write it
            String docId1 = (String) docHash1.get( Defs.DOCUMENT_NUMBER );
            String docId2 = (String) docHash2.get( Defs.DOCUMENT_NUMBER );            
            if ( !qHash.containsKey( docId1 ) )
            {
                qHash.put( docId1,  docId1 );
                docHash1.put( Defs.RANK, String.valueOf( rank ) );
                rank++;
                docHash1.put( Defs.SCORE, String.valueOf( score ) );
                score -= 0.0001;
                String out = HashtableUtils.hashtableToString( docHash1, formatOut );
                writer.write( out + "\n" );
            }
            if ( !qHash.containsKey( docId2 ) )
            {
                qHash.put( docId2,  docId2 );
                docHash2.put( Defs.RANK, String.valueOf( rank ) );
                rank++;
                docHash2.put( Defs.SCORE, String.valueOf( score ) );
                score -= 0.0001;
                String out = HashtableUtils.hashtableToString( docHash2, formatOut );
                writer.write( out + "\n" );
            }            
            
            qIdPrevious = qIdCurrent;
        }
        
        reader1.close();
        reader2.close();
        writer.close();
    }
    
    
    /**
     * @param args the command line arguments
     * 0 - file1 in
     * 1 - file2 in
     * 2 - file out
     */
    public static void main(String[] args) throws IOException
    {
        ResultMerger merger = new ResultMerger( args );
        merger.process();
        // TODO code application logic here
    }
    
}
