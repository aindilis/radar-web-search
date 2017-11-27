package org.apache.lucenesandbox.xmlindexingdemo;

import org.xml.sax.*;
import org.xml.sax.AttributeList;
import javax.xml.parsers.*;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class XMLDocumentHandlerSAX
    extends HandlerBase
{
    /** A buffer for each XML element */
    private StringBuffer elementBuffer = new StringBuffer();

    private Document doc;
    private ArrayList docs;
    /**
     * Tag that indicates document
     */
    private String docTag;

        
    /**
     * @param xmlFile
     * @param docTag - Tag that indicates document
     */
    public XMLDocumentHandlerSAX(File xmlFile, String docTag)
	throws ParserConfigurationException, SAXException, IOException
    {
        this.docTag = docTag;
	SAXParserFactory spf = SAXParserFactory.newInstance();

	SAXParser parser = spf.newSAXParser();
        try
        {
            parser.parse(xmlFile, this);
        }
        catch ( SAXException ex )
        {
            ex.printStackTrace();
            xmlFile.renameTo( new File( xmlFile.getName() + ".bad" ) );
            throw ex;
        }
    }

    // call at document start
    public void startDocument()
    {
        docs = new ArrayList();
    }

    // call at element start
    public void startElement(String localName, AttributeList atts)
	throws SAXException
    {
        // If it is a Doc Tag -> create a new doc
        if ( localName.equals( docTag ) )
        {
            doc = new Document();
            docs.add( doc );
        }
        elementBuffer.setLength(0);
    }

    // call when cdata found
    public void characters(char[] text, int start, int length)
    {
	elementBuffer.append(text, start, length);
    }

    // call at element end
    public void endElement(String localName)
	throws SAXException
    {
        // "text" is used since that is what is searched by default        
        String fldName = "text";
        if ( localName.equals( "DOCNO" ) )
        {
            fldName = localName;
        }
	doc.add(Field.Text( fldName, elementBuffer.toString()));
    }

    public ArrayList getDocuments()
    {
	return docs;
    }
}
