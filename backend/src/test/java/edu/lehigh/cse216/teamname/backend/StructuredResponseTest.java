package edu.lehigh.cse216.teamname.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class StructuredResponseTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StructuredResponseTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(StructuredResponseTest.class);
    }

     /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        String title = "Test Title";
        String content = "Test Content";
        int id = 17;
        int likes = 5;
        DataRow d = new DataRow(id, title, content, likes);

        String status = "ok";
        String message = "";
        StructuredResponse res = new StructuredResponse(status, message, d);

        assertTrue(res.mStatus.equals(status));
        assertTrue(res.mMessage.equals(message));
        assertTrue(res.mData.equals(d));
    }
}