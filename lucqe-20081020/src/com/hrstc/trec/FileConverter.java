package com.hrstc.trec;

import java.io.*;
import java.util.*;

import com.hrstc.utils.*;

/**
 * FileConverter.java
 * Takes result file from lucene and qrel file and combines them in the file
 * that could be used for training input...output
 *
 * Created on October 17, 2004, 2:39 PM
 *
 * @author  wani
 */
public class FileConverter
{    
    File resultFile;
    File qRelFile;
    File outFile;
    
    /**
     * Creates a new instance of FileConverter
     * @param resultFileName
     * @param qRelName
     * @param outFileName
     */
    public FileConverter( String resultFileName, String qRelName, String outFileName )
    {
        this.resultFile = new File(resultFileName);
        this.qRelFile = new File(qRelName);
        this.outFile = new File(outFileName);
    }
    
    public void convert() throws IOException
    {
        // Load Relevance File
        QueryRelevance qRel = new QueryRelevance( qRelFile );
        qRel.load();
        // Read Score File & Write Training File
        BufferedReader reader = new BufferedReader( new FileReader( resultFile ) );
        BufferedWriter writer = new BufferedWriter( new FileWriter( outFile ) );
        while ( reader.ready() )
        {
            String str = reader.readLine();
            String inFormat = QueryRelevance.queryIdFld + " j1 " + QueryRelevance.docNoFld + " rank " + QueryRelevance.scoreFld + 
                              " tf1 idf1 tf2 idf2 tf3 idf3 tf4 idf4 coord ";
            Hashtable scoreHash = StringUtils.stringToHashtable( str, inFormat );
            // Retrieves data only if it is a relevant doc | q
            System.out.println( str );
            Hashtable relHash = qRel.get( scoreHash );
            if ( relHash == null )
            {
                scoreHash.put( QueryRelevance.relevanceFld, QueryRelevance.NOT_RELEVANT );
            }            
            // Add data from relevance hash to score hash
            else
            {
                scoreHash.putAll( relHash );
            }
            //String outFormat = QueryRelevance.scoreFld + " " + QueryRelevance.relevanceFld;
            String outFormat = QueryRelevance.scoreFld + " tf1 idf1 tf2 idf2 tf3 idf3 coord " + QueryRelevance.relevanceFld;
            //System.out.println( outFormat );
            //System.out.println( scoreHash );
            writer.write( HashtableUtils.hashtableToString( scoreHash, outFormat ) + "\n" );
        }
        // Clean up
        writer.close();
        reader.close();
        // TODO ***
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println( "Usage: ResultFile, QRelFile, OutFile" );
        FileConverter converter = new FileConverter( args[0], args[1], args[2] );
        converter.convert();
        System.out.println( "FIN" );
    }
    
}
