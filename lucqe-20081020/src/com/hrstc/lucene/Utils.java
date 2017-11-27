/*
 * SimilarityUtils.java
 *
 * Created on October 25, 2004, 4:48 PM
 * 
 * @author  Neil Rouben
 */

package com.hrstc.lucene;

import java.io.*;
import java.util.*;

import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;

public class Utils
{
    // Values used in caching functions
    // Stores cached maxIdf
    public static float maxIdf = -1;
    // Caches idf values
    public static Hashtable<String, Float> idfTbl = null;
    public static Vector<TermQuery> terms = null;
    public static Document doc = null;
    public static int docTermCount = 0;
    

    /**
     * score(q,d) =?(t in q) tf(t in d) * idf(t) * getBoost(t.field in d) * lengthNorm(t.field in d) * coord(q,d) * queryNorm(q)
     * @param term
     * @param similarity
     * @return
     */    
    public static float scoreTerm( Document doc, String termStr, int docId, Similarity similarity, FilterIndexReader idxReader, Searcher searcher ) 
    throws IOException
    {
        Term term = new Term( Defs.FLD_TEXT, termStr );
        // tf(t in d) * idf(t) * getBoost(t.field in d) * lengthNorm(t.field in d) * coord(q,d) * queryNorm(q)
        // tf(t in d)    
        float tf = getTF(term.text(), docId, idxReader );
        // idf(t)
        float idf = similarity.idf( term, searcher );
        // getBoost(t.field in d)
        float boost = new Float( "1.0").floatValue();
        // lengthNorm(t.field in d)
        float lengthNorm = getLengthNorm( doc, similarity );
        // coord(q,d)
        float coord = new Float( "1.0").floatValue();        
        // queryNorm(q)        
        float queryNorm = new Float( "1.0").floatValue();      
        // tf(t in d) * idf(t) * getBoost(t.field in d) * lengthNorm(t.field in d) * coord(q,d) * queryNorm(q)
        float score = tf * idf * boost * lengthNorm * coord * queryNorm;
        
        return score;
    }
    
    public static float getIDF( String termStr, Searcher searcher, Similarity similarity )
    throws IOException
    {
        Term term = new Term( Defs.FLD_TEXT, termStr );
        float idf = similarity.idf( term, searcher );
        return idf;
    }

    
    public static float getIDFNorm( String termStr, Vector<TermQuery> terms,
            Searcher searcher, Similarity similarity )
            throws IOException
    {
        return getIDFNorm( termStr, terms, searcher, similarity, false );
    }    

    /**
     * 
     * @param termStr
     * @param terms
     * @param searcher
     * @param similarity
     * @param cache - indicates if values will be cached
     * @return
     * @throws IOException
     */
    public static float getIDFNorm( String termStr, Vector<TermQuery> terms, Searcher searcher, Similarity similarity, boolean cache )
    throws IOException
    {
        // get maxIDF
        // if cache and terms are equal get a cached value
        // else find maxIdf
        float maxIdf = 0;
        if ( cache && terms.equals( Utils.terms ) )
        {
            maxIdf = Utils.maxIdf;
        }
        else
        {
            maxIdf = getMaxIDF(terms, searcher, similarity);
            // Cache the value
            Utils.maxIdf = maxIdf;
            Utils.terms = terms;
        }
                
        // Normalize
        Term term = new Term( Defs.FLD_TEXT, termStr );
        float idf = similarity.idf( term, searcher );
        float idfNorm = idf / maxIdf;
        
        return idfNorm;
    }
    
    
    private static float getMaxIDF( Vector<TermQuery> terms, Searcher searcher, Similarity similarity ) throws IOException
    {
        float maxIdf = 0;

        for ( int i = 0; i < terms.size(); i++ )
        {
            Term term = terms.elementAt( i ).getTerm();
            float idf = similarity.idf( term, searcher );
            if ( maxIdf < idf )
            {
                maxIdf = idf;
            }
        }
        return maxIdf;
    }
    

