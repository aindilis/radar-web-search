/*
 * FileRenamer.java
 *
 * Created on October 17, 2004, 12:43 AM
 */

package com.hrstc.utils;

import java.io.*;
import java.util.*;

/**
 *
 * @author  wani
 */
public class FileRenamer
{
    
    /** Creates a new instance of FileRenamer */
    public FileRenamer()
    {
    }
    
    public void rename( String fileName )
    {
        rename( new File( fileName ) );
    }
    
    public void rename( File file )
    {
        // Just the file rename it
        if ( !file.isDirectory() )
        {
            file.renameTo( new File( file.getName() + ".Z" ) );
        }
        // Must be directory
        else
        {
            File[] files = file.listFiles();
            for ( int i=0; i < files.length; i++ )
            {
                rename( files[i] );
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        FileRenamer fRen = new FileRenamer();
        fRen.rename( args[0] );
    }
    
}
