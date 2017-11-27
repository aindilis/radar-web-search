/*
 * ReportSummarizer.java
 *
 * Searches directory for the robust_eval.out files and creates summary report
 *
 * Created on January 5, 2005, 1:02 PM
 *
 * @author  Neil O Rouben
 */

package com.hrstc.trec.report;

import java.io.*;

public class EvalSummarizer
{
    File dir;
    StringBuffer buf;
    Parser parser;
    
    /** Creates a new instance of RobustReporter */
    public EvalSummarizer(String[] args) throws IOException
    {
        parser = new RobustEvalParser();
        dir = new File( args[0] );
        buf = new StringBuffer();
    }
    
    public void process() throws IOException
    {
        process( dir );        
        System.out.println( buf.toString() );
    }
    
    
    /**
     * Process directory recursively
     *
     */
    private void process( File file ) throws IOException
    {
        // Basis
        if ( file.isFile() )
        {
            if ( file.getName().indexOf( "robust_eval.out" ) >= 0 )
            {
                buf.append( file.getName() + " " + parser.parse( file ) + "\n" );
            }
        }
        // Must be Dir then (recur)
        else
        {            
            File[] files = file.listFiles();
            for ( int i = 0; i < files.length; i++ )
            {
                process( files[i] );
            }
            
        }
        
    }
    
    /**
     * @param args the command line arguments
     * args[0] - location of the directory to generate report from
     */
    public static void main(String[] args) throws IOException
    {
        EvalSummarizer rep = new EvalSummarizer( args );
        rep.process();
        // TODO code application logic here
    }
    
}
