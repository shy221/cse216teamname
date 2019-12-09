package edu.lehigh.cse216.teamname.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import sun.awt.image.ImageWatched.Link;

public class Database {
    /**
     * The connection to the database. When there is no connection, it should be
     * null. Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all data in the database
     */
    private PreparedStatement mSelectAll;

    /**
     * A prepared statement for getting one row from the database
     */
    private PreparedStatement mSelectOne;

    /**
     * A prepared statement for deleting a row from the database
     */
    private PreparedStatement mDeleteOne;

    /**
     * A prepared statement for inserting into the database
     */
    private PreparedStatement mInsertOne;

    /**
     * A prepared statement for inserting into the database with the tm event ID
     */
    // private PreparedStatement mInsertOne2;

    /**
     * A prepared statement for updating a single row in the database
     */
    private PreparedStatement mUpdateOne;

    /**
     * A prepared statement for creating the table in our database
     */
    private PreparedStatement mCreateTable;

    /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTable;

    /**
     * A prepared statement for increase likes
     */
    private PreparedStatement mIncrementLikes;

    /**
     * A prepared statement for decrease likes
     */
    private PreparedStatement mDecrementLikes;

    /**
     * A prepared statement for increase dislikes
     */
    private PreparedStatement mIncrementDislikes;

    /**
     * A prepared statement for decrease dislikes
     */
    private PreparedStatement mDecrementDislikes;

    /**
     * A prepared statement for clear likes
     */
    private PreparedStatement mClearLikes;

    /**
     * A prepared statement for clear dislikes
     */
    private PreparedStatement mClearDislikes;

    /**
     * A prepared statement for clear comments
     */
    private PreparedStatement mClearComments;

    /**
     * A prepared statement for checking if liked
     */
    private PreparedStatement mCheckLikes;

    /**
     * A prepared statement for checking if disliked
     */
    private PreparedStatement mCheckDislikes;

    /**
     * A prepared statement for getting all data in the user table
     */
    private PreparedStatement uSelectAll;

    /**
     * A prepared statement for getting one row from the user table
     */
    private PreparedStatement uSelectOne;

    /**
     * A prepared statement for deleting a row from the user table
     */
    private PreparedStatement uDeleteOne;

    /**
     * A prepared statement for inserting into the user table
     */
    private PreparedStatement uInsertOne;

    /**
     * A prepared statement for updating a single row in the user table
     */
    private PreparedStatement uUpdateOne;

    /**
     * A prepared statement for creating the table in our user table
     */
    private PreparedStatement uCreateTable;

    /**
     * A prepared statement for dropping the table in our user table
     */
    private PreparedStatement uDropTable;

    /**
     * A prepared statement for authenticating user in our user table
     */
    private PreparedStatement uAuth;

    /**
     * A prepared statement for authenticating user in our user table
     */
    private PreparedStatement uUpdatePassword;

    /**
     * A prepared statement for getting all data in the user table
     */
    private PreparedStatement cSelectAll;

    /**
     * A prepared statement for getting one row from the user table
     */
    private PreparedStatement cSelectOne;

    /**
     * A prepared statement for deleting a row from the user table
     */
    private PreparedStatement cDeleteOne;

    /**
     * A prepared statement for inserting into the user table
     */
    private PreparedStatement cInsertOne;

    /**
     * A prepared statement for updating a single row in the user table
     */
    private PreparedStatement cUpdateOne;

    /**
     * A prepared statement for creating the table in our user table
     */
    private PreparedStatement cCreateTable;

    /**
     * A prepared statement for dropping the table in our comment table
     */
    private PreparedStatement cDropTable;

    /**
     * A prepared statement for creating the view in our like table
     */
    private PreparedStatement lCreateView;

    /**
     * A prepared statement for creating the view in our like table
     */
    private PreparedStatement lDropView;

    /**
     * A prepared statement for creating the table in our like table
     */
    private PreparedStatement lCreateTable;

    /**
     * A prepared statement for dropping the table in our like table
     */
    private PreparedStatement lDropTable;

    /**
     * A prepared statement for clearing the likes for a row in our like table
     */
    private PreparedStatement lClearOne;

