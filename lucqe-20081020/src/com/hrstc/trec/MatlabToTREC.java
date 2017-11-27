package com.hrstc.trec;

import java.io.*;
import java.util.*;

import com.hrstc.utils.*;

/**
 * MatlabToTREC.java
 *
 * Created on November 3, 2004, 11:16 AM
 *
 * Merges output from matlab with the lucenes output and creates a file that 
 * could be evaluated by trec_eval 
 * TREC: qid iter   docno      rank  sim   run_id
 *
 * @author  Neil Rouben
 */
public class MatlabToTREC
{
    
    /** Creates a new instance of MatlabToTREC */
    public MatlabToTREC()
    {
    }
    
    
    /**
     * 
     * @param args 
     *              0 - Matlab Out File
     *              1 - Lucene Result File
     * @throws IOException
     */
    public void process( String[] args ) throws IOException
    {
        // TODO code application logic here
        // Read and Merge both files
        BufferedReader inMat = new BufferedReader( new FileReader( args[0]) );        
        BufferedReader inLuc = new BufferedReader( new FileReader( args[1]) );        
        Vector v = new Vector();
        while ( inLuc.ready() )
        {
            String lucLine = inLuc.readLine();
            String matLine = inMat.readLine();
            // 700 Q0 FBIS4-52762 1000 
            // qid iter docno
            Hashtable data = StringUtils.stringToHashtable( lucLine,  
            Defs.QUERY_ID + " " + Defs.ITTERATION + " " + Defs.DOCUMENT_NUMBER );
            data.putAll( StringUtils.stringToHashtable( matLine, Defs.SCORE ) );
            data.put( Defs.RUN_ID,  "FIS" );
            v.add( data );
        }        
        // Sort
        Object[] array = v.toArray();
        Arrays.sort( array, new MatlabToTREC.TrecComparator() );
        // Add Rankings
        addRankings( array );
        // Output
        StringTokenizer tknzr = new StringTokenizer( args[0], "." );
        FileWriter out = new FileWriter( tknzr.nextToken() + ".result" );
        for ( int i = 0; i < array.length; i++ )
        {
            Hashtable hash = (Hashtable) array[i];
            // qid iter docno rank sim run_id
            out.write( HashtableUtils.hashtableToString( hash, 
		       Defs.QUERY_ID + " " + Defs.ITTERATION + " " + Defs.DOCUMENT_NUMBER + " " + Defs.RANK +
                       " " + Defs.SCORE + " " + Defs.RUN_ID ) + "\n" );
        }
        // Clean Up
        inLuc.close();
        inMat.close();
        out.close();
    }
    
    private void addRankings( Object[] array )
    {
        int rank = 1;
        String curQId = "";
        for ( int i = 0; i < array.length; i++ )
        {
            Hashtable hash = (Hashtable) array[i];
            String qId = hash.get( Defs.QUERY_ID ).toString();
            // New Set of Queries
            if ( !qId.equals( curQId ) )
            {
                rank = 1;
            }
            hash.put( Defs.RANK,  String.valueOf( rank ) );
            curQId = qId;
            rank++;
        }
    }
    
    /**
     * @param args matlabFile, luceneFile
     */
    public static void main(String[] args) throws IOException
    {
        MatlabToTREC conv = new MatlabToTREC();
        conv.process( args );
        System.out.println( "FIN" );
    }
    
    private class TrecComparator implements Comparator
    {
        
        public int compare(Object obj1, Object obj2)
        {
            Hashtable hash1 = (Hashtable) obj1;
            Hashtable hash2 = (Hashtable) obj2;
            int qId1 = Integer.valueOf( hash1.get( Defs.QUERY_ID ).toString() ).intValue();
            int qId2 = Integer.valueOf( hash2.get( Defs.QUERY_ID ).toString() ).intValue();            
            float score1 = Float.valueOf( hash1.get( Defs.SCORE ).toString() ).floatValue();
            float score2 = Float.valueOf( hash2.get( Defs.SCORE ).toString() ).floatValue();            
            
            if ( qId1 == qId2 )
            {
                // Reversed since want in descending order
                if ( score1 > score2 )
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
            else
            {
                if ( qId1 > qId2  )
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }            
        }
        
    }
    
}
