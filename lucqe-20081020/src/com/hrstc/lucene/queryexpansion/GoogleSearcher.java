package com.hrstc.lucene.queryexpansion;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.*;

import org.apache.lucene.demo.html.HTMLParser;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import com.google.soap.search.GoogleSearch;
import com.google.soap.search.GoogleSearchFault;
import com.google.soap.search.GoogleSearchResult;
import com.google.soap.search.GoogleSearchResultElement;

import com.hrstc.lucene.*;
import com.hrstc.utils.*;

/**
 * Performs Query Expansion, utilizing google for document source
 * 
 * @author Neil O. Rouben
 */
public class GoogleSearcher 
{
    // CONSTANTS
    /**
     * Auth key in order to use google's web api
     */
    public static final String AUTH_KEY_FLD = "google.auth.key";
    /**
     * Location where cache is stored
     */
    public static final String FILE_CACHE_FLD = "google.file.cache";
    
	private static Logger logger = Logger.getLogger( "com.hrstc.lucene.queryexpansion.GoogleSearcher" );

    /**
     * Properties that contain necessary values 
     */
    private Properties prop;
    
    /**
     * Location where cache is stored
     */
    private File cache;
    /**
     * Auth key in order to use google's web api
     */
    private String key;
    		
	
	/**
	 * @param key to use google's web api
	 * @param cacheFileName where search results returned from google will be kept
	 * @param analyzer - used to parse documents to extract terms
	 * @param searcher - used to obtain idf
	 * @param similarity
	 */
	public GoogleSearcher( Properties prop ) 
	{
		key = prop.getProperty( GoogleSearcher.AUTH_KEY_FLD );
        cache = new File( prop.getProperty( Defs.RUN_TAG_FLD ) + prop.getProperty( GoogleSearcher.FILE_CACHE_FLD ) );
	}
	
    
    
	
	/**
	 * @param queryTxt
	 * @return
	 * @throws GoogleSearchFault
	 * @throws IOException
	 */
	public Vector<Document> search(String queryTxt) throws GoogleSearchFault, IOException 
	{
        logger.info( "Google Search..." );
        Vector<Document> hits = new Vector<Document>();
        BufferedWriter cacheOut = new BufferedWriter(new FileWriter( cache, true ));        
        
        // Execute Search
        GoogleSearch search = new GoogleSearch();
        search.setKey( this.key );
        search.setQueryString( queryTxt + " filetype:htm OR filetype:html" );
        GoogleSearchResult result = search.doSearch();
        logger.finer( result.toString() );        
        
        // Extract Contents
        GoogleSearchResultElement[] elements = result.getResultElements();
        for ( int i = 0; i < elements.length; i++ )
        {
            Document doc = new Document();            
            GoogleSearchResultElement element = elements[i];
            URL url = new URL( element.getURL() );
            String urlTxt = readURL( url, search );
            doc.add( Field.Text( Defs.FLD_TEXT, urlTxt ) );
            
            logger.finer( doc.toString() );
            cacheOut.write( doc.toString() );
            hits.add( doc );
        }
        
        cacheOut.close();
        
		return hits;
	}
    
    /**
     * Attempts to read url directly; if not possible tries to read it from google's cache.
     * Parses html out and returns only contents.
     * 
     * @param args
     * @throws GoogleSearchFault 
     * @throws IOException 
     * @throws Exception
     */
    public String readURL( URL url, GoogleSearch search ) throws GoogleSearchFault, IOException
    {
        String txt;
        
        // Try to read URL directly
        try
        { 
            InputStream inputStream = url.openStream();    
            txt = htmlToTxt( inputStream );
            inputStream.close();
        }
        // If Exception happens try to read it from google's cache
        catch ( IOException e)
        {
            e.printStackTrace();
            logger.log( Level.SEVERE, e.getStackTrace().toString() );
            logger.info( "Exception Encountered: document will be retrieved from cache." );
            
            // Read from cache
            byte[] byteArray = search.doGetCachedPage( url.toString() );                
            ByteArrayInputStream inputStream = new ByteArrayInputStream( byteArray );
            txt = htmlToTxt( inputStream );
            inputStream.close();
        }

        return txt;
    }

    
    /**
     * Reads html and returns txt contents
     * @param in
     * @return
     * @throws IOException 
     */
    public String htmlToTxt( InputStream inputStream ) throws IOException
    {
        HTMLParser parser = new HTMLParser( inputStream ) ;
        Reader reader = parser.getReader();
        String txt = StringUtils.toString( reader );
        
        return txt;
    }
	
}