    /**
     * A prepared statement for creating the view in our like table
     */
    private PreparedStatement dCreateView;

    /**
     * A prepared statement for dropping the table in our like table
     */
    private PreparedStatement dDropView;

    /**
     * A prepared statement for creating the table in our like table
     */
    private PreparedStatement dCreateTable;

    /**
     * A prepared statement for dropping the table in our like table
     */
    private PreparedStatement dDropTable;

    /**
     * A prepared statement for clearing the likes for a row in our like table
     */
    private PreparedStatement dClearOne;

    
    /**
     * A prepared statement for getting all data in the user table
     */
    private PreparedStatement fSelectAll;

    /**
     * A prepared statement for getting one row from the user table
     */
    private PreparedStatement fSelectOne;

    /**
     * A prepared statement for deleting a row from the user table
     */
    private PreparedStatement fDeleteOne;

    

    /**
     * A prepared statement for creating the table in our user table
     */
    private PreparedStatement fCreateTable;

    /**
     * A prepared statement for dropping the table in our user table
     */
    private PreparedStatement fDropTable;

    private PreparedStatement fInsertOne;


    // Prepared statements for tblQR
    private PreparedStatement qCreateTable;

    private PreparedStatement qDropTable;

    private PreparedStatement qSelectAll;

    private PreparedStatement qInsertOne;

    private PreparedStatement qDeleteOne;



    /**
     * RowData is like a struct in C: we use it to hold data, and we allow direct
     * access to its fields. In the context of this Database, RowData represents the
     * data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want to
     * encourage users to think of RowData as being anything other than an abstract
     * representation of a row of the database. RowData and the Database are tightly
     * coupled: if one changes, the other should too.
     */
    public static class RowData {
        /**
         * The ID of this row of the database
         */
        int mId;
        /**
         * The user id of this message
         */
        int uId;
        /**
         * Name of the user who post this message
         */
        String userName;
        /**
         * The subject stored in this row
         */
        String mSubject;
        /**
         * The message stored in this row
         */
        String mMessage;
        /**
         * The count of likes
         */
        int mlikes;
        /**
         * The count of likes
         */
        int mdislikes;
        /**
         * The date of the post
         */
        Timestamp mDate;

        String mLink;

        String fileId;

        int qId;

        Timestamp qDate;


        /**
         * Construct a RowData object by providing values for its fields
         */
        public RowData(int mid, int uid, String username, String subject, String message, int likes, int dislikes, Timestamp date, String fileId, String link, int qid, Timestamp qdate) {
            mId = mid;
            uId = uid;
            userName = username;
            mSubject = subject;
            mMessage = message;
            mlikes = likes;
            mdislikes = dislikes;
            mDate = date;
            mLink = link;
            fileId = fileId;
            qId = qid;
            qDate = qdate;

        }

        /**
         * Construct a RowData object by providing values for its fields
         */
        public RowData(int mid, String subject) {
            mId = mid;
            uId = 0;
            userName = null;
            mSubject = subject;
            mMessage = null;
            mlikes = 0;
            mdislikes = 0;
            mDate = null;
            mLink = null;
            fileId = null;
            qId = 0;
            qDate = null;
        }
    }

    /**
     * RowData is like a struct in C: we use it to hold data, and we allow direct
     * access to its fields. In the context of this Database, RowData represents the
     * data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want to
     * encourage users to think of RowData as being anything other than an abstract
     * representation of a row of the database. RowData and the Database are tightly
     * coupled: if one changes, the other should too.
     */
    public static class RowUser {
        /**
         * The ID of this row of the database
         */
        int uId;
        /**
         * The subject stored in this row
         */
        String username;
        /**
         * The message stored in this row
         */
        String uEmail;
//        /**
//         * The count of likes
//         */
//        String uSalt;
//        /**
//         * The message stored in this row
//         */
//        String uPassword;
        /**
         * The message stored in this row
         */
        String uIntro;
        
        int uQuota;

        /**
         * Construct a RowUser object by providing values for its fields
         */
        public RowUser(int uid, String name, String email, String intro, int quota) {
            uId = uid;
            username = name;
            uEmail = email;
//            uSalt = salt;
//            uPassword = password;
            uIntro = intro;
            uQuota = quota;
        }

