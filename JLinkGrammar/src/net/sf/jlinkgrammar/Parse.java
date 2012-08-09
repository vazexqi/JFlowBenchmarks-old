package net.sf.jlinkgrammar;

import java.io.IOException;

/*
 * parser.java
 *
 * Created on December 1, 2006, 10:00 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * @author johnryan
 */
public class Parse {

    /**
     * Creates a new instance of parser
     */
    public Parse() {
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        Parser.doIt(args);
        //System.out.println(System.currentTimeMillis() - startTime);
    }

}
