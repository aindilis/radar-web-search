/*
 * Main.java
 *
 * Created on October 11, 2004, 1:10 PM
 */

package xml_fixer;

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Adds <DOCS> to the beginning of the file; and </DOCS> to the end of the file
 *
 * @author  Neil Rouben
 */
public class XMLFixer
{
    File dir;
    
    /** Creates a new instance of Main */
    public XMLFixer( String dirPath )
    {
        dir = new File( dirPath );
    }
    
    public void fix() throws IOException
    {
        System.out.println( "Processing..." );
        File[] files = dir.listFiles();
        for ( int i = 0; i < files.length; i++ )
        {
            fixFile( files[i] );
        }
        // TODO
    }
    
    private void fixFile( File file ) throws IOException
    {
        // Read File
        System.out.println( file.getName() ) ;
        BufferedReader reader = new BufferedReader( new FileReader(file) );
        StringBuffer buf = new StringBuffer();
        buf.append( "<DOCS>\n" );
        // Correct some unclosed tags
        while ( reader.ready() )
        {
            String line = reader.readLine();
            if ( line.indexOf( "<F " ) != -1 )
            {
                line = "<F>" + line.substring( line.indexOf( ">" ) + 1 );
            }
            buf.append( line );
            buf.append( "\n" );
        }
        reader.close();
        buf.append( "</DOCS>" );        
        // Write to File
        BufferedWriter writer = new BufferedWriter( new FileWriter(file) );
        writer.write( buf.toString() );
        writer.close();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println( "usage: <dir>" );
        XMLFixer fixer = new XMLFixer( args[0] );
        fixer.fix();
        System.out.println( "Finsihed..." );
        // TODO code application logic here
    }
    
}
