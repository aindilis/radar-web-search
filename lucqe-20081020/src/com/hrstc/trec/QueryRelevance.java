package com.hrstc.trec;

import java.io.*;
import java.util.*;

import com.hrstc.utils.*;

/**
 * QueryRelevance.java
 * Used for storing QueryRelevance Judgments
 *
 *
 * Created on October 17, 2004, 2:22 PM
 *
 * @author  Neil O. Rouben
 */
public class QueryRelevance
{    
    File file;
    Hashtable hash;
    
    /**
     * Field Definitions
     */
    public static final String queryIdFld = "qId";
    public static final String docNoFld = "docNo";    
    public static final String relevanceFld = "rel";        
    public static final String scoreFld = "score";        
    
    public static final String NOT_RELEVANT = "0";
    
    /** Creates a new instance of QueryRelevance */
    public QueryRelevance( File file )
    {
        hash = new Hashtable();
        this.file = file;
    }
    
    public void load() throws IOException
    {
        BufferedReader reader = new BufferedReader( new FileReader( file ) );
        while ( reader.ready() )
        {
            String str = reader.readLine();
            System.out.println( str );
            Hashtable qh = StringUtils.stringToHashtable( str,  QueryRelevance.queryIdFld +
                " j1 " + QueryRelevance.docNoFld + " " + QueryRelevance.relevanceFld );           
            // If relevance = 0 then don't need to put it - save memory and simplify logic in other parts of the program
            String rel = qh.get( QueryRelevance.relevanceFld ).toString();
            if ( !rel.equals( QueryRelevance.NOT_RELEVANT ) )
            {
                hash.put( getKey(qh), qh );
            }
        }
    }
    
    public String getKey( Hashtable h )
    {
        return h.get( QueryRelevance.queryIdFld ).toString() + h.get( QueryRelevance.docNoFld ).toString();
    }


    /**
     *
     * @param qHash
     * @return if does not exist -> null
     */    
    public Hashtable get( Hashtable qHash )
    {
        return (Hashtable) hash.get( getKey(qHash)  );
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
    }
    
}
