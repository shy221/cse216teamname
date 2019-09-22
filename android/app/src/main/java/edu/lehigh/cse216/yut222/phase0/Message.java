package edu.lehigh.cse216.yut222.phase0;

class Message {
    /**
     * An integer index for this piece of data
     */
    int mId;

    /**
     * The string title and contents that comprise this piece of data
     */
    String mTitle;
    String mContent;

    /**
     * Construct a Message by setting its id, title and content
     *
     * @param id The id of this piece of data
     * @param title The string title for this piece of data
     * @param content The string contents for this piece of data
     */
    Message(int id, String title, String content) {
        mId = id;
        mTitle = title;
        mContent = content;
    }
}