package edu.lehigh.cse216.yut222.phase0;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Message_constructor_sets_fields() throws Exception {
        Message d = new Message(999,"test title","test content",0);
        assertEquals(d.mId,999);
        assertEquals(d.mTitle, "test title");
        assertEquals(d.mLikes,0);
        assertEquals(d.mContent, "test content");

    }
}