    /**
     * Use similarity class instead
     */
    private static float getLengthNorm( Document doc, Similarity similarity )
    {
        Field fld = doc.getField( Defs.FLD_TEXT );
        int numTokens = new StringTokenizer( fld.stringValue() ).countTokens();
        return similarity.lengthNorm( Defs.FLD_TEXT, numTokens );
    }

    
    public static float getTFNorm( String termStr, Document doc, int docId, Similarity similarity, FilterIndexReader idxReader ) throws IOException
    {
        return getTFNorm(termStr, doc, docId, similarity, idxReader, false);
    }
    
    
    /**
     *
     * @return tf * lengthNorm = [0;1]
     */
    public static float getTFNorm( String termStr, Document doc, int docId, Similarity similarity, FilterIndexReader idxReader, boolean cache ) 
    throws IOException
    {
        float tf = getTF(termStr, docId, idxReader );
        //System.out.print( tf + " : " );
        // Normalize with similarity
        tf = similarity.tf( tf );
        //System.out.print( tf + " : " );        
        // Length Normazliation
        int docTermCount = getDocTermCount( doc, cache );
        tf = tf * similarity.lengthNorm( Defs.FLD_TEXT, docTermCount );
        //System.out.println( tf + "(" + docTermCount + ")" );        
        return tf;
    }
    
    public static int getDocTermCount( Document doc )
    {
        return getDocTermCount( doc, false );
    }
    
    public static int getDocTermCount( Document doc, boolean cache )
    {
        int docTermCount; 

        if ( cache )
        {
            if ( doc.equals( Utils.doc ) )
            {
                return Utils.docTermCount;
            }
            // cache is empty
            else
            {
                // Calculate docTermCount
                docTermCount = getDocTermCount( doc, false );
                // cache values
                Utils.doc = doc;
                Utils.docTermCount = docTermCount;
                return docTermCount;
            }
        }
        else
        {
            StringBuffer strb = new StringBuffer();
            String[] txt = doc.getValues( Defs.FLD_TEXT );
            for ( int i = 0; i < txt.length; i++ )
            {
                strb.append( txt[i] );
            }
            StringTokenizer tknzr = new StringTokenizer( strb.toString() );
            docTermCount = tknzr.countTokens();
            return docTermCount;
        }
    }
    
    
    
    public static float getTF( String term, int docId, FilterIndexReader idxReader ) throws IOException
    {
        // tf(t in d)
        TermFreqVector termFreqVector = idxReader.getTermFreqVector( docId, Defs.FLD_TEXT );
        String[] terms = termFreqVector.getTerms();
        int freqs[] = termFreqVector.getTermFrequencies();
        boolean found = false;
        float tf = 0;
        for ( int i = 0; i < terms.length && !found; i++ )
        {
            if ( term.equals( terms[i] ) )
            {
                tf = freqs[i];
                found = true;                
            }
        }
        return tf;
    }

    public static float coord( Vector<TermQuery> terms, Document doc, int docId, Similarity similarity, FilterIndexReader idxReader ) 
    throws IOException
    {
        int maxOverlap = terms.size();
        int overlap = 0;
        // Calculate overlap (terms w/ freq > 0
        for ( int i = 0; i < terms.size(); i++ )
        {
            float tf = getTF( terms.elementAt(i).getTerm().text(), docId, idxReader );
            if ( tf > 0 )
            {
                overlap++;
            }
        }        
        
        float coord = similarity.coord(overlap, maxOverlap);
        //System.out.println( overlap + " : " + maxOverlap + " : " + coord  );
        return coord;
    }

    
    /**
     * Returns normalized boost factor
     * @param termQuery
     * @param terms
     * @return
     */
    public static float getBoostNorm( TermQuery termQuery, Vector<TermQuery> terms )
    {
        float max = 0;
        
        // Find max
        Iterator<TermQuery> itr = terms.iterator();
        while ( itr.hasNext() )
        {
            TermQuery tq = itr.next();
            float boost = tq.getBoost();
            if ( boost > max )
            {
                max = boost;
            }
        }
        
        // Normalize
        float boost = termQuery.getBoost() / max;
        
        return boost;
    }
    
    
}
