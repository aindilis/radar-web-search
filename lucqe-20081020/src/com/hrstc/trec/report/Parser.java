/*
 * Parser.java
 *
 * Created on January 5, 2005, 1:35 PM
 *
 * @author  Neil O Rouben
 */

package com.hrstc.trec.report;

import java.io.*;

public interface Parser
{
    
    public String parse(File file) throws IOException;
    
}
