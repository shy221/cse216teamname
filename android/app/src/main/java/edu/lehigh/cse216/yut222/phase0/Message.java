//Shenyi Yu Phase 1
package edu.lehigh.cse216.yut222.phase0;
import org.json.JSONArray;

import java.util.ArrayList;
class Message {
    /**
     * An integer index for this piece of data
     */
    int mId;
    //int uid;
    int mLikes;
    int mDislikes;
    int uId;

    /**
     * The string title and contents that comprise this piece of data
     */
    String mTitle;
    String mContent;
    String cUsername;
    String mCreated;


    /**
     * Construct a Message by setting its id, title and content
     *
     * @param id The id of this piece of data
     * @param title The string title for this piece of data
     * @param content The string contents for this piece of data
     */
    Message(int mid, int uid,  int likes, int dislike, String title, String content, String username, String time){
        mId = mid;
        uId = uid;
        mTitle = title;
        mDislikes = dislike;
        mLikes = likes;
        mContent = content;
        cUsername = username;
        mCreated = time;
    }
}