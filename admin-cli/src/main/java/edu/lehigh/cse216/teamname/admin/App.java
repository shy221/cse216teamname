package edu.lehigh.cse216.teamname.admin;




import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;

import com.sendgrid.*;


/**
 * App is our basic admin app. For now, it is a demonstration of the six key
 * operations on a database: connect, insert, update, query, delete, disconnect
 */
public class App {
    

    /**
     * Print the menu for our program
     */
    static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [T] Create a table");
        System.out.println("  [D] Drop a table");
        System.out.println("  [V] Create views");
        System.out.println("  [v] Drop views");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [*] Query for all rows");
        System.out.println("  [-] Delete a row (or decrement for like or dislike)");
        System.out.println("  [+] Insert a new row (or increment for like or dislike)");
        System.out.println("  [~] Update a row");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
        System.out.println("  [L] Clear likes and dislikes");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in) {
        // The valid actions:
        String actions = "TDVv1*-+~q?L";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Print the table menu for our program
     */
    static void tblMenu() {
        System.out.println("Choose a table");
        System.out.println("  [M] tblData");
        System.out.println("  [U] tblUser");
        System.out.println("  [C] tblComment");
        System.out.println("  [L] tblLike");
        System.out.println("  [D] tblDislike");
        System.out.println("  [F] tblFile");
        System.out.println("  [q] Quit to main menu");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char promptTables(BufferedReader in) {
        // The valid actions:
        String actions = "MUCLDFq?";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Print the table menu for our program
     */
    static void tblMenuLite() {
        System.out.println("Choose a table");
        System.out.println("  [M] tblData");
        System.out.println("  [U] tblUser");
        System.out.println("  [C] tblComment");
        System.out.println("  [F] tblFile");
        System.out.println("  [q] Quit to main menu");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char promptTablesLite(BufferedReader in) {
        // The valid actions:
        String actions = "MUCFq?";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Print the views menu for our program
     */
    static void viewsMenu() {
        System.out.println("Choose a view");
        System.out.println("  [L] numOfLikes");
        System.out.println("  [D] numOfDislikes");
        System.out.println("  [q] Quit to main menu");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char promptViews(BufferedReader in) {
        // The valid actions:
        String actions = "LDq?";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Print the views menu for our program
     */
    static void likesMenu() {
        System.out.println("Choose an attribute to clear");
        System.out.println("  [L] Like");
        System.out.println("  [D] Dislike");
        System.out.println("  [q] Quit to main menu");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char promptLikes(BufferedReader in) {
        // The valid actions:
        String actions = "LDq?";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String message
     * 
     * @param in      A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided. May be "".
     */
    static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in      A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided. On error, it will be -1
     */
    static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         * NumerFormatException is already handled by parseInt() catch
         * (NumberFormatException e) { e.printStackTrace(); }
         */
        return i;
    }

//    /**
//     * Method to send a email with email address
//     */
//    static boolean sendEmail(String from_email, String to_email, String password) {
//        Email from = new Email(from_email);
//        String subject = "[Buzz] Welcome to Buzz!";
//        Email to = new Email(to_email);
//        Content content = new Content("text/plain", "Your Buzz password is: " + password
//                + "\n\nTo reset your password, please login with your email address at: "
//                + "https://arcane-refuge-67249.herokuapp.com" + " and reset your password in the profile page.");
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }

//    /**
//     * function to generate a random string of length n
//     */
//    static String randomPassword(int n) {
//        // chose a Character random from this String
//        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"
//                + ",./;-=+!@#$%^&*";
//
//        // create StringBuffer size of AlphaNumericString
//        StringBuilder sb = new StringBuilder(n);
//
//        for (int i = 0; i < n; i++) {
//
//            // generate a random number between
//            // 0 to AlphaNumericString variable length
//            int index = (int) (AlphaNumericString.length() * Math.random());
//
//            // add Character one by one in end of sb
//            sb.append(AlphaNumericString.charAt(index));
//        }
//
//        return sb.toString();
//    }

    /**
     * The main routine runs a loop that gets a request from the user and processes
     * it
     * 
     * @param argv Command-line options. Ignored by this program.
     */
    public static void main(String[] argv) {
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        String db_url = env.get("DATABASE_URL");

        // Get a fully-configured connection to the database, or exit
        // immediately
        Database db = Database.getDatabase(db_url);
        if (db == null)
            return;

        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            // function call
            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'T') {
                while (true) {
                    action = promptTables(in);
                    if (action == '?') {
                        tblMenu();
                    } else if (action == 'M') {
                        db.createData();
                    } else if (action == 'U') {
                        db.createUser();
                    } else if (action == 'C') {
                        db.createComment();
                    } else if (action == 'L') {
                        db.createLike();
                    } else if (action == 'D') {
                        db.createDislike();
                    } else if (action == 'F'){
                        db.createFile();
                    } else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == 'D') {
                while (true) {
                    action = promptTables(in);
                    if (action == '?') {
                        tblMenu();
                    } else if (action == 'M') {
                        db.dropData();
                    } else if (action == 'U') {
                        db.dropUser();
                    } else if (action == 'C') {
                        db.dropComment();
                    } else if (action == 'L') {
                        db.dropLike();
                    } else if (action == 'D') {
                        db.dropDislike();
                    } else if (action == 'F'){
                        db.dropFile();
                    }
                     else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == 'V') {
                while (true) {
                    action = promptViews(in);
                    if (action == '?') {
                        viewsMenu();
                    } else if (action == 'L') {
                        db.createViewForLike();
                    } else if (action == 'D') {
                        db.createViewForDislike();
                    } else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == 'v') {
                while (true) {
                    action = promptViews(in);
                    if (action == '?') {
                        viewsMenu();
                    } else if (action == 'L') {
                        db.dropViewForLike();
                    } else if (action == 'D') {
                        db.dropViewForDislike();
                    } else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == '1') {
                while (true) {
                    action = promptTables(in);
                    if (action == '?') {
                        tblMenuLite();

                    } else if (action == 'M') {
                        int mid = getInt(in, "Enter the message ID");
                        if (mid == -1)
                            continue;
                        Database.RowData res = db.selectOneFromData(mid);
                        if (res != null) {
                            System.out.println("  [" + res.mId + "] " + res.mSubject);
                            System.out.println("  [" + res.uId + "] " + res.userName);
                            System.out.println("  --> " + res.mMessage);
                            System.out.println("  --> " + res.mlikes);
                            System.out.println("  --> " + res.mdislikes);
                            System.out.println("  --> " + res.mDate);
                            System.out.println("  --> " + res.mLink);
                            System.out.println("  --> " + res.field);
                        }

                    } else if (action == 'U') {
                        int uid = getInt(in, "Enter the user ID");
                        if (uid == -1)
                            continue;
                        Database.RowUser res = db.selectOneFromUser(uid);
                        if (res != null) {
                            System.out.println("  [" + res.uId + "] " + res.username);
                            System.out.println("  --> " + res.uEmail);
//                            System.out.println("  --> " + res.uSalt);
//                            System.out.println("  --> " + res.uPassword);
                            System.out.println("  --> " + res.uIntro);
                            System.out.println("  --> " + res.uQuota);
                        }

                    } else if (action == 'C') {
                        int cid = getInt(in, "Enter the comment ID");
                        if (cid == -1)
                            continue;
                        Database.RowComment res = db.selectOneFromComment(cid);
                        if (res != null) {
                            System.out.println("  [" + res.cId + "] ");
                            System.out.println("  [" + res.uId + "] ");
                            System.out.println("  [" + res.mId + "] ");
                            System.out.println("  --> " + res.cText);
                            System.out.println("  --> " + res.field);
                            System.out.println("  --> " + res.cLink);
                        }
                    } else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == '*') {
                while (true) {
                    action = promptTables(in);
                    if (action == '?') {
                        tblMenuLite();

                    } else if (action == 'M') {
                        ArrayList<Database.RowData> res = db.selectAllFromData();
                        if (res == null)
                            continue;
                        System.out.println("  Current tblData Contents");
                        System.out.println("  -------------------------");
                        for (Database.RowData rd : res) {
                            System.out.println("  [" + rd.mId + "] " + rd.mSubject);
                        }

                    } else if (action == 'U') {
                        ArrayList<Database.RowUser> res = db.selectAllFromUser();
                        if (res == null)
                            continue;
                        System.out.println("  Current tblUser Contents");
                        System.out.println("  -------------------------");
                        for (Database.RowUser rd : res) {
                            System.out.println("  [" + rd.uId + "] " + rd.username);
                        }

                    } else if (action == 'C') {
                        int mid = getInt(in, "Enter the message ID");
                        ArrayList<Database.RowComment> res = db.selectAllFromComment(mid);
                        if (res == null)
                            continue;
                        System.out.println("  Current tblComment Contents");
                        System.out.println("  -------------------------");
                        for (Database.RowComment rd : res) {
                            System.out.println("  [" + rd.cId + "] ");
                            System.out.println("  [" + rd.uId + "] ");
                            System.out.println("  [" + rd.mId + "] ");
                            System.out.println("  --> " + rd.cText);
                        }
                    } else if (action == 'F') {
                        
                        DriveQuickstart serviceDrive = new DriveQuickstart();
                        //ArrayList<Database.RowData> res = db.selectAllFromData();
                        /*if (res == null)
                            continue;*/
                        System.out.println("  Current tblFile Contents");
                        System.out.println("  -------------------------");
                        //serviceDrive.listDrive();
                        /*for (Database.RowData rd : res) {
                            System.out.println("  [" + rd.mId + "] " + rd.mSubject);
                        }*/
                    } else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == '-') {
                while (true) {
                    action = promptTables(in);
                    if (action == '?') {
                        tblMenu();
                    } else if (action == 'M') {
                        int mid = getInt(in, "Enter the message ID");
                        if (mid == -1)
                            continue;
                        int res = db.deleteRowFromData(mid);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows deleted");
                    } else if (action == 'U') {
                        int uid = getInt(in, "Enter the user ID");
                        if (uid == -1)
                            continue;
                        int res = db.deleteRowFromUser(uid);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows deleted");
                    } else if (action == 'C') {
                        int cid = getInt(in, "Enter the comment ID");
                        if (cid == -1)
                            continue;
                        int res = db.deleteRowFromComment(cid);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows deleted");
                    } else if (action == 'L') {
                        int uid = getInt(in, "Enter the user ID");
                        int mid = getInt(in, "Enter the message ID");
                        if (uid == -1 || mid == -1)
                            continue;
                        int res = db.deleteRowFromLike(uid, mid);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows deleted");
                    } else if (action == 'D') {
                        int uid = getInt(in, "Enter the user ID");
                        int mid = getInt(in, "Enter the message ID");
                        if (uid == -1 || mid == -1)
                            continue;
                        int res = db.deleteRowFromDislike(uid, mid);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows deleted");
                    } else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == '+') {
                while (true) {
                    action = promptTables(in);
                    if (action == '?') {
                        tblMenu();

                    } else if (action == 'M') {
                        String subject = getString(in, "Enter the subject");
                        String message = getString(in, "Enter the message");
                        int uid = getInt(in, "Enter the uid");
                        if (subject.equals("") || message.equals("") || uid <= 0)
                            continue;
                        int res = db.insertRowToData(uid, subject, message);
                        System.out.println(res + " rows added");

                    } else if (action == 'U') {
                        String email = getString(in, "Enter the email");
                        if (email.equals("")) {
                            continue;
                        }
//                        String password = randomPassword(8);

                        int res = db.insertRowToUser(email);
//                        System.out.println(sendEmail("admin@buzz.com", email));
                        System.out.println(res + " rows added");

                    } else if (action == 'C') {
                        String msg = getString(in, "Enter the comment");
                        int mid = getInt(in, "Enter the id of message");
                        int uid = getInt(in, "Enter the your user id");
                        if (msg.equals("") || mid <= 0 || uid <= 0) {
                            continue;
                        }
                        int res = db.insertRowToComment(uid, mid, msg);
                        System.out.println(res + " rows added");

                    } else if (action == 'L') {
                        int mid = getInt(in, "Enter the id of message");
                        int uid = getInt(in, "Enter the your user id");
                        if (mid <= 0 || uid <= 0) {
                            continue;
                        }
                        int res = db.insertRowToLike(uid, mid);
                        System.out.println(res + " rows added");
                    } else if (action == 'D') {
                        int mid = getInt(in, "Enter the id of message");
                        int uid = getInt(in, "Enter the your user id");
                        if (mid <= 0 || uid <= 0) {
                            continue;
                        }
                        int res = db.insertRowToDislike(uid, mid);
                        System.out.println(res + " rows added");
                    } else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == '~') {
                while (true) {
                    action = promptTables(in);
                    if (action == '?') {
                        tblMenuLite();

                    } else if (action == 'M') {
                        int mid = getInt(in, "Enter the message ID");
                        String newTitle = getString(in, "Enter the new title");
                        String newMessage = getString(in, "Enter the new message");
                        if (mid == -1 || newTitle.equals("") || newMessage.equals(""))
                            continue;
                        int res = db.updateOneInData(mid, newTitle, newMessage);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows updated");

                    } else if (action == 'U') {
                        int uid = getInt(in, "Enter the user ID");
                        String newUsername = getString(in, "Enter the new username");
                        String newIntro = getString(in, "Enter the new intro");
                        if (uid == -1 || newUsername.equals("") || newIntro.equals(""))
                            continue;
                        int res = db.updateOneInUser(uid, newUsername, newIntro);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows updated");

                    } else if (action == 'C') {
                        int uid = getInt(in, "Enter the user ID");
                        String newText = getString(in, "Enter the new comment");
                        if (uid == -1 || newText.equals(""))
                            continue;
                        int res = db.updateOneInComment(uid, newText);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows updated");
                    } else if (action == 'q') {
                        break;
                    }
                }

            } else if (action == 'L') {
                while (true) {
                    action = promptLikes(in);
                    if (action == '?') {
                        likesMenu();
                    } else if (action == 'L') {
                        int mid = getInt(in, "Enter the message ID");
                        if (mid == -1)
                            continue;
                        int res = db.clearRowFromLike(mid);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows deleted");
                    } else if (action == 'D') {
                        int mid = getInt(in, "Enter the message ID");
                        if (mid == -1)
                            continue;
                        int res = db.clearRowFromDislike(mid);
                        if (res == -1)
                            continue;
                        System.out.println("  " + res + " rows deleted");
                    } else if (action == 'q') {
                        break;
                    }
                }
            }
        }
        // Always remember to disconnect from the database when the program
        // exits
        db.disconnect();
    }
}