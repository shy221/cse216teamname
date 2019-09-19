package edu.lehigh.cse216.teamname.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testMenu()
    {
        String menuOp = "Main Menu\n" +
                        "  [T] Create tblData\n" +
                        "  [D] Drop tblData\n" +
                        "  [1] Query for a specific row\n" +
                        "  [*] Query for all rows\n" +
                        "  [-] Delete a row\n" +
                        "  [+] Insert a new row\n" +
                        "  [~] Update a row\n" +
                        "  [q] Quit Program\n" +
                        "  [?] Help (this message)";
        // for now, assume it is true
        assertTrue ( true ); 

    }
}
