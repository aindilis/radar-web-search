package com.hrstc.fuzzy;

import java.io.*;
import java.util.StringTokenizer;

import com.hrstc.utils.*;

/**
 * Generats mfs for the functions with high mf count
 * @author  Neil O. Rouben
 */
public class FISGenerator
{
    
    /** Creates a new instance of FISGenerator */
    public FISGenerator()
    {
    }
    
    
    /**
     * Used to generate mfs for the functions with high mf count
     * @param filePath - path to the file with <x> and <y> tags that need to be replaced
     * @param count - number of times to perform substitution     
     *
        [Input2]
        Name='tf1'
        Range=[0 1]
        NumMFs=1
        MF1='high':'trimf',[0 1 2]

        [Input3]
        Name='idf1'
        Range=[0 1]
        NumMFs=1
        MF1='high':'trimf',[0 1 1]
---------------------------------------
        [Input<t1>]
        Name='tf<t2>'
        Range=[0 1]
        NumMFs=1
        MF1='high':'trimf',[0 1 2]

        [Input<t3>]
        Name='idf<t2>'
        Range=[0 1]
        NumMFs=1
        MF1='high':'trimf',[0 1 1]

     */
    public String generateMFs( String filePath, String countStr ) throws IOException
    {
        int count = Integer.valueOf( countStr ).intValue();
        int[] counters = new int[10];
        counters[1] = 3;
        counters[2] = 1;
        counters[3] = 4;
        counters[4] = 5;        
        StringBuffer strb = new StringBuffer();
        String template = FileUtils.fileToString( filePath );
        
        for ( int i = 0; i < count; i++ )
        {
            String tempStr = template;
            tempStr = tempStr.replaceAll( "<t1>", String.valueOf( counters[1] ) );
            tempStr = tempStr.replaceAll( "<t2>", String.valueOf( counters[2] ) );
            tempStr = tempStr.replaceAll( "<t3>", String.valueOf( counters[3] ) );            
            tempStr = tempStr.replaceAll( "<t4>", String.valueOf( counters[4] ) );            
            strb.append( tempStr );            
            // Update Counters
            counters[1] += 3;
            counters[2] += 1;            
            counters[3] += 3;
            counters[4] += 3;            
        }
        
        return strb.toString();
    }
    
    
/**


[Rules]
0 1 1 0 0 0 0 0 0 0, 1 (0.33) : 1
0 0 0 1 1 0 0 0 0 0, 1 (0.33) : 1
0 0 0 0 0 1 1 0 0 0, 1 (0.33) : 1
0 -1 -1 0 0 0 0 0 0 0, -1 (0.33) : 1
0 0 0 -1 -1 0 0 0 0 0, -1 (0.33) : 1
0 0 0 0 0 -1 -1 0 0 0, -1 (0.33) : 1
0 0 0 0 0 0 0 1 1 0, 1 (0.33) : 1
0 0 0 0 0 0 0 -1 -1 0, -1 (0.33) : 1
0 0 0 0 0 0 0 0 0 1, 1 (0.05) : 1
0 0 0 0 0 0 0 0 0 -1, -1 (0.05) : 1
 
*/ 
    public String generateRules()
    {
        StringBuffer strb = new StringBuffer();
        
        int inputNum = 10;
        int rulesNum = 10;
        int count = 2;
        String weight = String.valueOf( 1.0 / inputNum  );

        // Create Rules
        for ( int i = 0; i < ( rulesNum / 2 ); i++ )
        {
            StringBuffer rulePos = new StringBuffer();
            StringBuffer ruleNeg = new StringBuffer();
            // Create Rule
            for ( int j = 0; j <= inputNum;  )
            {
                // Found Active Rule position - right before '1'
                if ( j == count )
                {
                    String posRuleTxt = "1 1 1 ";
                    String negRuleTxt = "-1 -1 -1 ";
                    rulePos.append( posRuleTxt );
                    ruleNeg.append( negRuleTxt );                    
                    // Increment by the number of input spaces taken in a rule
                    j += new StringTokenizer( posRuleTxt ).countTokens();
                }
                // If in the end: ", 1 (0.33) : 1"
                if ( j == rulesNum )
                {
                    rulePos.append( ", 1 (" + weight + ") : 1" );
                    ruleNeg.append( ", -1 (" + weight + ") : 1" ); 
                    j++;
                }
                // If in between              
                else if ( j != count ) 
                {
                    rulePos.append( "0 " );
                    ruleNeg.append( "0 " );                    
                    j++;
                }
                
            }
            strb.append( rulePos.toString() + "\n" );
            strb.append( ruleNeg.toString() + "\n" );
            count += 3;
        }
        
        return strb.toString();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        FISGenerator fisg = new FISGenerator();
        String str = null; 
        str = fisg.generateMFs( args[0], args[1] );        
        str = fisg.generateRules();
        System.out.println( str );
        
        FileWriter writer = new FileWriter( "output.txt" );
        writer.write( str );
        writer.close();
        // TODO code application logic here
    }
    
}
