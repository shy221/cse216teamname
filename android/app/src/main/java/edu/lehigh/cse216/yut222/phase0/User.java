//ZehuiXiao phase2
package edu.lehigh.cse216.yut222.phase0;

class User {
    /**
     * An integer index for this piece of data
     */
    int uId;
    String uName;

    /**
     * The string title and contents that comprise this piece of data
     */
    String uEmail;
    String uIntro;

    /**
     * Construct a Message by setting its id, title and content
     *
     * @param id The id of this piece of data
     * @param title The string title for this piece of data
     * @param content The string contents for this piece of data
     */
    User(int id, String name, String email, String intro){
        uId = id;
        uName = name;
        uEmail = email;
        uIntro = intro;
    }
}