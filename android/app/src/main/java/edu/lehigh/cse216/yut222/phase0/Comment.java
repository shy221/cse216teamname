//Zehui Xiao phase2
package edu.lehigh.cse216.yut222.phase0;

public class Comment {
    /**
     * An integer index for this piece of data
     */
    int cId;
    int uId;
    int mId;
    /**
     * The string title and contents that comprise this piece of data
     */
    String cText;
    String cUsername;

    /**
     * Construct a Message by setting its id, title and content
     * @param text The string contents for this piece of data
     */
    Comment(int cid, int uid, int mid, String text, String username) {
        cId = cid;
        uId = uid;
        mId = mid;
        cText = text;
        cUsername = username;
    }
}
