/*
 * FileLineExtractor.java
 * Extracts lines from file: for example extract odd line, or even, etc
 *
 * Created on October 24, 2004, 9:52 AM
 *
 * @author  wani
 */

package com.hrstc.utils;

import java.io.*;

public class FileLineExtractor
{
    private File inFile;
    private File outFile;
    private int lineCount;
    
    /**
     * Creates a new instance of FileLineExtractor
     * @param fileName
     * @param lineCountStr which line to extract 1 - based
     */
    public FileLineExtractor( String inFileName, String lineCountStr, String outFileName )
    {
        inFile = new File( inFileName );
        outFile = new File( outFileName );
        lineCount = Integer.valueOf( lineCountStr ).intValue();
    }
    
    public void process() throws IOException
    {
        BufferedReader in = new BufferedReader( new FileReader( inFile ) );
        BufferedWriter out = new BufferedWriter( new FileWriter( outFile ) );
        
        for ( int i = 1; in.ready(); i++ )
        {
            String line = in.readLine();
            if ( i % lineCount == 0 )
            {
                out.write( line + "\n" );
            }
        }
        
        in.close();
        out.close();
    }
    
    /**
     * @param args the command line arguments
     * ex. queries.bak 2 queries_even.txt
     */
    public static void main(String[] args) throws IOException
    {
        FileLineExtractor lExtractor = new FileLineExtractor( args[0],  args[1], args[2] );
        lExtractor.process();
    }
    
}
