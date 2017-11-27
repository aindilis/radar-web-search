/*
 * Utils.java
 *
 * Created on November 3, 2004, 9:52 AM
 */

package com.hrstc.utils;

/**
 *
 * @author  Neil O. Rouben
 */
public class Utils
{
    
    public static int search( Object[] array, Object obj )
    {
        int idx = -1;
        for ( int i = 0; i < array.length; i++ )
        {
            if ( obj.equals( array[i] )  )
            {
                idx = i;
            }
        }
        return idx;
    }
    
}
