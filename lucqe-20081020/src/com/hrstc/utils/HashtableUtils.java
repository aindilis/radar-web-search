/*
 * HashtableUtils.java
 *
 * Created on October 17, 2004, 4:40 PM
 */

package com.hrstc.utils;

import java.util.*;

/**
 *
 * @author  wani
 */
public class HashtableUtils
{
    
    
    /**
     * Makes a string from a flds specified
     * @param h
     * @param flds
     * @return
     */    
    public static String hashtableToString( Hashtable h, String fldsStr )
    {
        StringBuffer strb = new StringBuffer();
        String[] flds = StringUtils.stringToStringArray( fldsStr );
        for ( int i = 0; i < flds.length; i++ )
        {
            strb.append( h.get( flds[i] ) + " " );
        }

        return strb.toString().trim();
    }
        
}
