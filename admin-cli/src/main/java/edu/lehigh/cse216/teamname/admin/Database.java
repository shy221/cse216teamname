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
     * A prepared statement for dropping the table in our user table
     */
    private PreparedStatement cDropTable;

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
         * The date of the post
         */
        Timestamp mDate;

        /**
         * Construct a RowData object by providing values for its fields
         */
        public RowData(int id, String subject, String message, int likes, Timestamp date) {
            mId = id;
            mSubject = subject;
            mMessage = message;
            mlikes = likes;
            mDate = date;
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
                            + "NOT NULL, email VARCHAR(500) NOT NULL, salt VARCHAR(500) NOT NULL, password "
                            + "VARCHAR(500) NOT NULL, intro VARCHAR(500) NOT NULL)");
            db.uDropTable = db.mConnection.prepareStatement("DROP TABLE tblUser");

            // Standard CRUD operations
            db.uDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblUser WHERE uid = ?");
            db.uInsertOne = db.mConnection.prepareStatement("INSERT INTO tblUser VALUES (default, ?, ?, ?, ?)");
            db.uSelectAll = db.mConnection.prepareStatement("SELECT uid, username FROM tblUser");
            db.uSelectOne = db.mConnection.prepareStatement("SELECT * from tblUser WHERE uid=?");
            db.uUpdateOne = db.mConnection.prepareStatement("UPDATE tblUser SET username = ?, intro = ? WHERE uid = ?");

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table
            // creation/deletion, so multiple executions will cause an exception
            db.mCreateTable = db.mConnection
                    .prepareStatement("CREATE TABLE tblData (mid SERIAL PRIMARY KEY, uid SERIAL, subject VARCHAR(50) "
                            + "NOT NULL, message VARCHAR(500) NOT NULL, date TIMESTAMP(6)), FOREIGN KEY(uid) REFERENCES tblUser");
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");

            // Standard CRUD operations
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE mid = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT mid, uid, subject FROM tblData");
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE mid=?");
            db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET message = ? WHERE mid = ?");

            // Prepared statement for comment table
            db.cCreateTable = db.mConnection
                    .prepareStatement("CREATE TABLE tblComment (cid SERIAL PRIMARY KEY, uid SERIAL "
                            + "NOT NULL, mid SERIAL NOT NULL, text VARCHAR(500) NOT NULL, "
                            + "FOREIGN KEY(uid) REFERENCES tblUser, FOREIGN KEY(mid) REFERENCES tblData)");
            db.cDropTable = db.mConnection.prepareStatement("DROP TABLE tblComment");

            // Standard CRUD operations
            db.cDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblComment WHERE cid = ?");
            db.cInsertOne = db.mConnection.prepareStatement("INSERT INTO tblComment VALUES (default, ?, ?, ?)");
            db.cSelectAll = db.mConnection.prepareStatement("SELECT cid, uid, text FROM tblComment");
            db.cSelectOne = db.mConnection.prepareStatement("SELECT * from tblComment join tblUser on uid WHERE cid=?");
            db.cUpdateOne = db.mConnection.prepareStatement("UPDATE tblComment SET text = ? WHERE uid = ?");

            // Prepared statement for like relation
            db.lCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblLike (uid SERIAL PRIMARY KEY, mid SERIAL "
                    + "PRIMARY KEY, FOREIGN KEY(uid) REFERENCES tblUser, FOREIGN KEY(mid) REFERENCES tblData)");
            db.lDropTable = db.mConnection.prepareStatement("DROP TABLE tblLike");

            // Standard CRUD operations
            db.lInsertOne = db.mConnection.prepareStatement("INSERT INTO tblLike VALUES (?, ?)");
            db.lDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblLike WHERE uid = ?");
            db.lClearOne = db.mConnection.prepareStatement("DELETE FROM tblLike WHERE mid = ?");

            // Prepared statement for dislike relation
            db.dCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblDislike (uid SERIAL PRIMARY KEY, mid SERIAL "
                    + "PRIMARY KEY, FOREIGN KEY(uid) REFERENCES tblUser, FOREIGN KEY(mid) REFERENCES tblData)");
            db.dDropTable = db.mConnection.prepareStatement("DROP TABLE tblDislike");

            // Standard CRUD operations
            db.dInsertOne = db.mConnection.prepareStatement("INSERT INTO tblDislike VALUES (?, ?)");
            db.dDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblDislike WHERE uid = ?");
            db.dClearOne = db.mConnection.prepareStatement("DELETE FROM tblDislike WHERE mid = ?");

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
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * 
     * @return The number of rows that were inserted
     */
    int insertRow(String subject, String message) {
        int count = 0;
        try {
            mInsertOne.setString(1, subject);
            mInsertOne.setString(2, message);
            mInsertOne.setInt(3, 0);
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
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowData> selectAll() {
        ArrayList<RowData> res = new ArrayList<RowData>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new RowData(rs.getInt("id"), rs.getString("subject"), "", 0, null));
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
    RowData selectOne(int id) {
        RowData res = null;
        try {
            mSelectOne.setInt(1, id);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) {
                res = new RowData(rs.getInt("id"), rs.getString("subject"), rs.getString("message"), rs.getInt("likes"),
                        rs.getTimestamp("date"));
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
    int deleteRow(int id) {
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
     * Update the message for a row in the database
     * 
     * @param id      The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated. -1 indicates an error.
     */
    int updateOne(int id, String message) {
        int res = -1;
        try {
            mUpdateOne.setString(1, message);
            mUpdateOne.setInt(2, id);
            res = mUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public RowData incrementLikes(int id) {
        try {
            mIncrementLikes.setInt(1, id);
            mIncrementLikes.executeUpdate();
            return selectOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create tblData. If it already exists, this will print an error
     */
    void createTable() {
        try {
            mCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database. If it does not exist, this will print an
     * error.
     */
    void dropTable() {
        try {
            mDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}