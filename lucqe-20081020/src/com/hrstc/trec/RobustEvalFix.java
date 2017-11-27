package com.hrstc.trec;

import java.io.*;
import java.util.*;

import com.hrstc.utils.*;

/**
 * RobustEvalFix.java
 * Used for fixing robust_eval script so that the queries match with the script
 *
 * Created on November 15, 2004, 10:21 AM
 */
public class RobustEvalFix
{
    private String robustEvalFileName;
    private String queryResultFileName;
    
    /** Creates a new instance of RobustEvalFix */
    public RobustEvalFix( String scriptName, String queryFileName )
    {
        robustEvalFileName = scriptName;
        queryResultFileName = queryFileName;
    }
    
    public void process() throws IOException
    {
        // Make a copy of script file
        String scriptStr = FileUtils.fileToString( robustEvalFileName );
        // Get all of the queries used in a run
        Vector queryIds = extractQueryIds( queryResultFileName );
        // Delete any queries #s from script that are not present in the run
        scriptStr = processStr( scriptStr, queryIds );
        // TODO *
        // Write to file
        //String fileOutName = robustEvalFileName.substring( robustEvalFileName.indexOf( '/' ) + 1 );
        String fileOutName = "robust2004_eval.pl";
        FileWriter out = new FileWriter( new File( fileOutName ) );
        out.write( scriptStr );
        out.close();
    }
    
    protected String processStr( String scriptStr, Vector ids )
    {
        StringBuffer strb = new StringBuffer();
        int begPos = -1; 
        int endPos = scriptStr.indexOf( '[' );
        strb.append( scriptStr.substring( 0, endPos ) );
        for ( int i = 0; i < 3; i++ )
        {
            begPos = scriptStr.indexOf( "[", endPos );
            // Append things that are being skipped
            //System.out.print( endPos + 1 + ":" );
            //System.out.print( begPos + '\n' );            
            strb.append( scriptStr.substring( endPos, begPos ) );
            endPos = scriptStr.indexOf( ']', begPos + 1 );
            String str = scriptStr.substring( begPos, endPos );
            str = correctStr(str, ids);
            strb.append( str );
        }
        strb.append( scriptStr.substring( endPos ) );
        
        return strb.toString();        
    }
    
    protected String correctStr( String str, Vector ids )
    {
        // Figure out which ids to keep
        Vector idsKeep = new Vector();
        StringTokenizer tknzr = new StringTokenizer( str, ", []" );
        while ( tknzr.hasMoreElements() )
        {
            String token = tknzr.nextToken();
            // If one of the ids then keep it
            if ( ids.indexOf( token ) >= 0 )
            {
                idsKeep.add( token );
            }
        }
        
        // Create New String
        StringBuffer strb = new StringBuffer();
        strb.append( "[" );
        Enumeration itr = idsKeep.elements();
        while ( itr.hasMoreElements() )
        {
            strb.append( itr.nextElement() );
            if ( itr.hasMoreElements() )
            {
                strb.append( ", " );
            }
        }
        //strb.append( "]" );
        return strb.toString();
    }
   
    
    private Vector extractQueryIds( String fileName ) throws IOException
    {
        Vector ids = new Vector();
        BufferedReader in = new BufferedReader( new FileReader( fileName ) );
        while ( in.ready() )
        {
            StringTokenizer tknzr = new StringTokenizer( in.readLine() );
            if ( tknzr.hasMoreElements() )
            {
                ids.add( tknzr.nextToken() );
            }
        }

        return ids;
    }
    
    /**
     * @param args 
     *              robust_eval file
     *              query_result file
     */
    public static void main(String[] args) throws IOException
    {
        RobustEvalFix fixer = new RobustEvalFix( args[0], args[1] );
        fixer.process();
        System.out.println( "FIN" );
    }
    
}
