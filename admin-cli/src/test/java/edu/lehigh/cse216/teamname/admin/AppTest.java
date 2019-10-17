package edu.lehigh.cse216.teamname.admin;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.FileReader;

import java.io.IOException;

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

    // ~Field-------------------------------------------------------------
    /**
     * init an object to test
     */
    private App test = new App();
    private BufferedReader in;

    public void testPrompt() {
        // Main Menue Promt
        in = new BufferedReader(new StringReader("T"));
        assertEquals('T', test.prompt(in));
        in = new BufferedReader(new StringReader("D"));
        assertEquals('D', test.prompt(in));
        in = new BufferedReader(new StringReader("V"));
        assertEquals('V', test.prompt(in));
        in = new BufferedReader(new StringReader("v"));
        assertEquals('v', test.prompt(in));
        in = new BufferedReader(new StringReader("1"));
        assertEquals('1', test.prompt(in));
        in = new BufferedReader(new StringReader("*"));
        assertEquals('*', test.prompt(in));
        in = new BufferedReader(new StringReader("-"));
        assertEquals('-', test.prompt(in));
        in = new BufferedReader(new StringReader("+"));
        assertEquals('+', test.prompt(in));
        in = new BufferedReader(new StringReader("~"));
        assertEquals('~', test.prompt(in));
        in = new BufferedReader(new StringReader("q"));
        assertEquals('q', test.prompt(in));
        in = new BufferedReader(new StringReader("?"));
        assertEquals('?', test.prompt(in));
        in = new BufferedReader(new StringReader("L"));
        assertEquals('L', test.prompt(in));

        // promptTables
        in = new BufferedReader(new StringReader("M"));
        assertEquals('M', test.promptTables(in));
        in = new BufferedReader(new StringReader("U"));
        assertEquals('U', test.promptTables(in));
        in = new BufferedReader(new StringReader("C"));
        assertEquals('C', test.promptTables(in));
        in = new BufferedReader(new StringReader("L"));
        assertEquals('L', test.promptTables(in));
        in = new BufferedReader(new StringReader("D"));
        assertEquals('D', test.promptTables(in));
        in = new BufferedReader(new StringReader("q"));
        assertEquals('q', test.promptTables(in));
        in = new BufferedReader(new StringReader("?"));
        assertEquals('?', test.promptTables(in));

        // promptTablesLite
        in = new BufferedReader(new StringReader("M"));
        assertEquals('M', test.promptTablesLite(in));
        in = new BufferedReader(new StringReader("U"));
        assertEquals('U', test.promptTablesLite(in));
        in = new BufferedReader(new StringReader("C"));
        assertEquals('C', test.promptTablesLite(in));
        in = new BufferedReader(new StringReader("q"));
        assertEquals('q', test.promptTablesLite(in));
        in = new BufferedReader(new StringReader("?"));
        assertEquals('?', test.promptTablesLite(in));

        // promptViews
        in = new BufferedReader(new StringReader("L"));
        assertEquals('L', test.promptViews(in));
        in = new BufferedReader(new StringReader("D"));
        assertEquals('D', test.promptViews(in));
        in = new BufferedReader(new StringReader("q"));
        assertEquals('q', test.promptViews(in));
        in = new BufferedReader(new StringReader("?"));
        assertEquals('?', test.promptViews(in));

        // promptLikes
        in = new BufferedReader(new StringReader("L"));
        assertEquals('L', test.promptLikes(in));
        in = new BufferedReader(new StringReader("D"));
        assertEquals('D', test.promptLikes(in));
        in = new BufferedReader(new StringReader("q"));
        assertEquals('q', test.promptLikes(in));
        in = new BufferedReader(new StringReader("?"));
        assertEquals('?', test.promptLikes(in));
    }

    public void testGetString() {
        in = new BufferedReader(new StringReader("test_string"));
        assertEquals("test_string", test.getString(in, ""));
        Exception thrown = null;
        try {
            in = new BufferedReader(new FileReader("DNE.txt"));
            test.getString(in, "");
        } catch (Exception exception) {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
    }

    public void testGetInt() {
        in = new BufferedReader(new StringReader("1"));
        assertEquals(1, test.getInt(in, ""));
        Exception thrown = null;
        try {
            in = new BufferedReader(new FileReader("DNE.txt"));
            test.getInt(in, "");
        } catch (Exception exception) {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        thrown = null;
        try {
            in = new BufferedReader(new StringReader("not_a_number"));
            test.getInt(in, "");
        } catch (Exception exception) {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
    }
}