        /**
         * Construct a RowUser object by providing values for its fields
         */
        public RowUser(int uid, String name) {
            uId = uid;
            username = name;
            uEmail = null;
//            uSalt = null;
//            uPassword = null;
            uIntro = null;
        }
    }

    /**
     * RowComment is like a struct in C: we use it to hold data, and we allow direct
     * access to its fields. In the context of this Database, RowComment represents the
     * data we'd see in a row.
     * 
     * We make RowComment a static class of Database because we don't really want to
     * encourage users to think of RowComment as being anything other than an abstract
     * representation of a row of the database. RowComment and the Database are tightly
     * coupled: if one changes, the other should too.
     */
    public static class RowComment {
        /**
         * The ID of this row of the database
         */
        int cId;
        /**
         * The subject stored in this row
         */
        int uId;
        /**
         * The message stored in this row
         */
        int mId;
        /**
         * The count of likes
         */
        String cText;
        
        String fileId;

        String cLink;

        /**
         * Construct a RowComment object by providing values for its fields
         */
        public RowComment(int cid, int uid, int mid, String text, String fileId, String link) {
            cId = cid;
            uId = uid;
            mId = mid;
            cText = text;
            fileId = fileId;
            cLink = link;
        }
    }

    /**
     * RowDrive is like a struct in C: we use it to hold data, and we allow direct
     * access to its fields. In the context of this Database, RowDrive represents the
     * data we'd see in a row.
     * 
     * We make RowDrive a static class of Database because we don't really want to
     * encourage users to think of RowDrive as being anything other than an abstract
     * representation of a row of the database. RowDrive and the Database are tightly
     * coupled: if one changes, the other should too.
     */
    public static class RowDrive {
        /**
         * The ID of this row of the database
         */
        String fId;
        /**
         * The subject stored in this row
         */
        int uId;
        /**
         * The message stored in this row
         */
        String fActivity;
        /**
         * The message stored in this row
         */
        String fName;
        /**
         * The count of likes
         */
        int fSize;

        /**
         * Construct a RowDrive object by providing values for its fields
         */
        public RowDrive(String fid, String name, int uid, String activity, int size) {
            fId = fid;
            uId = uid;
            fName = name;
            fActivity = activity;
            fSize = size;
        }
        public RowDrive(String fid, String name) {
            fId = fid;
            uId = 0;
            fName = name;
            fActivity = "default";
            fSize = 0;
        }
    }

    public static class RowQR {
        int qId;

        int uId;

        Timestamp qDate;

        /**
         * Construct a RowQR object by providing values for its fields
         */
        public RowQR (int qid, int uid, Timestamp qdate){
            qId = qid;
            uId = uid;
            qDate = qdate;
        }
    }
 

    /**
     * The Database constructor is private: we only create Database objects through
     * the getDatabase() method.
     */
    private Database() {
    }

