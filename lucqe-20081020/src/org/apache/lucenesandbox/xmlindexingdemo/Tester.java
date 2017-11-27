/*
 * Tester.java
 *
 * Created on October 17, 2004, 11:50 AM
 */

package org.apache.lucenesandbox.xmlindexingdemo;

/**
 *
 * @author  wani
 */
public class Tester
{
    
    /** Creates a new instance of Tester */
    public Tester()
    {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        String str = "No. 2&blank;/&blank;&hyph;&sect;Tuesday&sect;";
        str = str.replaceAll( "&blank;|&hyph;|&amp;|&sect;", " " );
        System.out.println( str );
    }
    
}
