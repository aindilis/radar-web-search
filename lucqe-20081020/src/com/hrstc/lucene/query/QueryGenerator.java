/*
 * QueryGenerator.java
 * Takes a TREC topic file and generates queries from <description> field to be used
 * by Lucene
 * Preserves the <num> which is the topic number
 *
 *
 * Created on October 13, 2004, 1:37 PM
 */

package com.hrstc.lucene.query;

import java.io.*;
import java.util.*;

import com.hrstc.utils.*;

/**
 *
 * @author  Neil O. Rouben
 */
public class QueryGenerator
{
    public static final String FLD_TITLE = "<title>";
    public static final String FLD_DESCRIPTION = "<desc>";
    
    File file;
    File qFile;
    String qFld;
    
    /** Creates a new instance of QueryGenerator */
    public QueryGenerator( String[] args )
    {
        file = new File( args[0] );
        qFld = args[1];
        qFile = new File( "queries.txt" );
    }
    
    /**
     * Generates queries
     *
     * @param fld from which to extract query
     */
    public void generate() throws IOException
    {
        BufferedReader reader = new BufferedReader( new FileReader(file) );
        BufferedWriter writer = new BufferedWriter( new FileWriter(qFile) );
        
        //Read File
        while ( reader.ready() )
        {
            String line = reader.readLine();
            // Found Query Title
            if ( line.startsWith( "<num>" ) )
            {
                // <num> Number: 301
                StringTokenizer tknzr = new StringTokenizer( line );
                String numToken = null;
                while ( tknzr.hasMoreElements() )
                {
                    numToken  = tknzr.nextToken();
                }
                String query = getQuery( reader );
                // Write Query out
                writer.write( numToken + " " + query + "\n" );
            }
        }
        // Clean up
        reader.close();
        writer.close();
    }
    
    /**
     * @param args the command line arguments
     * 0 - fileName to the query file
     * 1 - from which field to extract query (see static finals for options)
     */
    public static void main(String[] args) throws IOException
    {
        QueryGenerator qg = new QueryGenerator( args );
        qg.generate();
        // TODO code application logic here
    }
    
    /**
     * Extracts <description> field
     */
    public String getQueryFromDescription(BufferedReader reader) throws IOException
    {
        /*
            File Format:
            <num> Number: 439
            <title> inventions, scientific discoveries
            <desc> Description:
            What new inventions or scientific discoveries have been
            made?
         
            <narr> Narrative:
         */
        StringBuffer query = new StringBuffer();
        String line = reader.readLine();
        // Skip to the beginning of description field
        while ( line.indexOf( "<desc>" ) < 0  )
        {
            line = reader.readLine();
        }
        // Must be in the beginning of <desc> field
        // Skip the tag; get the next line
        line = reader.readLine();
        // Read untill the end - <narr>
        while ( line.indexOf( "<narr>" ) < 0 )
        {
            // Pad with space so that there is space between former lines
            query.append( line + " " );
            line = reader.readLine();
        }
        return StringUtils.stringToAlphaNumeric( query.toString() );
    }
    
    public String getQueryFromTitle(BufferedReader reader) throws IOException
    {
        // Get: Title line starts with <title>
        String title = null;
        String line = null;
        while ( title == null )
        {
            line = reader.readLine();
            if ( line.indexOf( "<title>" ) != -1 )
            {
                title = line.trim();
            }
        }
        /**
         * Title Format:
         * <title> International Organized Crime
         * OR
         * <title>
         * U.S. ethnic population
         */
        StringBuffer query = new StringBuffer();
        if ( title.indexOf( ">" ) < title.length() -1 )
        {
            query.append( title.substring( title.indexOf( ">" ) + 1 ) );
        }
        else
        {
            query.append( reader.readLine() );
        }
        return query.toString().trim();
    }
    
    public String getQuery(BufferedReader reader) throws IOException
    {
        if ( qFld.equals( FLD_DESCRIPTION ) )
        {
            return getQueryFromDescription( reader );
        }
        else
        {
            return getQueryFromTitle( reader );
        }
    }
    
    
}
