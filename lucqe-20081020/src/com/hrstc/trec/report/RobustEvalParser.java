/*
 * RobustEvalParser.java
 *
 * Created on January 5, 2005, 1:38 PM
 */

package com.hrstc.trec.report;

import java.io.*;
import java.util.*;


/**
 *
 * @author  wani
 */
public class RobustEvalParser implements Parser
{
    
    /** Creates a new instance of RobustEvalParser */
    public RobustEvalParser()
    {
    }
    

    public String parse(java.io.File file) throws IOException
    {
        BufferedReader reader = new BufferedReader( new FileReader( file ) );
        String str = parse( reader );
        reader.close();
        return str;
    }

    public String parse(String str) throws IOException
    {
        BufferedReader reader = new BufferedReader( new StringReader( str ) );
        String rstr = parse( reader );
        reader.close();
        return rstr;
    }
    
    
/*    
Summary measures over 200 old topics
MAP: 0.1742
P(10): 0.3440
Number of topics with no relevant in top 10:  31/200 = 15.5%
Area underneath MAP(X) vs. X curve for worst 50 topics: 0.0079
*/        
    public String parse( BufferedReader reader ) throws IOException
    {
        StringBuffer buf = new StringBuffer();
        String line = "";

        while ( reader.ready() && line != null )
        {
            line = reader.readLine();
            if ( line != null )
            {
                StringTokenizer tknzr = new StringTokenizer( line, ":=" );
                int count = 0;
                while ( tknzr.hasMoreElements() )
                {
                    String token = tknzr.nextToken();
                    count++;
                    // Skip first token - title
                    // Get the rest of the tokens
                    if ( count != 1 )
                    {
                        buf.append( token + "\t" );
                    }
                }
            }
        }        
        
        return buf.toString();
    }
    
    public static void main(String[] args) throws IOException
    {
        RobustEvalParser parser = new RobustEvalParser();
        parser.parse( "Summary measures over 200 old topics MAP: 0.1742 P(10): 0.3440\n" +
                      "Number of topics with no relevant in top 10:  31/200 = 15.5%\n" +
                      "Area underneath MAP(X) vs. X curve for worst 50 topics: 0.0079\n" );
        
    }
    
}
