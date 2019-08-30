package edu.lehigh.cse216.yut222.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class Database {
    /**
     * The connection to the database
     */
    private Connection mConnection;

    /**
     * The Id of the next inserted row
     */
    private int mCount;

    /**
     * Some prepared statements
     */
    private PreparedStatement mDeleteOne;
    private PreparedStatement mInsertOne;
    private PreparedStatement mSelectAll;
    private PreparedStatement mSelectOne;
    private PreparedStatement mUpdateOne;

    /**
     * The Database constructor is private: we only create Database objects
     * through the getDatabase() method.
     */
    private Database() {
        mCount = 0;
    }

    /**
     * Connect to the Database
     * 
     * @param ip   The IP address of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String ip, String port, String user, String pass) {
        Database db = new Database();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/", user, pass);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        }

        // Create prepared statement
        try {
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (?, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT id, subject FROM tblData");
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
            db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET subject = ?, message = ? WHERE id = ?");
        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    /**
     * Close the current connection to the Database, if one exists.
     * 
     * @return True if the connection was cleanly closed, false otherwise.
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
     * @param message The message for this new row
     * @return The Id of the new row, or -1 if no row was created
     */
    public int createEntry(String subject, String message) {
        if (subject == null || message == null)
            return -1;
        int id = mCount++;
        try {
            mInsertOne.setInt(1, id);
            mInsertOne.setString(2, subject);
            mInsertOne.setString(3, message);
            mInsertOne.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Get all data for a specific row, by Id
     * 
     * @param id The Id of the row being requested
     * @return The data for the requested row, or null if the Id was invalid
     */
    public DataRow readOne(int id) {
        DataRow res = null;
        try {
            mSelectOne.setInt(1, id);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) {
                res = new DataRow(rs.getInt("id"), rs.getString("subject"), rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Query the database for a list of all subjects and their Ids
     * 
     * @return All rows, as an ArrayList
     */
    public ArrayList<DataRowLite> readAll() {
        ArrayList<DataRowLite> res = new ArrayList<DataRowLite>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new DataRowLite(new DataRow(rs.getInt("id"), rs.getString("subject"), null)));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update the subject and message of a row in the database
     * 
     * @param id The Id of the row to update
     * @param subject The new subject for the row
     * @param message The new message for the row
     * @return a copy of the data in the row, if exists, or null otherwise
     */
    public DataRow updateOne(int id, String subject, String message) {
        try {
            mUpdateOne.setString(1, subject);
            mUpdateOne.setString(2, message);
            mUpdateOne.setInt(3, id);
            mUpdateOne.execute();
            return readOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete a row from the database
     * 
     * @param id The Id of the row to delete
     * @return true if the row was delete, false otherwise
     */
    public boolean deleteOne(int id) {
        try {
            mDeleteOne.setInt(1, id);
            mDeleteOne.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}