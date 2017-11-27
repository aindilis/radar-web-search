package com.hrstc.trec;

import java.io.*;
import java.util.*;

/**
 * StatsParser.java
 *
 * Created on October 15, 2004, 2:34 PM
 *
 * Takes as input output from ret_eval -q and produces output in form:
 * topicid avep p10
 * for the use by roubust_eval
 *
 * @author  Neil O. Rouben
 */
public class StatsParser
{
    File statsFile;
    File outFile;
    
    /** Creates a new instance of StatsParser */
    public StatsParser( String statsFileStr, String outFileStr )
    {
        statsFile = new File( statsFileStr );
        outFile = new File( outFileStr );
    }
    
    public void parse() throws IOException
    {
        BufferedReader in = new BufferedReader( new FileReader( statsFile ) );
        BufferedWriter out = new BufferedWriter( new FileWriter( outFile ));
        RStat stat = new RStat();
        Vector stats = new Vector();        
           
        // Read File
        while ( in.ready() )
        {
            String line = in.readLine();
            // Queryid (Num):      301
            if ( line.indexOf( "Queryid (Num):" ) != -1 )
            {
                stat.setQId( line.substring( line.indexOf( ":" ) + 1 ).trim() );
            }
            // The Next line must be Average precision 
            else if ( line.indexOf( "Average precision (non-interpolated) for all rel docs(averaged over queries)" ) != -1 )
            {
                line = in.readLine();
                stat.setAvgPrecision( line.trim() );
            }
            // Precision @ 10 docs
            // At   10 docs:   0.2000
            // Last value to be parsed out for a query -> write out and reset
            else if ( line.indexOf( "At   10 docs:" ) != -1 )
            {
                stat.setP10( line.substring( line.indexOf( ":" ) + 1 ).trim() );
                stats.add( stat );                
                stat = new RStat();
            }
        }
        
        // Write File
        // Remove last element since it is the stat for the all of the queries
        stats.remove( stats.size() - 1 );
        for ( int i = 0; i < stats.size(); i++ )
        {
            RStat rstat = (RStat) stats.elementAt( i );
            out.write( rstat.toString() + "\n" );
        }
        
        // Clean Up
        in.close();
        out.close();
        // TODO
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        StatsParser parser = new StatsParser( args[0], args[1] );
        parser.parse();
        System.out.println( "FIN" );
    }
    
}

/**
 * Represents  
 * topicid avep p10
 * for the use by roubust_eval
 */
class RStat
{
    private String qId;
    private String avgPrecision;
    private String p10;
        
    public RStat()
    {
    }
    
    /**
     * topicid avep p10
     */
    public String toString()
    {
        return ( qId + " " + avgPrecision + " " + p10 );
    }
    
    /**
     * Getter for property avgPrecision.
     * @return Value of property avgPrecision.
     */
    public java.lang.String getAvgPrecision()
    {
        return avgPrecision;
    }
    
    /**
     * Setter for property avgPrecision.
     * @param avgPrecision New value of property avgPrecision.
     */
    public void setAvgPrecision(java.lang.String avgPrecision)
    {
        this.avgPrecision = avgPrecision;
    }
    
    /**
     * Getter for property p10.
     * @return Value of property p10.
     */
    public java.lang.String getP10()
    {
        return p10;
    }
    
    /**
     * Setter for property p10.
     * @param p10 New value of property p10.
     */
    public void setP10(java.lang.String p10)
    {
        this.p10 = p10;
    }
    
    /**
     * Getter for property qId.
     * @return Value of property qId.
     */
    public java.lang.String getQId()
    {
        return qId;
    }
    
    /**
     * Setter for property qId.
     * @param qId New value of property qId.
     */
    public void setQId(java.lang.String qId)
    {
        this.qId = qId;
    }
    
}