    /**
     * Get a fully-configured connection to the database
     * 
     * @param db_url The login info to heroku database
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String db_url) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Class.forName("org.postgresql.Driver");
            URI dbUri = new URI(db_url);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath()
                    + "?sslmode=require";
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Unable to find postgresql driver");
            return null;
        } catch (URISyntaxException s) {
            System.out.println("URI Syntax Error");
            return null;
        }

        // Attempt to create all of our prepared statements. If any of these
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            // SQL incorrectly. We really should have things like "tblData"
            // as constants, and then build the strings for the statements
            // from those constants.

            // Prepared statement for user table
            db.uCreateTable = db.mConnection
                    .prepareStatement("CREATE TABLE tblUser (uid SERIAL PRIMARY KEY, username VARCHAR(50) "
                            + "NOT NULL, email VARCHAR(500) NOT NULL "
                            + ", intro VARCHAR(500) NOT NULL, quota INTEGER)");
            db.uDropTable = db.mConnection.prepareStatement("DROP TABLE tblUser");

            // Standard CRUD operations
            db.uDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblUser WHERE uid = ?");
            db.uInsertOne = db.mConnection.prepareStatement("INSERT INTO tblUser VALUES (default, ?, ?, ?)");
            db.uSelectAll = db.mConnection.prepareStatement("SELECT uid, username FROM tblUser");
            db.uSelectOne = db.mConnection.prepareStatement("SELECT * from tblUser WHERE uid = ?");
            db.uUpdateOne = db.mConnection.prepareStatement("UPDATE tblUser SET username = ?, intro = ? WHERE uid = ?");
//            db.uAuth = db.mConnection.prepareStatement("SELECT * from tblUser WHERE email = ? AND password = ?");
//            db.uUpdatePassword = db.mConnection
//                    .prepareStatement("UPDATE tblUser SET salt = ?, password = ? WHERE uid = ?");

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table
            // creation/deletion, so multiple executions will cause an exception

            // changed table
            db.mCreateTable = db.mConnection
                    .prepareStatement("CREATE TABLE tblData (mid SERIAL PRIMARY KEY, uid INTEGER, subject VARCHAR(50) "
                            + "NOT NULL, message VARCHAR(500) NOT NULL, date TIMESTAMP(6), fileId VARCHAR(50), mLink VARCHAR(50), mime VARCHAR(50), " 
                            /*+ "tmEventId VARCHAR(20), " */+ "FOREIGN KEY(uid) REFERENCES tblUser)");
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");

            // Standard CRUD operations
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE mid = ?");
            db.mClearLikes = db.mConnection.prepareStatement("DELETE FROM tblLike WHERE mid = ?");
            db.mClearDislikes = db.mConnection.prepareStatement("DELETE FROM tblDislike WHERE mid = ?");
            db.mClearComments = db.mConnection.prepareStatement("DELETE FROM tblComment WHERE mid = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?, ?, ?,)");
            // db.mInsertOne2 = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?, ?, ?, ?)"); //the last ? is for tm event code insertion, need to test
            db.mSelectAll = db.mConnection.prepareStatement("SELECT mid, subject FROM tblData");
            db.mSelectOne = db.mConnection.prepareStatement(
                    "SELECT row.*, (SELECT COUNT(*) FROM tblLike WHERE tblLike.mid = row.mid) AS likes, (SELECT COUNT(*) FROM tblDislike WHERE tblDislike.mid = row.mid) AS dislikes FROM (SELECT * from tblData NATURAL JOIN tblUser) AS row WHERE row.mid = ?");
            db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET subject = ?, message = ? WHERE mid = ?");
            db.mIncrementLikes = db.mConnection.prepareStatement("INSERT INTO tblLike VALUES (?, ?)");
            db.mDecrementLikes = db.mConnection.prepareStatement("DELETE FROM tblLike WHERE uid = ? AND mid = ?");
            db.mIncrementDislikes = db.mConnection.prepareStatement("INSERT INTO tblDislike VALUES (?, ?)");
            db.mDecrementDislikes = db.mConnection.prepareStatement("DELETE FROM tblDislike WHERE uid = ? AND mid = ?");
            db.mCheckLikes = db.mConnection.prepareStatement("SELECT * from tblLike where uid = ? AND mid = ?");
            db.mCheckDislikes = db.mConnection.prepareStatement("SELECT * from tblDislike where uid = ? AND mid = ?");

            // Prepared statement for comment table
            db.cCreateTable = db.mConnection
                    .prepareStatement("CREATE TABLE tblComment (cid SERIAL PRIMARY KEY, uid INTEGER "
                            + "NOT NULL, mid INTEGER NOT NULL, text VARCHAR(500) NOT NULL, fileId VARCHAR(50), mLink VARCHAR(50), mime VARCHAR(50),"
                            + "FOREIGN KEY(uid) REFERENCES tblUser, FOREIGN KEY(mid) REFERENCES tblData)");
            db.cDropTable = db.mConnection.prepareStatement("DROP TABLE tblComment");

            // Standard CRUD operations
            db.cDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblComment WHERE cid = ?");
            db.cInsertOne = db.mConnection.prepareStatement("INSERT INTO tblComment VALUES (default, ?, ?, ?)");
            db.cSelectAll = db.mConnection.prepareStatement(
                    "SELECT cid, uid, mid, username, text FROM tblComment NATURAL JOIN tblUser where mid = ?");
            db.cSelectOne = db.mConnection.prepareStatement("SELECT * from tblComment join tblUser on tblComment.uid = tblUser.uid WHERE cid=?");
            db.cUpdateOne = db.mConnection.prepareStatement("UPDATE tblComment SET text = ? WHERE uid = ?");

            // Prepared statement for like relation
            db.lCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblLike (uid INTEGER, mid INTEGER, "
                    + "PRIMARY KEY(uid, mid), FOREIGN KEY(uid) REFERENCES tblUser, FOREIGN KEY(mid) REFERENCES tblData);");
            db.lDropTable = db.mConnection.prepareStatement("DROP TABLE tblLike");
            db.lCreateView = db.mConnection.prepareStatement(
                    "CREATE VIEW numOfLikes AS SELECT mid, COUNT(*) AS likes FROM tblLike GROUP BY mid;");
            db.lDropView = db.mConnection.prepareStatement("DROP VIEW numOfLikes;");

            // Prepared statement for dislike relation
            db.dCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblDislike (uid INTEGER, mid INTEGER, "
                    + "PRIMARY KEY(uid, mid), FOREIGN KEY(uid) REFERENCES tblUser, FOREIGN KEY(mid) REFERENCES tblData);");
            db.dDropTable = db.mConnection.prepareStatement("DROP TABLE tblDislike");
            db.dCreateView = db.mConnection.prepareStatement(
                    "CREATE VIEW numOfDislikes AS SELECT mid, COUNT(*) AS dislikes FROM tblDislike GROUP BY mid;");
            db.dDropView = db.mConnection.prepareStatement("DROP VIEW numOfDislikes;");

            db.fCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblFile (fid VARCHAR(50) PRIMARY KEY, name VARCHAR(50), uid INTEGER, activity VARCHAR(500) NOT NULL,"
                                                        + "size INTEGER)");
            db.fSelectAll = db.mConnection.prepareStatement("SELECT * from tblFile");
            db.fSelectOne = db.mConnection.prepareStatement("SELECT * from tblFile WHERE fid = ?");
            db.fDropTable = db.mConnection.prepareStatement("DROP TABLE tblFile");
            db.fInsertOne = db.mConnection.prepareStatement("INSERT INTO tblFile VALUES(?, ?, ?, ?)");
            db.fSelectAll = db.mConnection.prepareStatement("SELECT fid, name FROM tblFile");
            
            // Prepared statement for tblQR
            db.qCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblQR (qid SERIAL PRIMARY KEY, uid INTEGER, date TIMESTAMP(6), FOREIGN KEY(uid) REFERENCES tblUser)");
            db.qDropTable = db.mConnection.prepareStatement("DROP TABLE tblQR");
            db.qSelectAll = db.mConnection.prepareStatement("SELECT * from tblQR");
            db.qInsertOne = db.mConnection.prepareStatement("INSERT INTO tblQR VALUES(default, ?, ?)");
            db.qDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblQR WHERE qid = ?");

        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an error
     * occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    /**
     * Insert a row into the database
     * 
     * @param uid     The id of the post creator
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * 
     * @return The number of rows that were inserted
     */
    int insertRowToData(int uid, String subject, String message) {
        int count = 0;
        try {
            mInsertOne.setInt(1, uid);
            mInsertOne.setString(2, subject);
            mInsertOne.setString(3, message);
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            mInsertOne.setTimestamp(4, ts);
            count += mInsertOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert a row into the database
     * 
     * @param uid     The id of the post creator
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * @param event   The event ID of the tm event
     * 
     * @return The number of rows that were inserted
     */

    /* since we no longer use ticketmaster api, no need for this method.
    int insertRowToDataWithEvent(int uid, String subject, String message, String event) {
        int count = 0;
        try {
            mInsertOne2.setInt(1, uid);
            mInsertOne2.setString(2, subject);
            mInsertOne2.setString(3, message);
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            mInsertOne2.setTimestamp(4, ts);
            count += mInsertOne.executeUpdate();
            mInsertOne2.setString(5, event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    */

    /**
     * Insert a row into the database
     * 
     * @param email The subject for this new row
     * 
     * @return The number of rows that were inserted
     */
    int insertRowToUser(String email) {
        // modify functions here
//        String salt = BCrypt.gensalt(12);
//        String hash = BCrypt.hashpw(password, salt);
        int count = 0;
        try {
            uInsertOne.setString(1, email.split("@")[0]);
            uInsertOne.setString(2, email);
//            uInsertOne.setString(3, salt);
//            uInsertOne.setString(4, hash);
            uInsertOne.setString(3, "This person is lazy, so nothing's here");
            count += uInsertOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert a row into the database
     * 
     * @param uid  The id of the comment's owner
     * @param mid  The message related
     * @param text The content of the comment
     * 
     * @return The number of rows that were inserted
     */
    int insertRowToComment(int uid, int mid, String text) {
        int count = 0;
        try {
            cInsertOne.setInt(1, uid);
            cInsertOne.setInt(2, mid);
            cInsertOne.setString(3, text);
            count += cInsertOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertRowToLike(int uid, int mid) {
        int count = 0;
        try {
            mIncrementLikes.setInt(1, uid);
            mIncrementLikes.setInt(2, mid);
            count += mIncrementLikes.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertRowToDislike(int uid, int mid) {
        int count = 0;
        try {
            mIncrementDislikes.setInt(1, uid);
            mIncrementDislikes.setInt(2, mid);
            count += mIncrementDislikes.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    int insertRowToFile(String fid, String name, int uid, String activity){
        int count = 0;
        try {
            fInsertOne.setString(1, fid);
            fInsertOne.setString(2, name);
//            uInsertOne.setString(3, salt);
//            uInsertOne.setString(4, hash);
            fInsertOne.setInt(3, uid);
            fInsertOne.setString(4, activity);
            //fInsertOne.setInt(5, size);

            count += fInsertOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertRowToQR(int uid){
        int count = 0;
        try {
            qInsertOne.setInt(1, uid);
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            qInsertOne.setTimestamp(2, ts);
            count += qInsertOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowData> selectAllFromData() {
        ArrayList<RowData> res = new ArrayList<RowData>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new RowData(rs.getInt("mid"), rs.getString("subject")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowUser> selectAllFromUser() {
        ArrayList<RowUser> res = new ArrayList<RowUser>();
        try {
            ResultSet rs = uSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new RowUser(rs.getInt("uid"), rs.getString("username")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowComment> selectAllFromComment(int mid) {
        ArrayList<RowComment> res = new ArrayList<RowComment>();
        try {
            cSelectAll.setInt(1, mid);
            ResultSet rs = cSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new RowComment(rs.getInt("cid"), rs.getInt("uid"), rs.getInt("mid"), rs.getString("text"), rs.getString("field"), rs.getString("cLink")));
            }
            
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowDrive> selectAllFromFile() {
        ArrayList<RowDrive> res = new ArrayList<RowDrive>();
        try {
            ResultSet rs = fSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new RowDrive(rs.getString("fid"), rs.getString("name")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<RowQR> selectAllFromQR() {
        ArrayList<RowQR> res = new ArrayList<RowQR>();
        try {
            ResultSet rs = qSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new RowQR(rs.getInt("qid"), rs.getInt("uid"), rs.getTimestamp("qdate")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    RowData selectOneFromData(int mid) {
        RowData res = null;
        try {
            mSelectOne.setInt(1, mid);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) {
                res = new RowData(rs.getInt("mid"), rs.getInt("uid"), rs.getString("username"), rs.getString("subject"), rs.getString("message"), rs.getInt("likes"),
                    rs.getInt("dislikes"), rs.getTimestamp("date"), rs.getString("mLink"), rs.getString("field"), rs.getInt("qid"), rs.getTimestamp("qdate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    RowUser selectOneFromUser(int uid) {
        RowUser res = null;
        try {
            uSelectOne.setInt(1, uid);
            ResultSet rs = uSelectOne.executeQuery();
            if (rs.next()) {
                res = new RowUser(rs.getInt("uid"), rs.getString("username"), rs.getString("email"),
                         rs.getString("intro"), rs.getInt("uQuota"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    RowComment selectOneFromComment(int cid) {
        RowComment res = null;
        try {
            cSelectOne.setInt(1, cid);
            ResultSet rs = cSelectOne.executeQuery();
            if (rs.next()) {
                res = new RowComment(rs.getInt(cid), rs.getInt("uid"), rs.getInt("mId"), rs.getString("text"), rs.getString("field"), rs.getString("cLink"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int deleteRowFromData(int id) {
        int res = -1;
        try {
            mDeleteOne.setInt(1, id);
            res = mDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int deleteRowFromUser(int uid) {
        int res = -1;
        try {
            uDeleteOne.setInt(1, uid);
            res = uDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int deleteRowFromComment(int cid) {
        int res = -1;
        try {
            cDeleteOne.setInt(1, cid);
            res = cDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int deleteRowFromQR(int qid) {
        int res = -1;
        try {
            qDeleteOne.setInt(1, qid);
            res = qDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int deleteRowFromLike(int uid, int mid) {
        int res = -1;
        try {
            mDecrementDislikes.setInt(1, uid);
            mDecrementDislikes.setInt(2, mid);
            res = mDecrementDislikes.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int deleteRowFromDislike(int uid, int mid) {
        int res = -1;
        try {
            mDecrementLikes.setInt(1, uid);
            mDecrementLikes.setInt(2, mid);
            res = mDecrementLikes.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int clearRowFromLike(int mid) {
        int res = -1;
        try {
            mClearLikes.setInt(1, mid);
            res = mClearLikes.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted. -1 indicates an error.
     */
    int clearRowFromDislike(int mid) {
        int res = -1;
        try {
            mClearDislikes.setInt(1, mid);
            res = mClearDislikes.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id      The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int updateOneInData(int mid, String title, String message) {
        int res = -1;
        try {
            mUpdateOne.setString(1, message);
            mUpdateOne.setString(2, title);
            mUpdateOne.setInt(3, mid);
            res = mUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id      The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int updateOneInUser(int uid, String username, String intro) {
        int res = -1;
        try {
            uUpdateOne.setString(1, username);
            uUpdateOne.setString(2, intro);
            uUpdateOne.setInt(3, uid);
            res = uUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id      The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int updateOneInComment(int uid, String text) {
        int res = -1;
        try {
            cUpdateOne.setString(1, text);
            cUpdateOne.setInt(2, uid);
            res = cUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Create view for tblLike. If it already exists, this will print an error
     */
    void createViewForLike() {
        try {
            lCreateView.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Drop view for tblLike. If it already exists, this will print an error
     */
    void dropViewForLike() {
        try {
            lDropView.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create view for tblDislike. If it already exists, this will print an error
     */
    void createViewForDislike() {
        try {
            dCreateView.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Drop view for tblDislike. If it already exists, this will print an error
     */
    void dropViewForDislike() {
        try {
            dDropView.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblData. If it already exists, this will print an error
     */
    void createData() {
        try {
            mCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblUser. If it already exists, this will print an error
     */
    void createUser() {
        try {
            uCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblComment. If it already exists, this will print an error
     */
    void createComment() {
        try {
            cCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblLike. If it already exists, this will print an error
     */
    void createLike() {
        try {
            lCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblDislike. If it already exists, this will print an error
     */
    void createDislike() {
        try {
            dCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblFile. If it already exists, this will print an error
     */
    void createFile() {
        try {
            fCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblQR. If it already exists, this will print an error
     */
    void createQR() {
        try {
            qCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database. If it does not exist, this will print an
     * error.
     */
    void dropData() {
        try {
            mDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblUser from the database. If it does not exist, this will print an
     * error.
     */
    void dropUser() {
        try {
            uDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblComment from the database. If it does not exist, this will print an
     * error.
     */
    void dropComment() {
        try {
            cDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblLike from the database. If it does not exist, this will print an
     * error.
     */
    void dropLike() {
        try {
            lDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblDislike from the database. If it does not exist, this will print an
     * error.
     */
    void dropDislike() {
        try {
            dDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database. If it does not exist, this will print an
     * error.
     */
    void dropFile() {
        try {
            fDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblQR from the database. If it does not exist, this will print an
     * error.
     */
    void dropQR(){
        try {
            qDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}